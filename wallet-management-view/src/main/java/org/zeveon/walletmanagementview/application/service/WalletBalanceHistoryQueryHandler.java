package org.zeveon.walletmanagementview.application.service;

import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.zeveon.walletmanagementview.domain.model.entity.WalletBalanceHistory;
import org.zeveon.walletmanagementview.domain.query.FindDefaultWalletHistoryByDateTimeRangeQuery;
import org.zeveon.walletmanagementview.domain.repository.WalletBalanceHistoryRepository;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;

/**
 * @author Stanislav Vafin
 */
@Service
@RequiredArgsConstructor
@ProcessingGroup("wallet-processor")
public class WalletBalanceHistoryQueryHandler {

    private final WalletBalanceHistoryRepository repository;

    @Value("${app.default-wallet-id}")
    private String defaultWalletId;

    @QueryHandler
    public List<WalletBalanceHistory> on(FindDefaultWalletHistoryByDateTimeRangeQuery query) {
        var atTheEndOfHourStartDatetime = query.getStartDatetime().truncatedTo(ChronoUnit.HOURS);
        var atTheEndOfHourEndDatetime = query.getEndDatetime().truncatedTo(ChronoUnit.HOURS).plusHours(1L);
        var commonZone = atTheEndOfHourStartDatetime.getZone();
        var oneHourStepDateTimeRange = getOneHourStepDateTimeRange(
                atTheEndOfHourStartDatetime,
                atTheEndOfHourEndDatetime,
                commonZone);
        var rangedBetweenDatesHistory = repository.findAllByWalletIdAndUpdateTimeBetweenOrderByUpdateTimeAscIdAsc(
                defaultWalletId,
                atTheEndOfHourStartDatetime,
                atTheEndOfHourEndDatetime);
        var currentBalance = BigDecimal.valueOf(-1);
        if (!rangedBetweenDatesHistory.isEmpty()) {
            currentBalance = rangedBetweenDatesHistory.iterator().next().getBalance();
        } else {
            return emptyList();
        }
        rangedBetweenDatesHistory.forEach(wbh -> oneHourStepDateTimeRange.put(truncateToHour(wbh, commonZone), wbh.getBalance()));
        for (var entry : oneHourStepDateTimeRange.entrySet()) {
            if (isNegative(entry.getValue())) {
                entry.setValue(currentBalance);
            } else {
                currentBalance = entry.getValue();
            }
        }
        return oneHourStepDateTimeRange.entrySet().stream()
                .map(this::buildWalletBalanceHistory)
                .toList();
    }

    private ZonedDateTime truncateToHour(WalletBalanceHistory walletBalanceHistory, ZoneId zoneId) {
        return walletBalanceHistory.getUpdateTime()
                .truncatedTo(ChronoUnit.HOURS)
                .withZoneSameInstant(zoneId);
    }

    private WalletBalanceHistory buildWalletBalanceHistory(Map.Entry<ZonedDateTime, BigDecimal> e) {
        return WalletBalanceHistory.builder()
                .updateTime(e.getKey())
                .balance(e.getValue())
                .build();
    }

    private boolean isNegative(BigDecimal value) {
        return value.signum() == -1;
    }

    private Map<ZonedDateTime, BigDecimal> getOneHourStepDateTimeRange(
            ZonedDateTime atTheEndOfHourStartDatetime,
            ZonedDateTime atTheEndOfHourEndDatetime,
            ZoneId zoneId
    ) {
        return Stream.iterate(atTheEndOfHourStartDatetime.toInstant(),
                        current -> current.isBefore(atTheEndOfHourEndDatetime.toInstant()),
                        current -> current.plus(Duration.ofHours(1L)))
                .map(instant -> instant.atZone(zoneId))
                .collect(Collectors.toMap(e -> e, e -> BigDecimal.valueOf(-1), (v1, v2) -> v1, TreeMap::new));
    }
}
