package com.sparta.logistics.presentation.common.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.logistics.common.code.ApiResponseCode;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;

@Schema(description = "공통 성공 응답")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record GeneralResponse<T>(
        @Schema(description = "응답 메시지", example = "요청이 성공했습니다.")
        String message,

        @Schema(description = "응답 데이터", nullable = true)
        T data
) {
    public static <T> ResponseEntity<GeneralResponse<T>> toResponseEntity(ApiResponseCode responseCode, T data) {
        return ResponseEntity.status(responseCode.getStatus())
                .body(fromData(responseCode, data));
    }

    private static <T> GeneralResponse<T> fromData(ApiResponseCode responseCode, T data) {
        return new GeneralResponse<>(responseCode.getMessage(), data);
    }
}
