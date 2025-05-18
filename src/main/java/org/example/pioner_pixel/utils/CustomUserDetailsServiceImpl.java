package org.example.pioner_pixel.utils;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.pioner_pixel.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService{
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("User not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmailData_Email(email)
                .orElseThrow(() -> new UsernameNotFoundException("user with email "+email+" not found"));
    }
}
