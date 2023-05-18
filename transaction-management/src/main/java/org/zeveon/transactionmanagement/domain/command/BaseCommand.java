package org.zeveon.transactionmanagement.domain.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

/**
 * @author Stanislav Vafin
 */
@Getter
@AllArgsConstructor
public abstract class BaseCommand {

    @TargetAggregateIdentifier
    private final String id;
}
