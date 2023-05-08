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
public class CreateWalletCommand extends BaseCommand {

    private final ZonedDateTime initialDate;
    private final BigDecimal initialBalance;
}
