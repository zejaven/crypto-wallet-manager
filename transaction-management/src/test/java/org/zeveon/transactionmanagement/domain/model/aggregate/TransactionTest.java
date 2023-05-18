package org.zeveon.transactionmanagement.domain.model.aggregate;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.zeveon.common.model.event.transaction.TransactionReceivedEvent;
import org.zeveon.transactionmanagement.domain.command.ReceiveTransactionCommand;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * @author Stanislav Vafin
 */
public class TransactionTest {

    private AggregateTestFixture<Transaction> testFixture;

    @BeforeEach
    public void setUp() {
        testFixture = new AggregateTestFixture<>(Transaction.class);
    }

    @Test
    public void testHandleReceiveTransactionCommand() {
        var id = UUID.randomUUID().toString();
        var amount = BigDecimal.ONE;
        var dateTime = ZonedDateTime.now();

        testFixture.givenNoPriorActivity()
                .when(new ReceiveTransactionCommand(id, dateTime, amount))
                .expectEvents(new TransactionReceivedEvent(id, dateTime, amount));
    }
}
