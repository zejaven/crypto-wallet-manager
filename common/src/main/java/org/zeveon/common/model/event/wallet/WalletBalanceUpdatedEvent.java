package org.zeveon.common.model.event.wallet;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * @author Stanislav Vafin
 */
@Getter
public class WalletBalanceUpdatedEvent extends WalletEvent {

    private final ZonedDateTime dateTime;
    private final BigDecimal balance;

    public WalletBalanceUpdatedEvent(String id, ZonedDateTime dateTime, BigDecimal balance) {
        super(id);
        this.dateTime = dateTime;
        this.balance = balance;
    }
}
