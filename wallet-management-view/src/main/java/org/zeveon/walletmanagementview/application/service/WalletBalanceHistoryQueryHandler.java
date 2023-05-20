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
        var hourlyHistories = repository
                .findAllById_WalletIdAndId_UpdateTimeBetweenOrderById_UpdateTimeAsc(
                        defaultWalletId,
                        atTheEndOfHourStartDatetime,
                        atTheEndOfHourEndDatetime
                );
        return hourlyHistories.isEmpty()
                ? emptyList()
                : generateHourlyHistories(hourlyHistories, atTheEndOfHourStartDatetime, atTheEndOfHourEndDatetime);
    }

    /**
     * Generate hourly histories between existing history records
     *
     * @param hourlyHistories existing history records
     * @param startDateTime   date and time at the beginning of history records
     * @param endDateTime     date and time at the end of history records
     * @return list of hourly histories
     */
    private List<WalletBalanceHistoryResampled> generateHourlyHistories(
            List<WalletBalanceHistoryResampled> hourlyHistories,
            ZonedDateTime startDateTime,
            ZonedDateTime endDateTime
    ) {
        var commonZone = startDateTime.getZone();
        encloseHistoriesWithRequestDates(hourlyHistories, startDateTime, endDateTime, commonZone);
        return hourlyHistories.stream()
                .flatMap(history -> generateHourlyHistories(
                        history.getId().getUpdateTime().truncatedTo(ChronoUnit.HOURS),
                        findNextHistory(history, hourlyHistories).getId().getUpdateTime().truncatedTo(ChronoUnit.HOURS),
                        commonZone,
                        history.getBalance()
                )).toList();
    }

    /**
     * Add missing history records at the beginning and end if necessary
     *
     * @param hourlyHistories list of hourly histories
     * @param startDateTime   start date and time
     * @param endDateTime     end date and time
     * @param commonZone      common timezone
     */
    private void encloseHistoriesWithRequestDates(
            List<WalletBalanceHistoryResampled> hourlyHistories,
            ZonedDateTime startDateTime,
            ZonedDateTime endDateTime,
            ZoneId commonZone
    ) {
        var firstHistory = hourlyHistories.iterator().next();
        var firstDateTime = firstHistory.getId().getUpdateTime().withZoneSameInstant(commonZone);
        var lastHistory = hourlyHistories.get(hourlyHistories.size() - 1);
        var lastDateTime = lastHistory.getId().getUpdateTime().withZoneSameInstant(commonZone);

        if (startDateTime.isBefore(firstDateTime)) {
            hourlyHistories.add(0, buildWalletBalanceHistory(startDateTime, firstHistory.getBalance()));
        }
        if (endDateTime.isAfter(lastDateTime)) {
            hourlyHistories.add(buildWalletBalanceHistory(endDateTime, lastHistory.getBalance()));
        }
    }

    /**
     * Find the next history record in the list
     *
     * @param currentHistory current history record
     * @param histories      full list of hourly history records
     * @return history record
     */
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

    /**
     * Generate a stream of hourly histories between start and end datetime
     *
     * @param startDateTime start date and time
     * @param endDateTime   end date and time
     * @param commonZone    timezone of the result history record date and time
     * @param balance       balance of the result history records
     * @return stream of hourly histories
     */
    private Stream<WalletBalanceHistoryResampled> generateHourlyHistories(
            ZonedDateTime startDateTime,
            ZonedDateTime endDateTime,
            ZoneId commonZone,
            BigDecimal balance
    ) {
        return Stream.iterate(startDateTime.toInstant(),
                        current -> current.isBefore(endDateTime.toInstant()),
                        current -> current.plus(Duration.ofHours(1)))
                .map(instant -> instant.atZone(commonZone))
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
