package org.zeveon.transactionmanagement.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.zeveon.common.model.dto.exception.BadRequestErrorMessageResponse;
import org.zeveon.common.model.dto.exception.InternalServerErrorMessageResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static java.util.Collections.singletonList;
import static java.util.Optional.ofNullable;

/**
 * @author Stanislav Vafin
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BadRequestErrorMessageResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception,
            ServletWebRequest request
    ) {
        var details = ofNullable(exception.getDetailMessageArguments())
                .map(args -> Arrays.stream(args)
                        .filter(o -> o instanceof ArrayList<?>)
                        .map(o -> (ArrayList<?>) o)
                        .flatMap(Collection::stream)
                        .map(Object::toString)
                        .toList())
                .orElse(singletonList(exception.getMessage()));
        return ResponseEntity.badRequest()
                .body(buildBadRequestErrorMessage(exception, request, exception.getStatusCode().value(), details));
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<InternalServerErrorMessageResponse> handleRuntimeException(RuntimeException exception, ServletWebRequest request) {
        var details = singletonList(ofNullable(exception.getCause())
                .map(Throwable::getMessage)
                .orElse(null));
        return ResponseEntity.internalServerError()
                .body(buildInternalServerErrorMessage(exception, request, HttpStatus.INTERNAL_SERVER_ERROR.value(), details));
    }

    private BadRequestErrorMessageResponse buildBadRequestErrorMessage(
            Exception exception,
            ServletWebRequest request,
            int statusCode,
            List<String> details
    ) {
        return BadRequestErrorMessageResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(statusCode)
                .path(request.getRequest().getServletPath())
                .exception(exception.getClass().getName())
                .message(exception.getMessage())
                .details(details)
                .build();
    }

    private InternalServerErrorMessageResponse buildInternalServerErrorMessage(
            Exception exception,
            ServletWebRequest request,
            int statusCode,
            List<String> details
    ) {
        return InternalServerErrorMessageResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(statusCode)
                .path(request.getRequest().getServletPath())
                .exception(exception.getClass().getName())
                .message(exception.getMessage())
                .details(details)
                .build();
    }
}
