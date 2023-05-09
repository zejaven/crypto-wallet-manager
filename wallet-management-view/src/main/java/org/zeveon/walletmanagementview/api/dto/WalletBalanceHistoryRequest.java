package org.zeveon.walletmanagementview.api.dto;

import lombok.Data;

import java.time.ZonedDateTime;

/**
 * @author Stanislav Vafin
 */
@Data
public class WalletBalanceHistoryRequest {

    private ZonedDateTime startDatetime;
    private ZonedDateTime endDatetime;
}
