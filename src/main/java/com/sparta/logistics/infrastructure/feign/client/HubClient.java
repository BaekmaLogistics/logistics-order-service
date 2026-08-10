package com.sparta.logistics.infrastructure.feign.client;

import com.sparta.logistics.infrastructure.feign.dto.hub.HubStockRequest;
import com.sparta.logistics.presentation.common.dto.response.GeneralResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "hub-service", url = "${services.hub.url}")
public interface HubClient {

    @PatchMapping("/internal/api/v1/hub-inventories/decrease")
    GeneralResponse<Void> decreaseStock(@RequestBody HubStockRequest request);

    @PatchMapping("/internal/api/v1/hub-inventories/restore")
    GeneralResponse<Void> increaseStock(@RequestBody HubStockRequest request);
}
