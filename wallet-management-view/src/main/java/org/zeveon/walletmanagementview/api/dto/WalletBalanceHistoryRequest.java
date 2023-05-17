package org.zeveon.walletmanagementview.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zeveon.walletmanagementview.application.validation.annotations.StartDateBeforeEndDate;

import java.time.ZonedDateTime;

/**
 * @author Stanislav Vafin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@StartDateBeforeEndDate
@Schema(description = "Wallet balance history search request data")
public class WalletBalanceHistoryRequest {

    @NotNull
    @Schema(description = "Start date and time", example = "2019-10-05T12:48:01+00:00")
    private ZonedDateTime startDatetime;

    @NotNull
    @Schema(description = "End date and time", example = "2019-10-05T17:48:02+00:00")
    private ZonedDateTime endDatetime;
}
