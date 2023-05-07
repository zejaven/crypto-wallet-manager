package org.zeveon.common.model.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Stanislav Vafin
 */
@Getter
@AllArgsConstructor
public abstract class BaseEvent {

    private final String id;
}
