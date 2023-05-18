package org.zeveon.walletmanagement.domain.command;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * @author Stanislav Vafin
 */
@Getter
public class UpdateWalletBalanceCommand extends BaseCommand {

    private final ZonedDateTime dateTime;
    private final BigDecimal amount;

    public UpdateWalletBalanceCommand(String id, ZonedDateTime dateTime, BigDecimal amount) {
        super(id);
        this.dateTime = dateTime;
        this.amount = amount;
    }
}
