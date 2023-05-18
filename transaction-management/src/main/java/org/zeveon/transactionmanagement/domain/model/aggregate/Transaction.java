package org.zeveon.transactionmanagement.domain.model.aggregate;

import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.zeveon.common.model.event.transaction.TransactionReceivedEvent;
import org.zeveon.transactionmanagement.domain.command.ReceiveTransactionCommand;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * @author Stanislav Vafin
 */
@Aggregate
@NoArgsConstructor
@SuppressWarnings("FieldCanBeLocal")
public class Transaction {

    @AggregateIdentifier
    private String id;

    private ZonedDateTime dateTime;

    private BigDecimal amount;

    @CommandHandler
    public Transaction(ReceiveTransactionCommand command) {
        AggregateLifecycle.apply(new TransactionReceivedEvent(
                command.getId(),
                command.getDateTime(),
                command.getAmount()
        ));
    }

    @EventSourcingHandler
    public void on(TransactionReceivedEvent event) {
        this.id = event.getId();
        this.dateTime = event.getDateTime();
        this.amount = event.getAmount();
    }
}
