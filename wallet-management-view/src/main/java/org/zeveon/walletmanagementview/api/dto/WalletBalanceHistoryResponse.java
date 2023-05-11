package org.zeveon.walletmanagementview.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * @author Stanislav Vafin
 */
@Data
@Builder
@Schema(description = "Wallet balance history search response data")
public class WalletBalanceHistoryResponse {

    @Schema(description = "Date and hour at the end of which the wallet balance was found", example = "2019-10-05T12:00:00+00:00")
    private ZonedDateTime dateTime;

    @Schema(description = "Wallet balance at the end of hour", example = "1000")
    private BigDecimal amount;
}
