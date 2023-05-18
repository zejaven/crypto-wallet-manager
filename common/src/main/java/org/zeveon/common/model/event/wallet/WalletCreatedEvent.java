package org.zeveon.common.model.event.wallet;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * @author Stanislav Vafin
 */
@Getter
public class WalletCreatedEvent extends WalletEvent {

    private final ZonedDateTime initialDate;
    private final BigDecimal initialBalance;

    public WalletCreatedEvent(String id, ZonedDateTime initialDate, BigDecimal initialBalance) {
        super(id);
        this.initialDate = initialDate;
        this.initialBalance = initialBalance;
    }
}
