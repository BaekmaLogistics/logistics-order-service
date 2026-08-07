package com.sparta.logistics.infrastructure.feign.client;

import com.sparta.logistics.infrastructure.feign.dto.CancelDeliveryRequest;
import com.sparta.logistics.infrastructure.feign.dto.DeliveryResponse;
import com.sparta.logistics.infrastructure.feign.dto.CreateDeliveryRequest;
import com.sparta.logistics.infrastructure.feign.dto.DeliveryStatusResponse;
import com.sparta.logistics.presentation.common.dto.response.GeneralResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "delivery-service", url = "${services.delivery.url}")
public interface DeliveryClient {
    @PostMapping("/internal/api/v1/deliveries")
    GeneralResponse<DeliveryResponse> createDelivery(
            @RequestBody CreateDeliveryRequest request
    );

    @PatchMapping("/internal/api/v1/deliveries/{deliveryId}/cancel")
    GeneralResponse<Void> cancelDelivery(
            @PathVariable("deliveryId") UUID deliveryId,
            @RequestBody CancelDeliveryRequest request
    );
}
