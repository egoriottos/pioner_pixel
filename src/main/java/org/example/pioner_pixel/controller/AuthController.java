package org.example.pioner_pixel.controller;

import jakarta.security.auth.message.AuthException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.pioner_pixel.dto.AuthenticationRequest;
import org.example.pioner_pixel.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Auth Controller", description = "Auth management")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "Auth user",
            description = "Auth to get token and then use this token in other services")
    @ApiResponse(
            responseCode = "200",
            description = "Успешная авторизация",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            name = "Пример токена",
                            value = "{ \"token\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...\" }"
                    )
            )
    )
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthenticationRequest request) throws AuthException {
        log.info("Start login user: {}", request.getEmail());
        String jwt = authService.authenticate(request.getEmail(), request.getPassword());
        log.info("Response: {}", jwt);
        return ResponseEntity.ok().body(Map.of("token", jwt));
    }
}
