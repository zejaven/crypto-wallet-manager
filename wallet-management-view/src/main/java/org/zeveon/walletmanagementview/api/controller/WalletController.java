package org.zeveon.walletmanagementview.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.responsetypes.MultipleInstancesResponseType;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.zeveon.walletmanagementview.api.dto.WalletBalanceHistoryRequest;
import org.zeveon.walletmanagementview.api.dto.WalletBalanceHistoryResponse;
import org.zeveon.walletmanagementview.domain.model.entity.WalletBalanceHistory;
import org.zeveon.walletmanagementview.domain.query.FindDefaultWalletHistoryByDateTimeRangeQuery;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Stanislav Vafin
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wallet")
public class WalletController {

    private final QueryGateway queryGateway;

    @Operation(summary = "Get wallet balance history", description = "Get balance history for application main wallet")
    @PostMapping(value = "/default/history", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Flux<WalletBalanceHistoryResponse> getDefaultWalletHistory(
            @RequestBody @Valid WalletBalanceHistoryRequest request
    ) {
        var query = new FindDefaultWalletHistoryByDateTimeRangeQuery(
                request.getStartDatetime(),
                request.getEndDatetime()
        );
        return Mono.fromFuture(queryGateway.query(query, new MultipleInstancesResponseType<>(WalletBalanceHistory.class)))
                .flatMapMany(histories -> Flux.fromStream(histories.stream()))
                .map(this::buildResponse);
    }

    private WalletBalanceHistoryResponse buildResponse(WalletBalanceHistory walletBalanceHistory) {
        return WalletBalanceHistoryResponse.builder()
                .dateTime(walletBalanceHistory.getUpdateTime())
                .amount(walletBalanceHistory.getBalance())
                .build();
    }
}
