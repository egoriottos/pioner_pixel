package org.example.pioner_pixel.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.pioner_pixel.domain.entity.Account;
import org.example.pioner_pixel.repository.AccountRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PercentServiceImpl implements PercentService{
    private final AccountRepository accountRepository;
    private static final BigDecimal INTEREST_RATE = new BigDecimal("1.10");
    private static final BigDecimal MAX_INCREASE = new BigDecimal("3.07");

    @Scheduled(fixedRate = 30000)
    @Override
    public void applyInterest() {
        List<Account> accounts = accountRepository.findAllWithoutUser();

        accounts.forEach(account -> {
            BigDecimal maxAllowed = account.getBalance().multiply(MAX_INCREASE);
            BigDecimal newBalance = account.getBalance().multiply(INTEREST_RATE)
                    .setScale(2, RoundingMode.HALF_UP);

            if (newBalance.compareTo(maxAllowed) > 0) {
                newBalance = maxAllowed;
            }

            if (newBalance.compareTo(account.getBalance()) > 0) {
                account.setBalance(newBalance);
                log.info("Balance increased for account ID: {}", account.getId());
            }
        });
    }
}
