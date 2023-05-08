package org.zeveon.common.model.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * @author Stanislav Vafin
 */
@Getter
@SuperBuilder
@AllArgsConstructor
public abstract class BaseEvent {

    private final String id;
}
