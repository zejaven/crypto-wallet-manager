package org.zeveon.walletmanagementview.application.service;

import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;
import org.zeveon.common.model.event.wallet.WalletBalanceUpdatedEvent;
import org.zeveon.common.model.event.wallet.WalletCreatedEvent;
import org.zeveon.walletmanagementview.domain.model.entity.WalletBalanceHistoryId;
import org.zeveon.walletmanagementview.domain.model.entity.WalletBalanceHistoryResampled;
import org.zeveon.walletmanagementview.domain.repository.WalletBalanceHistoryResampledRepository;

import java.time.temporal.ChronoUnit;

/**
 * @author Stanislav Vafin
 */
@Service
@RequiredArgsConstructor
@ProcessingGroup("wallet-processor")
public class WalletBalanceHistoryResampledEventHandler {

    private final WalletBalanceHistoryResampledRepository repository;

    @EventHandler
    public void on(WalletCreatedEvent event) {
        repository.save(WalletBalanceHistoryResampled.builder()
                .id(WalletBalanceHistoryId.builder()
                        .walletId(event.getId())
                        .updateTime(event.getInitialDate().truncatedTo(ChronoUnit.HOURS))
                        .build())
                .balance(event.getInitialBalance())
                .build());
    }

    @EventHandler
    public void on(WalletBalanceUpdatedEvent event) {
        repository.save(WalletBalanceHistoryResampled.builder()
                .id(WalletBalanceHistoryId.builder()
                        .walletId(event.getId())
                        .updateTime(event.getDateTime().truncatedTo(ChronoUnit.HOURS))
                        .build())
                .balance(event.getBalance())
                .build());
    }
}
