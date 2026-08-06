package com.sparta.logistics.infrastructure.feign.client;

import com.sparta.logistics.infrastructure.feign.dto.DeliveryResponse;
import com.sparta.logistics.infrastructure.feign.dto.CreateDeliveryRequest;
import com.sparta.logistics.presentation.common.dto.response.GeneralResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "delivery-service", url = "${services.delivery.url}")
public interface DeliveryClient {
    @PostMapping("/api/v1/deliveries/internal")
    GeneralResponse<DeliveryResponse> createDelivery(
            @RequestBody CreateDeliveryRequest request
    );
}
