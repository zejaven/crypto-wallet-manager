package org.zeveon.walletmanagementview.application.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.zeveon.walletmanagementview.api.dto.WalletBalanceHistoryRequest;

import java.time.ZonedDateTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * @author Stanislav Vafin
 */
@ExtendWith(MockitoExtension.class)
public class StartDateBeforeEndDateValidatorTest {

    private static final ZonedDateTime now = ZonedDateTime.now();

    @Mock
    private ConstraintValidatorContext context;

    @InjectMocks
    private StartDateBeforeEndDateValidator validator;

    public static Stream<Arguments> inputDatesAndExpectedResult() {
        return Stream.of(
                arguments(
                        new WalletBalanceHistoryRequest(now.minusHours(1), now),
                        true
                ),
                arguments(
                        new WalletBalanceHistoryRequest(now.plusHours(1), now),
                        false
                ),
                arguments(
                        new WalletBalanceHistoryRequest(now, now),
                        false
                )
        );
    }

    @ParameterizedTest
    @MethodSource("inputDatesAndExpectedResult")
    public void isValid_ShouldReturnResult_DependingFromInputDates(WalletBalanceHistoryRequest request, boolean isValid) {
        assertEquals(validator.isValid(request, context), isValid);
    }
}
