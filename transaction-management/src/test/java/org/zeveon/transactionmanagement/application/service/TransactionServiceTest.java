package org.zeveon.transactionmanagement.application.service;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.zeveon.transactionmanagement.api.dto.ReceiveTransactionRequest;
import org.zeveon.transactionmanagement.domain.command.ReceiveTransactionCommand;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Stanislav Vafin
 */
@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private CommandGateway commandGateway;

    @InjectMocks
    private TransactionService transactionService;

    @Captor
    private ArgumentCaptor<ReceiveTransactionCommand> commandCaptor;

    @Test
    public void send_ShouldSendCommandViaGateway() {
        var request = new ReceiveTransactionRequest(ZonedDateTime.now(), BigDecimal.ONE);
        when(commandGateway.send(any(ReceiveTransactionCommand.class)))
                .thenReturn(CompletableFuture.completedFuture("success"));

        var result = transactionService.send(request);

        verify(commandGateway).send(commandCaptor.capture());

        var commandSent = commandCaptor.getValue();

        assertNotNull(commandSent.getId());
        assertFalse(commandSent.getId().isEmpty());
        assertEquals(request.getDatetime(), commandSent.getDateTime());
        assertEquals(request.getAmount(), commandSent.getAmount());
        result.thenAccept(response -> assertEquals("success", response));
    }
}
