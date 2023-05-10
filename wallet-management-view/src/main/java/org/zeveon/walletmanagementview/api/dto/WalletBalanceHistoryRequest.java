package org.zeveon.walletmanagementview.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.zeveon.walletmanagementview.application.validation.annotations.StartDateBeforeEndDate;

import java.time.ZonedDateTime;

/**
 * @author Stanislav Vafin
 */
@Data
@StartDateBeforeEndDate
public class WalletBalanceHistoryRequest {

    @NotNull
    private ZonedDateTime startDatetime;

    @NotNull
    private ZonedDateTime endDatetime;
}
