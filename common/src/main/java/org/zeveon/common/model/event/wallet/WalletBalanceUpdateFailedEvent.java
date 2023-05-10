package org.zeveon.common.model.event.wallet;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.zeveon.common.model.FailedReason;

import java.time.ZonedDateTime;

/**
 * @author Stanislav Vafin
 */
@Getter
@SuperBuilder
public class WalletBalanceUpdateFailedEvent extends WalletEvent {

    private final ZonedDateTime dateTime;
    private final FailedReason failedReason;
}
