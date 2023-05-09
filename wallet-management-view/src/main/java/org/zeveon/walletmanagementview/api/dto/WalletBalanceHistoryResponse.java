package org.zeveon.walletmanagementview.api.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * @author Stanislav Vafin
 */
@Data
@Builder
public class WalletBalanceHistoryResponse {

    private ZonedDateTime dateTime;
    private BigDecimal amount;
}
