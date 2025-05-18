package org.example.pioner_pixel.service;

import io.jsonwebtoken.Claims;
import org.example.pioner_pixel.domain.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Map;
import java.util.function.Function;

public interface JwtService {

    String generateToken(User user);

    boolean isTokenValid(String token);

    Long extractUserId(String token);
}
