package org.example.pioner_pixel.service;

import org.springframework.scheduling.annotation.Scheduled;

public interface PercentService {
    @Scheduled(fixedRate = 30000)
    void applyInterest();
}
