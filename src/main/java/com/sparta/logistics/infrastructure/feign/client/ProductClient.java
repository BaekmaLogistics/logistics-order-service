package com.sparta.logistics.infrastructure.feign.client;

import com.sparta.logistics.infrastructure.feign.dto.ProductResponse;
import com.sparta.logistics.presentation.common.dto.response.GeneralResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "company-product-service", url = "${services.company-product.url}")
public interface ProductClient {

    @GetMapping("/api/v1/products/{productId}")
    GeneralResponse<ProductResponse> getProduct(
            @PathVariable("productId") UUID productId
    );
}
