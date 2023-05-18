package org.zeveon.walletmanagement.application.component;

import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.zeveon.walletmanagement.domain.command.CreateWalletCommand;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * @author Stanislav Vafin
 */
@Component
@RequiredArgsConstructor
@ProcessingGroup("transaction-processor")
public class WalletInitializer {

    private final CommandGateway commandGateway;

    private final EventStore eventStore;

    @Value("${app.default-wallet-id}")
    private String defaultWalletId;

    @Value("${app.initial-date}")
    private ZonedDateTime initialDate;

    @Value("${app.initial-balance}")
    private BigDecimal initialBalance;

    @EventListener(ApplicationReadyEvent.class)
    public void createDefaultWallet() {
        if (!aggregateExists(defaultWalletId)) {
            commandGateway.sendAndWait(new CreateWalletCommand(
                    defaultWalletId,
                    initialDate,
                    initialBalance
            ));
        }
    }

    private boolean aggregateExists(String aggregateIdentifier) {
        return eventStore.readEvents(aggregateIdentifier).asStream()
                .findFirst().isPresent();
    }
}
