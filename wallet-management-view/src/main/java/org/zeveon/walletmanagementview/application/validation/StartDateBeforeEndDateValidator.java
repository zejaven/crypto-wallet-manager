package org.zeveon.walletmanagementview.application.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.zeveon.walletmanagementview.api.dto.WalletBalanceHistoryRequest;
import org.zeveon.walletmanagementview.application.validation.annotations.StartDateBeforeEndDate;

/**
 * @author Stanislav Vafin
 */
public class StartDateBeforeEndDateValidator implements ConstraintValidator<StartDateBeforeEndDate, WalletBalanceHistoryRequest> {

    @Override
    public boolean isValid(WalletBalanceHistoryRequest historyRequest, ConstraintValidatorContext context) {
        return historyRequest.getStartDatetime()
                .isBefore(historyRequest.getEndDatetime());
    }
}

