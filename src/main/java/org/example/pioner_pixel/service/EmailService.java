package org.example.pioner_pixel.service;

import org.example.pioner_pixel.dto.UserDto;

public interface EmailService {
    UserDto addEmail(String email);

    UserDto updateEmail(Long id, String email);

    void deleteEmail(Long id);

    Long getCurrentUserId();
}
