package org.example.pioner_pixel.service;

import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

public interface TransferService {
    @Transactional
    void transferMoney(Long fromUserId, Long toUserId, BigDecimal amount);
}
