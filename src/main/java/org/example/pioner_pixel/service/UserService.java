package org.example.pioner_pixel.service;

import org.example.pioner_pixel.dto.UserSearchCriteria;
import org.example.pioner_pixel.dto.UserSearchResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.io.IOException;
import java.util.List;

public interface UserService {
    List<UserSearchResult> searchUsers(UserSearchCriteria criteria, Pageable pageable) throws IOException;

    long getTotalCount(UserSearchCriteria criteria) throws IOException;
}
