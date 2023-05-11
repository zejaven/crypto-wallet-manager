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
public class InternalServerErrorMessageResponse {

    @Schema(description = "Date and time when error happened", example = "2023-05-10T16:20:53.3312514")
    private LocalDateTime timestamp;

    @Schema(description = "Response status", example = "500")
    private int status;

    @Schema(description = "Path to api method where error happened", example = "/api/transaction/send")
    private String path;

    @Schema(description = "Exception name", example = "java.lang.NullPointerException")
    private String exception;

    @Schema(description = "Exception message", example = "Cannot invoke java.math.BigDecimal.longValue() because the return value of ... is null")
    private String message;

    @Schema(description = "List of error details", example = """
                [
                    null
                ]
            """)
    private List<String> details;
}
