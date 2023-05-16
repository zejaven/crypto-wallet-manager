package org.zeveon.walletmanagement.application.service;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.DomainEventMessage;
import org.axonframework.eventsourcing.eventstore.DomainEventStream;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.zeveon.common.model.event.transaction.TransactionReceivedEvent;
import org.zeveon.walletmanagement.domain.command.UpdateWalletBalanceCommand;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Stanislav Vafin
 */
@ExtendWith(MockitoExtension.class)
public class WalletHandlerTest {

    private static final String DEFAULT_WALLET_ID = "467f7419-e9ac-4ffd-8a02-7e11310b0de0";

    @Mock
    private CommandGateway commandGateway;

    @Mock
    private EventStore eventStore;

    private WalletHandler walletHandler;

    @Captor
    private ArgumentCaptor<UpdateWalletBalanceCommand> commandCaptor;

    @BeforeEach
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        walletHandler = new WalletHandler(commandGateway, eventStore);
        var field = walletHandler.getClass().getDeclaredField("defaultWalletId");
        field.setAccessible(true);
        field.set(walletHandler, DEFAULT_WALLET_ID);
    }

    @Test
    public void on_ShouldSendUpdateWalletBalanceCommand_WhenAggregateDoesNotExist() {
        var id = "transaction_aggregate_id";
        var dateTime = ZonedDateTime.now();
        var amount = BigDecimal.ONE;

        when(eventStore.readEvents(any(String.class)))
                .thenReturn(DomainEventStream.of(Stream.empty()));

        walletHandler.on(newTransactionReceivedEvent(id, dateTime, amount));

        verify(commandGateway).send(commandCaptor.capture());

        var sentCommand = commandCaptor.getValue();
        assertEquals(sentCommand.getId(), DEFAULT_WALLET_ID);
        assertEquals(sentCommand.getDateTime(), dateTime);
        assertEquals(sentCommand.getAmount(), amount);
    }

    @Test
    public void on_ShouldNotSendUpdateWalletBalanceCommand_WhenAggregateExists() {
        var id = "transaction_aggregate_id";
        var dateTime = ZonedDateTime.now();
        var amount = BigDecimal.ONE;
        DomainEventMessage<?> domainEventMessage = Mockito.mock(DomainEventMessage.class);

        when(eventStore.readEvents(any(String.class)))
                .thenReturn(DomainEventStream.of(Stream.of(domainEventMessage)));

        walletHandler.on(newTransactionReceivedEvent(id, dateTime, amount));

        verify(commandGateway, never()).send(any(UpdateWalletBalanceCommand.class));
    }

    private TransactionReceivedEvent newTransactionReceivedEvent(String id, ZonedDateTime dateTime, BigDecimal amount) {
        return TransactionReceivedEvent.builder()
                .id(id)
                .dateTime(dateTime)
                .amount(amount)
                .build();
    }
}
