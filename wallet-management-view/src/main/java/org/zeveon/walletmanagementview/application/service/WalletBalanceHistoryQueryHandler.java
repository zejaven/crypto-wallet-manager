package org.zeveon.walletmanagementview.application.service;

import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.zeveon.walletmanagementview.domain.model.entity.WalletBalanceHistoryId;
import org.zeveon.walletmanagementview.domain.model.entity.WalletBalanceHistoryResampled;
import org.zeveon.walletmanagementview.domain.query.FindDefaultWalletHistoryByDateTimeRangeQuery;
import org.zeveon.walletmanagementview.domain.repository.WalletBalanceHistoryResampledRepository;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;

/**
 * @author Stanislav Vafin
 */
@Service
@RequiredArgsConstructor
@ProcessingGroup("wallet-processor")
public class WalletBalanceHistoryQueryHandler {

    private final WalletBalanceHistoryResampledRepository repository;

    @Value("${app.default-wallet-id}")
    private String defaultWalletId;

    @QueryHandler
    public List<WalletBalanceHistoryResampled> on(FindDefaultWalletHistoryByDateTimeRangeQuery query) {
        var atTheEndOfHourStartDatetime = query.getStartDatetime().truncatedTo(ChronoUnit.HOURS);
        var atTheEndOfHourEndDatetime = query.getEndDatetime().truncatedTo(ChronoUnit.HOURS);
        var commonZone = atTheEndOfHourStartDatetime.getZone();
        var hourlyHistories = repository.findAllById_WalletIdAndId_UpdateTimeBetweenOrderById_UpdateTimeAsc(
                defaultWalletId,
                atTheEndOfHourStartDatetime,
                atTheEndOfHourEndDatetime);
        if (hourlyHistories.isEmpty()) {
            return emptyList();
        }
        var firstHistory = hourlyHistories.iterator().next();
        var firstDateTime = firstHistory.getId().getUpdateTime().withZoneSameInstant(commonZone);
        var lastHistory = hourlyHistories.get(hourlyHistories.size() - 1);
        var lastDateTime = lastHistory.getId().getUpdateTime().withZoneSameInstant(commonZone);
        if (atTheEndOfHourStartDatetime.isBefore(firstDateTime)) {
            hourlyHistories.add(0, buildWalletBalanceHistory(atTheEndOfHourStartDatetime, firstHistory.getBalance()));
        }
        if (atTheEndOfHourEndDatetime.isAfter(lastDateTime)) {
            hourlyHistories.add(buildWalletBalanceHistory(atTheEndOfHourEndDatetime, lastHistory.getBalance()));
        }
        return hourlyHistories.stream()
                .flatMap(history -> generateHourlyHistories(
                        history.getId().getUpdateTime().truncatedTo(ChronoUnit.HOURS),
                        findNextHistory(history, hourlyHistories).getId().getUpdateTime().truncatedTo(ChronoUnit.HOURS),
                        commonZone,
                        history.getBalance()
                )).toList();
    }

    private WalletBalanceHistoryResampled findNextHistory(
            WalletBalanceHistoryResampled currentHistory,
            List<WalletBalanceHistoryResampled> histories
    ) {
        return histories.stream()
                .filter(h -> h.getId().getUpdateTime().isAfter(currentHistory.getId().getUpdateTime()))
                .findFirst()
                .orElse(buildWalletBalanceHistory(
                        currentHistory.getId().getUpdateTime().plusHours(1),
                        currentHistory.getBalance()));
    }

    private Stream<WalletBalanceHistoryResampled> generateHourlyHistories(
            ZonedDateTime startDateTime,
            ZonedDateTime endDateTime,
            ZoneId zoneId,
            BigDecimal balance
    ) {
        return Stream.iterate(startDateTime.toInstant(),
                        current -> current.isBefore(endDateTime.toInstant()),
                        current -> current.plus(Duration.ofHours(1)))
                .map(instant -> instant.atZone(zoneId))
                .map(dt -> buildWalletBalanceHistory(dt, balance));
    }

    private WalletBalanceHistoryResampled buildWalletBalanceHistory(ZonedDateTime updateTime, BigDecimal balance) {
        return WalletBalanceHistoryResampled.builder()
                .id(WalletBalanceHistoryId.builder()
                        .updateTime(updateTime)
                        .build())
                .balance(balance)
                .build();
    }
}
