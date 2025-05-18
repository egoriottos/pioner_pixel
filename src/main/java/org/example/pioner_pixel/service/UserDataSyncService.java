package org.example.pioner_pixel.service;

import org.example.pioner_pixel.domain.document.UserDocument;
import org.example.pioner_pixel.domain.entity.User;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

public interface UserDataSyncService {
    @Transactional
    @Scheduled(fixedRate = 60000)
    void syncUsersToElastic();

    UserDocument convertToDocument(User user);
}
