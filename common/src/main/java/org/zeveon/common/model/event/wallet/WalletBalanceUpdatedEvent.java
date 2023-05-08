package org.zeveon.common.model.event.wallet;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * @author Stanislav Vafin
 */
@Getter
@SuperBuilder
public class WalletBalanceUpdatedEvent extends WalletEvent {

    private final ZonedDateTime dateTime;
    private final BigDecimal balance;
}
