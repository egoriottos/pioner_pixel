package org.example.pioner_pixel.controller;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.pioner_pixel.dto.PhoneDto;
import org.example.pioner_pixel.dto.UserDto;
import org.example.pioner_pixel.service.PhoneService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/phone")
@RestController
@Tag(name = "Phone management")
public class PhoneDataController {
    private final PhoneService phoneService;

    @Operation(
            summary = "Add phone",
            description = "Adds a new phone number for the user"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Phone number added successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
    )
    @PostMapping
    public ResponseEntity<UserDto> addPhone(@RequestBody @Valid PhoneDto phoneDto) {
        return ResponseEntity.ok(phoneService.addPhone(phoneDto.getPhone()));
    }

    @Operation(
            summary = "Update phone",
            description = "Updates an existing phone number by ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Phone number updated successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
    )
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updatePhone(@PathVariable("id") Long id, @RequestBody @Valid PhoneDto phoneDto) {
        return ResponseEntity.ok(phoneService.updatePhone(id, phoneDto.getPhone()));
    }

    @Operation(
            summary = "Delete phone",
            description = "Deletes a phone number by ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Phone number deleted successfully",
            content = @Content(mediaType = "text/plain")
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePhone(@PathVariable("id") Long id) {
        phoneService.deletePhone(id);
        return ResponseEntity.ok("Phone with id: " + id + " was deleted");
    }
}
