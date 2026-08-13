package com.sparta.logistics.presentation.common.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.logistics.common.code.ErrorResponseCode;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@Schema(description = "공통 에러 응답")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        @Schema(description = "에러 코드", example = "ORDER_NOT_FOUND")
        String errorCode,

        @Schema(description = "에러 메시지", example = "주문을 찾을 수 없습니다.")
        String message,

        @Schema(description = "필드별 검증 실패 메시지", nullable = true)
        Map<String, String> errors
) {
    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorResponseCode responseCode) {
        return ResponseEntity.status(responseCode.getStatus())
                .body(ErrorResponse.fromData(
                        responseCode, null));
    }

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorResponseCode responseCode, Map<String, String> errors) {
        return ResponseEntity.status(responseCode.getStatus())
                .body(ErrorResponse.fromData(
                        responseCode, errors));
    }

    private static ErrorResponse fromData(ErrorResponseCode responseCode, Map<String, String> errors) {
        return new ErrorResponse(
                responseCode.getErrorCode(),
                responseCode.getMessage(),
                errors
        );
    }
}
