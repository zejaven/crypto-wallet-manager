package org.zeveon.walletmanagement.domain.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

/**
 * @author Stanislav Vafin
 */
@Getter
@SuperBuilder
@AllArgsConstructor
public abstract class BaseCommand {

    @TargetAggregateIdentifier
    private final String id;
}
