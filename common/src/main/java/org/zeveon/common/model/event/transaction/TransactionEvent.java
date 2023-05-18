package org.zeveon.common.model.event.transaction;

import lombok.Getter;
import org.zeveon.common.model.event.BaseEvent;

/**
 * @author Stanislav Vafin
 */
@Getter
public abstract class TransactionEvent extends BaseEvent {

    protected TransactionEvent(String id) {
        super(id);
    }
}
