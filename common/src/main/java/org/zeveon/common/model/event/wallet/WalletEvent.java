package org.zeveon.common.model.event.wallet;

import lombok.Getter;
import org.zeveon.common.model.event.BaseEvent;

/**
 * @author Stanislav Vafin
 */
@Getter
public abstract class WalletEvent extends BaseEvent {

    protected WalletEvent(String id) {
        super(id);
    }
}
