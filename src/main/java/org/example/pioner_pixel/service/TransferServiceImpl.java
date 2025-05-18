package org.example.pioner_pixel.service;

import lombok.RequiredArgsConstructor;
import org.example.pioner_pixel.domain.entity.Account;
import org.example.pioner_pixel.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService{
    private final AccountRepository accountRepository;

    @Transactional
    @Override
    public void transferMoney(Long fromUserId, Long toUserId, BigDecimal amount) {
        if (fromUserId.equals(toUserId)) {
            throw new IllegalArgumentException("Нельзя переводить самому себе");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Сумма должна быть положительной");
        }

        Optional<Account> fromAccount = accountRepository.findByUserIdWithLock(fromUserId);
        Optional<Account> toAccount = accountRepository.findByUserIdWithLock(toUserId);

        if (fromAccount.get().getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Недостаточно средств");
        }

        fromAccount.get().setBalance(fromAccount.get().getBalance().subtract(amount));
        toAccount.get().setBalance(toAccount.get().getBalance().add(amount));
    }
}
