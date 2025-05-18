package org.example.pioner_pixel.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.pioner_pixel.domain.entity.PhoneData;
import org.example.pioner_pixel.domain.entity.User;
import org.example.pioner_pixel.dto.UserDto;
import org.example.pioner_pixel.repository.PhoneRepository;
import org.example.pioner_pixel.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PhoneServiceImpl implements PhoneService {
    private final UserRepository userRepository;
    private final PhoneRepository phoneRepository;
    private final ModelMapper modelMapper;

    @Override
    @CachePut(value = "users", key = "#root.target.getCurrentUserId()")
    @Transactional
    public UserDto addPhone(String phone) {
        User user = findCurrentUser();
        checkPhoneInDb(phone);

        PhoneData phoneData = PhoneData.builder()
                .phone(phone)
                .user(user)
                .build();

        user.getPhoneData().add(phoneData);

        userRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    @CachePut(value = "users", key = "#root.target.getCurrentUserId()")
    @Transactional
    public UserDto updatePhone(Long id, String phone) {
        User user = findCurrentUser();
        checkPhoneInDb(phone);

        PhoneData targetPhoneData = user.getPhoneData().stream()
                .filter(phoneData -> phoneData.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Phone with id " + id + " not found"));
        targetPhoneData.setPhone(phone);

        userRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    @CacheEvict(value = "users", key = "#root.target.getCurrentUserId()")
    @Transactional
    public void deletePhone(Long id) {
        User user = findCurrentUser();
        if (user.getPhoneData().size() <= 1) {
            throw new IllegalStateException("Cannot delete last number");
        }
        PhoneData phoneDataToRemove = user.getPhoneData().stream()
                .filter(phoneData -> phoneData.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Phone with id " + id + " not found"));

        user.getPhoneData().remove(phoneDataToRemove);
        userRepository.save(user);
    }

    @Override
    public Long getCurrentUserId() {
        return findCurrentUser().getId();
    }

    private User findCurrentUser() {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmailData_Email(currentUsername)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    private void checkPhoneInDb(String phone) {
        if (phoneRepository.findByPhone(phone).isPresent()) {
            throw new IllegalArgumentException("Номер уже существует");
        }
    }
}
