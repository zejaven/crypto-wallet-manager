package org.zeveon.transactionmanagement.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * @author Stanislav Vafin
 */
@Data
public class ReceiveTransactionRequest {

    @NotNull
    private ZonedDateTime datetime;

    @NotNull
    @Positive
    private BigDecimal amount;
}
