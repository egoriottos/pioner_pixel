package org.example.pioner_pixel.service;

import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.pioner_pixel.domain.entity.User;
import org.example.pioner_pixel.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public String authenticate(String email, String password) throws AuthException {
        User user = userRepository.findByEmailData_Email(email)
                .orElseThrow(() -> new AuthException("User not found"));

        if (!password.equals(user.getPassword())) {
            throw new AuthException("Invalid password");
        }

        return jwtService.generateToken(user);
    }
}
