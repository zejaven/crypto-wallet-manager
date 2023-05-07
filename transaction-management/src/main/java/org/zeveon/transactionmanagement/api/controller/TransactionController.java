package org.zeveon.transactionmanagement.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @PostMapping(value = "/send", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<String>> send(@RequestBody ReceiveTransactionRequest request) {
        return Mono.fromFuture(transactionService.send(request))
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity.internalServerError().body(e.getMessage())));
    }
}
