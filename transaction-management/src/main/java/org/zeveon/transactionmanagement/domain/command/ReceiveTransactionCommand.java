package org.zeveon.transactionmanagement.domain.command;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * @author Stanislav Vafin
 */
@Getter
@SuperBuilder
public class ReceiveTransactionCommand extends BaseCommand {

    private final ZonedDateTime dateTime;
    private final BigDecimal amount;
}
