package org.example.pioner_pixel.service;

import lombok.RequiredArgsConstructor;
import org.example.pioner_pixel.domain.document.UserDocument;
import org.example.pioner_pixel.domain.entity.EmailData;
import org.example.pioner_pixel.domain.entity.PhoneData;
import org.example.pioner_pixel.domain.entity.User;
import org.example.pioner_pixel.repository.UserRepository;
import org.example.pioner_pixel.repository.UserSearchRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDataSyncServiceImpl implements UserDataSyncService{
    private final UserRepository userRepository;
    private final UserSearchRepository userSearchRepository;
    @Transactional
    @Scheduled(fixedRate = 60000)
    @Override
    public void syncUsersToElastic() {
        List<User> users = userRepository.findAll();
        List<UserDocument> documents = users.stream()
                .map(this::convertToDocument)
                .toList();
        userSearchRepository.saveAll(documents);
    }

    @Override
    public UserDocument convertToDocument(User user) {
        Set<String> emails = user.getEmailData().stream()
                .map(EmailData::getEmail)
                .collect(Collectors.toSet());

        Set<String> phones = user.getPhoneData().stream()
                .map(PhoneData::getPhone)
                .collect(Collectors.toSet());

        BigDecimal balance = user.getAccount().getBalance();

        return UserDocument.builder()
                .id(user.getId())
                .name(user.getName())
                .emails(emails)
                .dateOfBirth(user.getDateOfBirth())
                .phones(phones)
                .balance(balance)
                .build();


    }
}
