package org.zeveon.common.model.event.wallet;

import lombok.Getter;
import org.zeveon.common.model.FailedReason;

import java.time.ZonedDateTime;

/**
 * @author Stanislav Vafin
 */
@Getter
public class WalletBalanceUpdateFailedEvent extends WalletEvent {

    private final ZonedDateTime dateTime;
    private final FailedReason failedReason;

    public WalletBalanceUpdateFailedEvent(String id, ZonedDateTime dateTime, FailedReason failedReason) {
        super(id);
        this.dateTime = dateTime;
        this.failedReason = failedReason;
    }
}
