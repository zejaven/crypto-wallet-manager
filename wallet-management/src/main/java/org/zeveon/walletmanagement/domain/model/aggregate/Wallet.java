package org.zeveon.walletmanagement.domain.model.aggregate;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.zeveon.common.model.FailedReason;
import org.zeveon.common.model.event.wallet.WalletBalanceUpdateFailedEvent;
import org.zeveon.common.model.event.wallet.WalletBalanceUpdatedEvent;
import org.zeveon.common.model.event.wallet.WalletCreatedEvent;
import org.zeveon.walletmanagement.domain.command.CreateWalletCommand;
import org.zeveon.walletmanagement.domain.command.UpdateWalletBalanceCommand;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * @author Stanislav Vafin
 */
@Slf4j
@Aggregate
@NoArgsConstructor
public class Wallet {

    @AggregateIdentifier
    private String id;

    private ZonedDateTime updateTime;

    private BigDecimal balance;

    @CommandHandler
    public Wallet(CreateWalletCommand command) {
        AggregateLifecycle.apply(new WalletCreatedEvent(
                command.getId(),
                command.getInitialDate(),
                command.getInitialBalance()
        ));
    }

    @EventSourcingHandler
    public void on(WalletCreatedEvent event) {
        this.id = event.getId();
        this.updateTime = event.getInitialDate();
        this.balance = event.getInitialBalance();
    }

    @CommandHandler
    public void on(UpdateWalletBalanceCommand command) {
        if (command.getDateTime().isBefore(this.updateTime)) {
            log.info("Transaction cannot happen before the last transaction. Transaction date: %s, Transaction amount: %s"
                    .formatted(command.getDateTime(), command.getAmount()));
            AggregateLifecycle.apply(new WalletBalanceUpdateFailedEvent(
                    command.getId(),
                    command.getDateTime(),
                    FailedReason.TRANSACTIONS_MUST_BE_SEQUENTIAL
            ));
        } else {
            AggregateLifecycle.apply(new WalletBalanceUpdatedEvent(
                    command.getId(),
                    command.getDateTime(),
                    this.balance.add(command.getAmount())
            ));
        }
    }

    @EventSourcingHandler
    public void on(WalletBalanceUpdatedEvent event) {
        this.id = event.getId();
        this.updateTime = event.getDateTime();
        this.balance = event.getBalance();
    }
}
