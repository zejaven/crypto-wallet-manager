package org.zeveon.transactionmanagement.domain.command;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * @author Stanislav Vafin
 */
@Getter
public class ReceiveTransactionCommand extends BaseCommand {

    private final ZonedDateTime dateTime;

    private final BigDecimal amount;

    public ReceiveTransactionCommand(String id, ZonedDateTime dateTime, BigDecimal amount) {
        super(id);
        this.dateTime = dateTime;
        this.amount = amount;
    }
}
