package org.zeveon.transactionmanagement.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zeveon.transactionmanagement.api.dto.ReceiveTransactionRequest;
import org.zeveon.transactionmanagement.application.service.TransactionService;
import reactor.core.publisher.Mono;

/**
 * @author Stanislav Vafin
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @Operation(summary = "Send transaction", description = "Send transaction to application main wallet")
    @PostMapping(value = "/send", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> send(@RequestBody @Valid ReceiveTransactionRequest request) {
        return Mono.fromFuture(transactionService.send(request));
    }
}
