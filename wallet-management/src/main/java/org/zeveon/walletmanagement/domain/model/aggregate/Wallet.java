package org.zeveon.walletmanagement.domain.model.aggregate;

import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.zeveon.common.model.event.wallet.WalletBalanceUpdatedEvent;
import org.zeveon.common.model.event.wallet.WalletCreatedEvent;
import org.zeveon.walletmanagement.domain.command.CreateWalletCommand;
import org.zeveon.walletmanagement.domain.command.UpdateWalletBalanceCommand;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * @author Stanislav Vafin
 */
@Aggregate
@NoArgsConstructor
public class Wallet {

    @AggregateIdentifier
    private String id;

    private ZonedDateTime updateTime;

    private BigDecimal balance;

    @CommandHandler
    public Wallet(CreateWalletCommand command) {
        AggregateLifecycle.apply(WalletCreatedEvent.builder()
                .id(command.getId())
                .initialDate(command.getInitialDate())
                .initialBalance(command.getInitialBalance())
                .build());
    }

    @EventSourcingHandler
    public void on(WalletCreatedEvent event) {
        this.id = event.getId();
        this.updateTime = event.getInitialDate();
        this.balance = event.getInitialBalance();
    }

    @CommandHandler
    public void on(UpdateWalletBalanceCommand command) {
        AggregateLifecycle.apply(WalletBalanceUpdatedEvent.builder()
                .id(command.getId())
                .dateTime(command.getDateTime())
                .balance(this.balance.add(command.getBalance()))
                .build());
    }

    @EventSourcingHandler
    public void on(WalletBalanceUpdatedEvent event) {
        this.id = event.getId();
        this.updateTime = event.getDateTime();
        this.balance = event.getBalance();
    }
}
