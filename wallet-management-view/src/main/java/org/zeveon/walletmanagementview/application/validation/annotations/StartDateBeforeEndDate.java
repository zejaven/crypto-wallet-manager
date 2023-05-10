package org.zeveon.walletmanagementview.application.validation.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.zeveon.walletmanagementview.application.validation.StartDateBeforeEndDateValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Stanislav Vafin
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StartDateBeforeEndDateValidator.class)
public @interface StartDateBeforeEndDate {
    String message() default "Start date have to be before end date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

