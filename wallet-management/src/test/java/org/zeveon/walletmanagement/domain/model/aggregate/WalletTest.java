package org.zeveon.walletmanagement.domain.model.aggregate;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.zeveon.common.model.FailedReason;
import org.zeveon.common.model.event.wallet.WalletBalanceUpdateFailedEvent;
import org.zeveon.common.model.event.wallet.WalletBalanceUpdatedEvent;
import org.zeveon.common.model.event.wallet.WalletCreatedEvent;
import org.zeveon.walletmanagement.domain.command.CreateWalletCommand;
import org.zeveon.walletmanagement.domain.command.UpdateWalletBalanceCommand;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * @author Stanislav Vafin
 */
public class WalletTest {

    private AggregateTestFixture<Wallet> testFixture;

    @BeforeEach
    public void setUp() {
        testFixture = new AggregateTestFixture<>(Wallet.class);
    }

    @Test
    public void testHandleCreateWalletCommand() {
        var id = UUID.randomUUID().toString();
        var initialDate = ZonedDateTime.now();
        var initialBalance = BigDecimal.ONE;

        testFixture.givenNoPriorActivity()
                .when(new CreateWalletCommand(id, initialDate, initialBalance))
                .expectEvents(new WalletCreatedEvent(id, initialDate, initialBalance));
    }

    @Test
    public void testUpdateWalletBalanceCommand() {
        var id = UUID.randomUUID().toString();
        var initialDateTime = ZonedDateTime.now();
        var initialBalance = BigDecimal.ONE;
        var updateDateTime = ZonedDateTime.now().plusHours(1L);
        var amount = BigDecimal.ONE;
        var expectedBalance = BigDecimal.valueOf(2L);

        testFixture.given(new WalletCreatedEvent(id, initialDateTime, initialBalance))
                .when(new UpdateWalletBalanceCommand(id, updateDateTime, amount))
                .expectEvents(new WalletBalanceUpdatedEvent(id, updateDateTime, expectedBalance));
    }

    @Test
    public void testUpdateWalletBalanceCommand_FailsWhenDateIsBeforeLastUpdate() {
        var id = UUID.randomUUID().toString();
        var initialDateTime = ZonedDateTime.now();
        var initialBalance = BigDecimal.ONE;
        var updateDateTime = ZonedDateTime.now().minusHours(1L);
        var amount = BigDecimal.ONE;

        testFixture.given(new WalletCreatedEvent(id, initialDateTime, initialBalance))
                .when(new UpdateWalletBalanceCommand(id, updateDateTime, amount))
                .expectEvents(new WalletBalanceUpdateFailedEvent(id, updateDateTime, FailedReason.TRANSACTIONS_MUST_BE_SEQUENTIAL));
    }
}
