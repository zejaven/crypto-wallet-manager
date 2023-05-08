package org.zeveon.common.model.event.transaction;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * @author Stanislav Vafin
 */
@Getter
@SuperBuilder
public class TransactionReceivedEvent extends TransactionEvent {

    private final ZonedDateTime dateTime;
    private final BigDecimal amount;
}
