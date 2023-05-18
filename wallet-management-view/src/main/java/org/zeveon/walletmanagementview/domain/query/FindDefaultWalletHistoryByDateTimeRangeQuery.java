package org.zeveon.walletmanagementview.domain.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;

/**
 * @author Stanislav Vafin
 */
@Getter
@AllArgsConstructor
public class FindDefaultWalletHistoryByDateTimeRangeQuery {

    private ZonedDateTime startDatetime;
    private ZonedDateTime endDatetime;
}
