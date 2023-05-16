package org.zeveon.walletmanagement.application.component;

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
import org.mockito.junit.jupiter.MockitoExtension;
import org.zeveon.walletmanagement.domain.command.CreateWalletCommand;

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
public class WalletInitializerTest {

    private static final String DEFAULT_WALLET_ID = "467f7419-e9ac-4ffd-8a02-7e11310b0de0";
    private static final ZonedDateTime INITIAL_DATE = ZonedDateTime.now();
    private static final BigDecimal INITIAL_BALANCE = BigDecimal.ONE;

    @Mock
    private CommandGateway commandGateway;

    @Mock
    private EventStore eventStore;

    private WalletInitializer walletInitializer;

    @Captor
    private ArgumentCaptor<CreateWalletCommand> commandCaptor;

    @BeforeEach
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        walletInitializer = new WalletInitializer(commandGateway, eventStore);
        setDefaultValue(walletInitializer, "defaultWalletId", DEFAULT_WALLET_ID);
        setDefaultValue(walletInitializer, "initialDate", INITIAL_DATE);
        setDefaultValue(walletInitializer, "initialBalance", INITIAL_BALANCE);
    }

    private void setDefaultValue(Object target, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        var field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    @Test
    public void createDefaultWallet_ShouldCreateWallet_WhenWalletDoesNotExist() {
        when(eventStore.readEvents(any(String.class)))
                .thenReturn(DomainEventStream.of(Stream.empty()));

        walletInitializer.createDefaultWallet();

        verify(commandGateway).sendAndWait(commandCaptor.capture());

        var sentCommand = commandCaptor.getValue();
        assertEquals(sentCommand.getId(), DEFAULT_WALLET_ID);
        assertEquals(sentCommand.getInitialDate(), INITIAL_DATE);
        assertEquals(sentCommand.getInitialBalance(), INITIAL_BALANCE);
    }

    @Test
    public void createDefaultWallet_ShouldNotCreateWallet_WhenWalletExists() {
        DomainEventMessage<?> domainEventMessage = mock(DomainEventMessage.class);
        when(eventStore.readEvents(any(String.class)))
                .thenReturn(DomainEventStream.of(Stream.of(domainEventMessage)));

        walletInitializer.createDefaultWallet();

        verify(commandGateway, never()).sendAndWait(any(CreateWalletCommand.class));
    }
}
