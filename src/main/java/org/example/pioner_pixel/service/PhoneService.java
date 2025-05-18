package org.example.pioner_pixel.service;

import org.example.pioner_pixel.dto.UserDto;

public interface PhoneService {
    UserDto addPhone(String phone);

    UserDto updatePhone(Long id, String phone);

    void deletePhone(Long id);

    Long getCurrentUserId();
}
