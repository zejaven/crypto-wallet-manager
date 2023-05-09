package org.zeveon.walletmanagementview.application.service;

import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;
import org.zeveon.common.model.event.wallet.WalletBalanceUpdatedEvent;
import org.zeveon.common.model.event.wallet.WalletCreatedEvent;
import org.zeveon.walletmanagementview.domain.model.entity.WalletBalanceHistory;
import org.zeveon.walletmanagementview.domain.repository.WalletBalanceHistoryRepository;

/**
 * @author Stanislav Vafin
 */
@Service
@RequiredArgsConstructor
@ProcessingGroup("wallet-processor")
public class WalletBalanceHistoryEventHandler {

    private final WalletBalanceHistoryRepository repository;

    @EventHandler
    public void on(WalletCreatedEvent event) {
        repository.save(WalletBalanceHistory.builder()
                .walletId(event.getId())
                .updateTime(event.getInitialDate())
                .balance(event.getInitialBalance())
                .build());
    }

    @EventHandler
    public void on(WalletBalanceUpdatedEvent event) {
        repository.save(WalletBalanceHistory.builder()
                .walletId(event.getId())
                .updateTime(event.getDateTime())
                .balance(event.getBalance())
                .build());
    }
}
