package com.sparta.logistics.presentation.common.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorResponseCode implements ApiResponseCode {
    // Common
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"COMMON_0001", "알 수 없는 오류가 발생했습니다."),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "COMMON_0002","유효하지 않은 요청입니다."),
    FEIGN_CLIENT_ERROR(HttpStatus.BAD_GATEWAY, "COMMON_0003", "Feign 통신 중 오류가 발생했습니다."),

    // Order
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDER_0001", "주문을 찾을 수 없습니다."),
    ORDER_INVALID_QUANTITY(HttpStatus.BAD_REQUEST, "ORDER_0002", "주문 수량이 올바르지 않습니다."),
    ORDER_ITEM_REQUIRED(HttpStatus.BAD_REQUEST, "ORDER_0003", "주문 상품은 필수입니다."),
    ORDER_CANNOT_BE_UPDATED(HttpStatus.BAD_REQUEST, "ORDER_0004", "수정할 수 없는 주문입니다."),
    ORDER_CANNOT_BE_CANCELLED(HttpStatus.BAD_REQUEST, "ORDER_0005", "취소할 수 없는 주문 상태입니다."),
    ORDER_CANNOT_BE_DELETED(HttpStatus.BAD_REQUEST, "ORDER_0006", "삭제할 수 없는 주문 상태입니다."),
    ORDER_ACCESS_DENIED(HttpStatus.FORBIDDEN, "ORDER_0007", "주문에 접근할 권한이 없습니다."),
    ORDER_CANNOT_CHANGE_STATUS(HttpStatus.BAD_REQUEST, "ORDER_0008", "변경할 수 없는 주문 상태입니다."),
    ORDER_DELIVERY_CREATE_FAILED(HttpStatus.BAD_GATEWAY, "ORDER_0009", "배송 생성 요청에 실패했습니다.");


    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}
