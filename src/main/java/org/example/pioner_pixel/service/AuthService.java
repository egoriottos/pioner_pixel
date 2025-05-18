package org.example.pioner_pixel.service;

import jakarta.security.auth.message.AuthException;

public interface AuthService {
    String authenticate(String email, String password) throws AuthException;
}
