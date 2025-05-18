package org.example.pioner_pixel.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.pioner_pixel.dto.UserSearchCriteria;
import org.example.pioner_pixel.dto.UserSearchResult;
import org.example.pioner_pixel.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
@Tag(name = "User search management",  description = "APIs for searching users with criteria and pagination")
public class UserSearchController {
    private final UserService userService;

    @Operation(
            summary = "Search users",
            description = "Searches users based on provided criteria with pagination support"
    )
    @ApiResponse(
            responseCode = "200",
            description = "List of users matching search criteria",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Page.class))
    )
    @PostMapping()
    public ResponseEntity<Page<UserSearchResult>> searchUsers(@RequestBody UserSearchCriteria criteria,
                                                              @PageableDefault(size = 10, page = 0)Pageable pageable) throws IOException {
        List<UserSearchResult> content = userService.searchUsers(criteria, pageable);

        long totalHits = userService.getTotalCount(criteria);

        return ResponseEntity.ok(new PageImpl<>(content, pageable, totalHits));
    }

}
