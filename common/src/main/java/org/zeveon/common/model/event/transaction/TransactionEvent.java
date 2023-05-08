package org.zeveon.common.model.event.transaction;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.zeveon.common.model.event.BaseEvent;

/**
 * @author Stanislav Vafin
 */
@Getter
@SuperBuilder
public abstract class TransactionEvent extends BaseEvent {
}
