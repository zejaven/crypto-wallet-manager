package org.zeveon.transactionmanagement.api.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * @author Stanislav Vafin
 */
@Data
public class ReceiveTransactionRequest {

    private ZonedDateTime datetime;
    private BigDecimal amount;
}
