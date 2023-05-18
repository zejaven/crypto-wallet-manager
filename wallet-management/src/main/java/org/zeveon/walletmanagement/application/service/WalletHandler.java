package org.zeveon.walletmanagement.application.service;

import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
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

    @Value("${app.default-wallet-id}")
    private String defaultWalletId;

    @EventHandler
    public void on(TransactionReceivedEvent event) {
        commandGateway.send(new UpdateWalletBalanceCommand(
                defaultWalletId,
                event.getDateTime(),
                event.getAmount()
            ));
    }
}
