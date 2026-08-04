package com.sparta.logistics.presentation.command.controller;

import com.sparta.logistics.application.command.dto.CreateOrderCommand;
import com.sparta.logistics.application.command.usecase.OrderCommandUseCase;
import com.sparta.logistics.presentation.common.dto.response.GeneralResponse;
import com.sparta.logistics.presentation.common.dto.response.GeneralResponseCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderCommandController {

    private final OrderCommandUseCase orderCommandUseCase;

    @PostMapping
    public ResponseEntity<GeneralResponse<UUID>> createOrder(
            @Valid @RequestBody CreateOrderCommand command
    ) {
        UUID orderId = orderCommandUseCase.createOrder(command);

        return GeneralResponse.toResponseEntity(GeneralResponseCode.CREATED, orderId);
    }
}
