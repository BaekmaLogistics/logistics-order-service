package com.sparta.logistics.presentation.common.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;

@Schema(description = "Feign 에러 응답")
public record FeignErrorResponse(
        @Schema(description = "에러 코드", example = "EXTERNAL_SERVICE_ERROR")
        String errorCode,

        @Schema(description = "에러 메시지", example = "외부 서비스 호출에 실패했습니다.")
        String message,

        @Schema(description = "필드별 검증 실패 메시지", nullable = true)
        Map<String, String> errors
) {
}
