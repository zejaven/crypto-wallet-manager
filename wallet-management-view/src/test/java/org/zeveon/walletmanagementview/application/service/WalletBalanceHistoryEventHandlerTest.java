package org.zeveon.walletmanagementview.application.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.zeveon.common.model.event.wallet.WalletBalanceUpdatedEvent;
import org.zeveon.common.model.event.wallet.WalletCreatedEvent;
import org.zeveon.walletmanagementview.domain.model.entity.WalletBalanceHistory;
import org.zeveon.walletmanagementview.domain.repository.WalletBalanceHistoryRepository;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Stanislav Vafin
 */
@ExtendWith(MockitoExtension.class)
public class WalletBalanceHistoryEventHandlerTest {

    @Mock
    private WalletBalanceHistoryRepository repository;

    @InjectMocks
    private WalletBalanceHistoryEventHandler handler;

    @Captor
    private ArgumentCaptor<WalletBalanceHistory> entityCaptor;

    @Test
    public void onWalletCreatedEvent_savesWalletBalanceHistory() {
        var id = "wallet_aggregate_id";
        var initialDate = ZonedDateTime.now();
        var initialBalance = BigDecimal.ONE;

        when(repository.save(any(WalletBalanceHistory.class)))
                .thenReturn(newWalletBalanceHistory(id, initialDate, initialBalance));

        handler.on(new WalletCreatedEvent(id, initialDate, initialBalance));

        verify(repository).save(entityCaptor.capture());

        var savedEntity = entityCaptor.getValue();
        assertEquals(savedEntity.getWalletId(), id);
        assertEquals(savedEntity.getUpdateTime(), initialDate);
        assertEquals(savedEntity.getBalance(), initialBalance);
    }

    @Test
    public void onWalletWalletBalanceUpdatedEvent_savesWalletBalanceHistory() {
        var id = "wallet_aggregate_id";
        var dateTime = ZonedDateTime.now();
        var balance = BigDecimal.ONE;

        when(repository.save(any(WalletBalanceHistory.class)))
                .thenReturn(newWalletBalanceHistory(id, dateTime, balance));

        handler.on(new WalletBalanceUpdatedEvent(id, dateTime, balance));

        verify(repository).save(entityCaptor.capture());

        var savedEntity = entityCaptor.getValue();
        assertEquals(savedEntity.getWalletId(), id);
        assertEquals(savedEntity.getUpdateTime(), dateTime);
        assertEquals(savedEntity.getBalance(), balance);
    }

    private WalletBalanceHistory newWalletBalanceHistory(String id, ZonedDateTime initialDate, BigDecimal initialBalance) {
        return WalletBalanceHistory.builder()
                .walletId(id)
                .updateTime(initialDate)
                .balance(initialBalance)
                .build();
    }
}
