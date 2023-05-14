package org.zeveon.walletmanagement.application.service;

import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.zeveon.common.model.event.transaction.TransactionReceivedEvent;
import org.zeveon.walletmanagement.domain.command.UpdateWalletBalanceCommand;

/**
 * @author Stanislav Vafin
 */
@Service
@RequiredArgsConstructor
@ProcessingGroup("transaction-processor")
public class WalletHandler {

    private final CommandGateway commandGateway;

    private final EventStore eventStore;

    @Value("${app.default-wallet-id}")
    private String defaultWalletId;

    @EventHandler
    public void on(TransactionReceivedEvent event) {
        if (!aggregateExists(event.getId())) {
            commandGateway.send(UpdateWalletBalanceCommand.builder()
                    .id(defaultWalletId)
                    .dateTime(event.getDateTime())
                    .balance(event.getAmount())
                    .build());
        }
    }

    private boolean aggregateExists(String aggregateIdentifier) {
        return eventStore.readEvents(aggregateIdentifier).asStream()
                .findFirst().isPresent();
    }
}
