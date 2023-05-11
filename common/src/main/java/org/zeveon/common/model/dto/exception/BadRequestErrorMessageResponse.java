package org.zeveon.common.model.dto.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Stanislav Vafin
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BadRequestErrorMessageResponse {

    @Schema(description = "Date and time when error happened", example = "2023-05-10T16:25:32.7170462")
    private LocalDateTime timestamp;

    @Schema(description = "Response status", example = "400")
    private int status;

    @Schema(description = "Path to api method where error happened", example = "/api/transaction/send")
    private String path;

    @Schema(description = "Exception name", example = "org.springframework.web.bind.MethodArgumentNotValidException")
    private String exception;

    @Schema(description = "Exception message", example = "Validation failed for argument [0] in ...")
    private String message;

    @Schema(description = "List of error details", example = """
                [
                    "amount: 'must be greater than 0'"
                ]
            """)
    private List<String> details;
}
