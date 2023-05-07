package org.zeveon.transactionmanagement.application.service;

import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;
import org.zeveon.transactionmanagement.api.dto.ReceiveTransactionRequest;
import org.zeveon.transactionmanagement.domain.command.ReceiveTransactionCommand;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * @author Stanislav Vafin
 */
@Service
@RequiredArgsConstructor
public class TransactionService {

    private final CommandGateway commandGateway;

    public CompletableFuture<String> send(ReceiveTransactionRequest request) {
        return commandGateway.send(new ReceiveTransactionCommand(
                UUID.randomUUID().toString(),
                request.getDatetime(),
                request.getAmount()
        ));
    }
}
