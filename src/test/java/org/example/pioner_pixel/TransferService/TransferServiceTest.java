package org.example.pioner_pixel.TransferService;

import org.example.pioner_pixel.domain.entity.Account;
import org.example.pioner_pixel.repository.AccountRepository;
import org.example.pioner_pixel.service.TransferServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransferServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private TransferServiceImpl transferService;

    @Test
    void transferMoney_shouldThrowWhenTransferToSelf() {
        assertThrows(IllegalArgumentException.class,
                () -> transferService.transferMoney(1L, 1L, BigDecimal.TEN),
                "Нельзя переводить самому себе");
    }

    @Test
    void transferMoney_shouldThrowWhenAmountNotPositive() {
        assertThrows(IllegalArgumentException.class,
                () -> transferService.transferMoney(1L, 2L, BigDecimal.ZERO),
                "Сумма должна быть положительной");
    }

    @Test
    void transferMoney_shouldThrowWhenInsufficientFunds() {
        Account fromAccount = new Account();
        fromAccount.setBalance(BigDecimal.ONE);
        Account toAccount = new Account();

        when(accountRepository.findByUserIdWithLock(1L)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findByUserIdWithLock(2L)).thenReturn(Optional.of(toAccount));

        assertThrows(IllegalArgumentException.class,
                () -> transferService.transferMoney(1L, 2L, BigDecimal.TEN),
                "Недостаточно средств");
    }

    @Test
    void transferMoney_shouldTransferSuccessfully() {
        BigDecimal amount = BigDecimal.TEN;
        Account fromAccount = new Account();
        fromAccount.setBalance(new BigDecimal("100"));
        Account toAccount = new Account();
        toAccount.setBalance(BigDecimal.ZERO);

        when(accountRepository.findByUserIdWithLock(1L)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findByUserIdWithLock(2L)).thenReturn(Optional.of(toAccount));

        transferService.transferMoney(1L, 2L, amount);

        assertEquals(new BigDecimal("90"), fromAccount.getBalance());
        assertEquals(amount, toAccount.getBalance());
    }
}
