package org.zeveon.walletmanagementview.domain.query;

import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;

/**
 * @author Stanislav Vafin
 */
@Getter
@Builder
public class FindDefaultWalletHistoryByDateTimeRangeQuery {

    private ZonedDateTime startDatetime;
    private ZonedDateTime endDatetime;
}
