package org.zeveon.walletmanagementview.domain.query;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

/**
 * @author Stanislav Vafin
 */
@Data
@Builder
public class FindDefaultWalletHistoryByDateTimeRangeQuery {

    private ZonedDateTime startDatetime;
    private ZonedDateTime endDatetime;
}
