package org.zeveon.walletmanagement.domain.command;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * @author Stanislav Vafin
 */
@Getter
@SuperBuilder
public class UpdateWalletBalanceCommand extends BaseCommand {

    private final ZonedDateTime dateTime;
    private final BigDecimal amount;
}
