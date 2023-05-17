package org.zeveon.walletmanagementview.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.zeveon.walletmanagementview.domain.model.entity.WalletBalanceHistory;
import org.zeveon.walletmanagementview.domain.query.FindDefaultWalletHistoryByDateTimeRangeQuery;
import org.zeveon.walletmanagementview.domain.repository.WalletBalanceHistoryRepository;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Stanislav Vafin
 */
@ExtendWith(MockitoExtension.class)
public class WalletBalanceHistoryQueryHandlerTest {

    private static final String DEFAULT_WALLET_ID = "467f7419-e9ac-4ffd-8a02-7e11310b0de0";

    @Mock
    private WalletBalanceHistoryRepository repository;

    @InjectMocks
    private WalletBalanceHistoryQueryHandler handler;

    @BeforeEach
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        handler = new WalletBalanceHistoryQueryHandler(repository);
        var field = handler.getClass().getDeclaredField("defaultWalletId");
        field.setAccessible(true);
        field.set(handler, DEFAULT_WALLET_ID);
    }

    public static Stream<Arguments> dateTimeRangeQueryInputDataAndExpectedResult() {
        return Stream.of(
                arguments(
                        ZonedDateTime.parse("2019-10-05T12:00:00+00:00"),
                        ZonedDateTime.parse("2019-10-05T13:00:00+00:00"),
                        emptyList(),
                        emptyList()
                ),
                arguments(
                        ZonedDateTime.parse("2019-10-05T13:00:00+00:00"),
                        ZonedDateTime.parse("2019-10-05T13:00:01+00:00"),
                        List.of(
                                newWalletBalanceHistory(DEFAULT_WALLET_ID, ZonedDateTime.parse("2019-10-05T13:00:00+00:00"), BigDecimal.valueOf(1000))
                        ),
                        List.of(
                                newWalletBalanceHistory(null, ZonedDateTime.parse("2019-10-05T13:00:00+00:00"), BigDecimal.valueOf(1000))
                        )
                ),
                arguments(
                        ZonedDateTime.parse("2019-10-05T12:00:00+00:00"),
                        ZonedDateTime.parse("2019-10-05T13:00:00+00:00"),
                        List.of(
                                newWalletBalanceHistory(DEFAULT_WALLET_ID, ZonedDateTime.parse("2019-10-05T13:00:00+00:00"), BigDecimal.valueOf(1000))
                        ),
                        List.of(
                                newWalletBalanceHistory(null, ZonedDateTime.parse("2019-10-05T12:00:00+00:00"), BigDecimal.valueOf(1000)),
                                newWalletBalanceHistory(null, ZonedDateTime.parse("2019-10-05T13:00:00+00:00"), BigDecimal.valueOf(1000))
                        )
                ),
                arguments(
                        ZonedDateTime.parse("2019-10-05T12:59:59+00:00"),
                        ZonedDateTime.parse("2019-10-05T13:59:59+00:00"),
                        List.of(
                                newWalletBalanceHistory(DEFAULT_WALLET_ID, ZonedDateTime.parse("2019-10-05T13:00:00+00:00"), BigDecimal.valueOf(1000))
                        ),
                        List.of(
                                newWalletBalanceHistory(null, ZonedDateTime.parse("2019-10-05T12:00:00+00:00"), BigDecimal.valueOf(1000)),
                                newWalletBalanceHistory(null, ZonedDateTime.parse("2019-10-05T13:00:00+00:00"), BigDecimal.valueOf(1000))
                        )
                ),
                arguments(
                        ZonedDateTime.parse("2019-10-05T12:59:59+00:00"),
                        ZonedDateTime.parse("2019-10-05T14:00:00+00:00"),
                        List.of(
                                newWalletBalanceHistory(DEFAULT_WALLET_ID, ZonedDateTime.parse("2019-10-05T13:00:00+00:00"), BigDecimal.valueOf(1000)),
                                newWalletBalanceHistory(DEFAULT_WALLET_ID, ZonedDateTime.parse("2019-10-05T14:45:05+00:00"), BigDecimal.valueOf(1001.1))
                        ),
                        List.of(
                                newWalletBalanceHistory(null, ZonedDateTime.parse("2019-10-05T12:00:00+00:00"), BigDecimal.valueOf(1000)),
                                newWalletBalanceHistory(null, ZonedDateTime.parse("2019-10-05T13:00:00+00:00"), BigDecimal.valueOf(1000)),
                                newWalletBalanceHistory(null, ZonedDateTime.parse("2019-10-05T14:00:00+00:00"), BigDecimal.valueOf(1001.1))
                        )
                ),
                arguments(
                        ZonedDateTime.parse("2019-10-05T19:00:00+07:00"),
                        ZonedDateTime.parse("2019-10-05T21:00:00+07:00"),
                        List.of(
                                newWalletBalanceHistory(DEFAULT_WALLET_ID, ZonedDateTime.parse("2019-10-05T13:00:00+00:00"), BigDecimal.valueOf(1000)),
                                newWalletBalanceHistory(DEFAULT_WALLET_ID, ZonedDateTime.parse("2019-10-05T14:45:05+00:00"), BigDecimal.valueOf(1001.1))
                        ),
                        List.of(
                                newWalletBalanceHistory(null, ZonedDateTime.parse("2019-10-05T19:00:00+07:00"), BigDecimal.valueOf(1000)),
                                newWalletBalanceHistory(null, ZonedDateTime.parse("2019-10-05T20:00:00+07:00"), BigDecimal.valueOf(1000)),
                                newWalletBalanceHistory(null, ZonedDateTime.parse("2019-10-05T21:00:00+07:00"), BigDecimal.valueOf(1001.1))
                        )
                ),
                arguments(
                        ZonedDateTime.parse("2019-10-05T19:00:00+07:00"),
                        ZonedDateTime.parse("2019-10-05T09:00:00-05:00"),
                        List.of(
                                newWalletBalanceHistory(DEFAULT_WALLET_ID, ZonedDateTime.parse("2019-10-05T13:00:00+00:00"), BigDecimal.valueOf(1000)),
                                newWalletBalanceHistory(DEFAULT_WALLET_ID, ZonedDateTime.parse("2019-10-05T14:45:05+00:00"), BigDecimal.valueOf(1001.1))
                        ),
                        List.of(
                                newWalletBalanceHistory(null, ZonedDateTime.parse("2019-10-05T19:00:00+07:00"), BigDecimal.valueOf(1000)),
                                newWalletBalanceHistory(null, ZonedDateTime.parse("2019-10-05T20:00:00+07:00"), BigDecimal.valueOf(1000)),
                                newWalletBalanceHistory(null, ZonedDateTime.parse("2019-10-05T21:00:00+07:00"), BigDecimal.valueOf(1001.1))
                        )
                ),
                arguments(
                        ZonedDateTime.parse("2019-10-05T12:48:01+00:00"),
                        ZonedDateTime.parse("2019-10-05T17:48:02+00:00"),
                        List.of(
                                newWalletBalanceHistory(DEFAULT_WALLET_ID, ZonedDateTime.parse("2019-10-05T13:00:00+00:00"), BigDecimal.valueOf(1000)),
                                newWalletBalanceHistory(DEFAULT_WALLET_ID, ZonedDateTime.parse("2019-10-05T14:45:05+00:00"), BigDecimal.valueOf(1001.1)),
                                newWalletBalanceHistory(DEFAULT_WALLET_ID, ZonedDateTime.parse("2019-10-05T17:59:59+00:00"), BigDecimal.valueOf(1153.15344839))
                        ),
                        List.of(
                                newWalletBalanceHistory(null, ZonedDateTime.parse("2019-10-05T12:00:00+00:00"), BigDecimal.valueOf(1000)),
                                newWalletBalanceHistory(null, ZonedDateTime.parse("2019-10-05T13:00:00+00:00"), BigDecimal.valueOf(1000)),
                                newWalletBalanceHistory(null, ZonedDateTime.parse("2019-10-05T14:00:00+00:00"), BigDecimal.valueOf(1001.1)),
                                newWalletBalanceHistory(null, ZonedDateTime.parse("2019-10-05T15:00:00+00:00"), BigDecimal.valueOf(1001.1)),
                                newWalletBalanceHistory(null, ZonedDateTime.parse("2019-10-05T16:00:00+00:00"), BigDecimal.valueOf(1001.1)),
                                newWalletBalanceHistory(null, ZonedDateTime.parse("2019-10-05T17:00:00+00:00"), BigDecimal.valueOf(1153.15344839))
                        )
                )
        );
    }

    @ParameterizedTest
    @MethodSource("dateTimeRangeQueryInputDataAndExpectedResult")
    public void onFindDefaultWalletHistoryByDateTimeRangeQuery_returnsExpectedResult(
            ZonedDateTime startDateTime,
            ZonedDateTime endDateTime,
            List<WalletBalanceHistory> foundHistories,
            List<WalletBalanceHistory> expectedResult
    ) {
        var query = newFindDefaultWalletHistoryByDateTimeRangeQuery(startDateTime, endDateTime);

        when(repository.findAllByWalletIdAndUpdateTimeBetweenOrderByUpdateTimeAscIdAsc(
                anyString(),
                any(ZonedDateTime.class),
                any(ZonedDateTime.class))
        ).thenReturn(foundHistories);

        var result = handler.on(query);

        verify(repository).findAllByWalletIdAndUpdateTimeBetweenOrderByUpdateTimeAscIdAsc(
                anyString(),
                any(ZonedDateTime.class),
                any(ZonedDateTime.class));

        assertNotNull(result);
        assertEquals(result.size(), expectedResult.size());
        assertTrue(result.containsAll(expectedResult));
    }

    private FindDefaultWalletHistoryByDateTimeRangeQuery newFindDefaultWalletHistoryByDateTimeRangeQuery(
            ZonedDateTime startDateTime,
            ZonedDateTime endDateTime
    ) {
        return FindDefaultWalletHistoryByDateTimeRangeQuery.builder()
                .startDatetime(startDateTime)
                .endDatetime(endDateTime)
                .build();
    }

    private static WalletBalanceHistory newWalletBalanceHistory(String id, ZonedDateTime initialDate, BigDecimal initialBalance) {
        return WalletBalanceHistory.builder()
                .walletId(id)
                .updateTime(initialDate)
                .balance(initialBalance)
                .build();
    }
}
