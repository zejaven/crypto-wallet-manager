package org.zeveon.common.model.event.transaction;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * @author Stanislav Vafin
 */
@Getter
public class TransactionReceivedEvent extends TransactionEvent {

    private final ZonedDateTime dateTime;
    private final BigDecimal amount;

    public TransactionReceivedEvent(String id, ZonedDateTime dateTime, BigDecimal amount) {
        super(id);
        this.dateTime = dateTime;
        this.amount = amount;
    }
}
