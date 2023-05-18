package org.zeveon.walletmanagement.domain.command;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * @author Stanislav Vafin
 */
@Getter
public class CreateWalletCommand extends BaseCommand {

    private final ZonedDateTime initialDate;
    private final BigDecimal initialBalance;

    public CreateWalletCommand(String id, ZonedDateTime initialDate, BigDecimal initialBalance) {
        super(id);
        this.initialDate = initialDate;
        this.initialBalance = initialBalance;
    }
}
