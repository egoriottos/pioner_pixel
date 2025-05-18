package org.example.pioner_pixel.controller;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.pioner_pixel.dto.EmailDto;
import org.example.pioner_pixel.dto.UserDto;
import org.example.pioner_pixel.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/email")
@RestController
@Tag(name = "Email Management")
public class EmailDataController {
    private final EmailService emailService;

    @Operation(
            summary = "Add email",
            description = "add email to user"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Email add",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
    )
    @PostMapping
    public ResponseEntity<UserDto> addEmail(@RequestBody @Valid EmailDto email) {
        return ResponseEntity.ok(emailService.addEmail(email.getEmail()));
    }

    @Operation(
            summary = "Update email",
            description = "update email by id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Email update",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
    )
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateEmail(@PathVariable("id") Long id, @RequestBody @Valid EmailDto email) {
        return ResponseEntity.ok(emailService.updateEmail(id, email.getEmail()));
    }

    @Operation(
            summary = "Delete email",
            description = "Delete email by user id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Email delete",
            content = @Content(mediaType = "text/plain")
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmail(@PathVariable("id") Long id) {
        emailService.deleteEmail(id);
        return ResponseEntity.ok("email with id: " + id + " was deleted");
    }
}
