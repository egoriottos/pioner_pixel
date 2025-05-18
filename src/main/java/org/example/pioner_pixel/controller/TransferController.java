package org.example.pioner_pixel.controller;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.pioner_pixel.dto.TransferRequest;
import org.example.pioner_pixel.service.JwtService;
import org.example.pioner_pixel.service.TransferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transfer")
@Tag(name = "Transfer management", description = "Operations related to money transfers between users")
public class TransferController {
    private final TransferService transferService;
    private final JwtService jwtService;

    @Operation(
            summary = "Transfer money",
            description = "Transfers money from the authenticated user to the user with the specified ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Money transfer successful",
            content = @Content(mediaType = "text/plain")
    )
    @PostMapping("/{id}")
    public ResponseEntity<String> transferMoney(@PathVariable("id") Long id,
                                                @RequestBody @Valid TransferRequest request,
                                                @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
        Long fromUserId = jwtService.extractUserId(token);
        transferService.transferMoney(fromUserId, id, request.getAmount());
        return ResponseEntity.ok("Amount: " + request.getAmount() + " to user with id: " + id + " transfer");
    }
}
