package org.example.pioner_pixel.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.pioner_pixel.domain.entity.EmailData;
import org.example.pioner_pixel.domain.entity.User;
import org.example.pioner_pixel.dto.UserDto;
import org.example.pioner_pixel.repository.EmailRepository;
import org.example.pioner_pixel.repository.UserRepository;
import org.example.pioner_pixel.utils.CustomUserDetailsService;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {
    private final UserRepository userRepository;
    private final EmailRepository emailRepository;
    private final ModelMapper modelMapper;

    @Override
    @CachePut(value = "users", key = "#root.target.getCurrentUserId()")
    public UserDto addEmail(String email) {
        User user = findCurrentUser();

        checkEmailInDb(email);

        EmailData emailData = EmailData.builder()
                .email(email)
                .user(user)
                .build();

        user.getEmailData().add(emailData);

        userRepository.save(user);

        return modelMapper.map(user, UserDto.class);
    }

    @Override
    @Transactional
    @CachePut(value = "users", key = "#root.target.getCurrentUserId()")
    public UserDto updateEmail(Long id, String email) {
        User user = findCurrentUser();

        checkEmailInDb(email);

        EmailData targetEmail = user.getEmailData().stream()
                .filter(emailData -> emailData.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Email with id " + id + " not found"));
        targetEmail.setEmail(email);

        userRepository.save(user);
        updateSecurityContext(user);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    @CacheEvict(value = "users", key = "#root.target.getCurrentUserId()")
    public void deleteEmail(Long id) {
        User user = findCurrentUser();

        if (user.getEmailData().size() <= 1) {
            throw new IllegalStateException("Cannot delete the last email");
        }

        EmailData emailToRemove = user.getEmailData().stream()
                .filter(email -> email.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Email not found"));

        String currentAuthEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean isRemovingCurrentAuthEmail = emailToRemove.getEmail().equals(currentAuthEmail);

        user.getEmailData().remove(emailToRemove);
        userRepository.save(user);

        if (isRemovingCurrentAuthEmail) {
            String newAuthEmail = user.getEmailData().get(0).getEmail();
            updateSecurityContextAfterDelete(currentAuthEmail, newAuthEmail);
        }
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

    private void updateSecurityContext(User user) {
        String firstEmail = user.getEmailData().get(0).getEmail();
        Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();

        Authentication newAuth = new UsernamePasswordAuthenticationToken(
                firstEmail,
                currentAuth.getCredentials(),
                currentAuth.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }

    private void updateSecurityContextAfterDelete(String oldEmail, String newEmail) {
        Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
        if (currentAuth != null && oldEmail.equals(currentAuth.getName())) {
            Authentication newAuth = new UsernamePasswordAuthenticationToken(
                    newEmail,
                    currentAuth.getCredentials(),
                    currentAuth.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(newAuth);
        }
    }


    private void checkEmailInDb(String email) {
        if (emailRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Почта уже существует");
        }
    }
}
