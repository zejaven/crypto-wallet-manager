package org.zeveon.common.model.event.wallet;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.zeveon.common.model.event.BaseEvent;

/**
 * @author Stanislav Vafin
 */
@Getter
@SuperBuilder
public abstract class WalletEvent extends BaseEvent {
}
