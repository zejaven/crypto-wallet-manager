package org.zeveon.transactionmanagement.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * @author Stanislav Vafin
 */
@Data
@Schema(description = "Transaction request data")
public class ReceiveTransactionRequest {

    @NotNull
    @Schema(description = "Date and time of sending a transaction", example = "2019-10-05T14:45:05+00:00")
    private ZonedDateTime datetime;

    @NotNull
    @Positive
    @Schema(description = "Amount of BTC to send", example = "1.1")
    private BigDecimal amount;
}
