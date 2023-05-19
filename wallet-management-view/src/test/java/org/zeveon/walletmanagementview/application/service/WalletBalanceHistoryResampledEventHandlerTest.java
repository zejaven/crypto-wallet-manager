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
import org.zeveon.walletmanagementview.domain.model.entity.WalletBalanceHistoryId;
import org.zeveon.walletmanagementview.domain.model.entity.WalletBalanceHistoryResampled;
import org.zeveon.walletmanagementview.domain.repository.WalletBalanceHistoryResampledRepository;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Stanislav Vafin
 */
@ExtendWith(MockitoExtension.class)
public class WalletBalanceHistoryResampledEventHandlerTest {

    @Mock
    private WalletBalanceHistoryResampledRepository repository;

    @InjectMocks
    private WalletBalanceHistoryResampledEventHandler handler;

    @Captor
    private ArgumentCaptor<WalletBalanceHistoryResampled> entityCaptor;

    @Test
    public void onWalletCreatedEvent_savesWalletBalanceHistoryResampled() {
        var id = "wallet_aggregate_id";
        var initialDate = ZonedDateTime.now();
        var initialBalance = BigDecimal.ONE;

        when(repository.save(any(WalletBalanceHistoryResampled.class)))
                .thenReturn(buildWalletBalanceHistoryResampled(id, initialDate, initialBalance));

        handler.on(new WalletCreatedEvent(id, initialDate, initialBalance));

        verify(repository).save(entityCaptor.capture());

        var savedEntity = entityCaptor.getValue();
        assertEquals(savedEntity.getId().getWalletId(), id);
        assertEquals(savedEntity.getId().getUpdateTime(), initialDate.truncatedTo(ChronoUnit.HOURS));
        assertEquals(savedEntity.getBalance(), initialBalance);
    }

    @Test
    public void onWalletWalletBalanceUpdatedEvent_savesWalletBalanceHistoryResampled() {
        var id = "wallet_aggregate_id";
        var dateTime = ZonedDateTime.now();
        var balance = BigDecimal.ONE;

        when(repository.save(any(WalletBalanceHistoryResampled.class)))
                .thenReturn(buildWalletBalanceHistoryResampled(id, dateTime, balance));

        handler.on(new WalletBalanceUpdatedEvent(id, dateTime, balance));

        verify(repository).save(entityCaptor.capture());

        var savedEntity = entityCaptor.getValue();
        assertEquals(savedEntity.getId().getWalletId(), id);
        assertEquals(savedEntity.getId().getUpdateTime(), dateTime.truncatedTo(ChronoUnit.HOURS));
        assertEquals(savedEntity.getBalance(), balance);
    }

    private WalletBalanceHistoryResampled buildWalletBalanceHistoryResampled(String id, ZonedDateTime initialDate, BigDecimal initialBalance) {
        return WalletBalanceHistoryResampled.builder()
                .id(WalletBalanceHistoryId.builder()
                        .walletId(id)
                        .updateTime(initialDate)
                        .build())
                .balance(initialBalance)
                .build();
    }
}
