package com.sparta.logistics.application.command.service;

import com.sparta.logistics.infrastructure.feign.client.DeliveryClient;
import com.sparta.logistics.infrastructure.feign.client.HubClient;
import com.sparta.logistics.infrastructure.feign.client.ProductClient;
import com.sparta.logistics.infrastructure.feign.dto.delivery.CancelDeliveryRequest;
import com.sparta.logistics.infrastructure.feign.dto.delivery.CreateDeliveryRequest;
import com.sparta.logistics.infrastructure.feign.dto.delivery.DeliveryResponse;
import com.sparta.logistics.infrastructure.feign.dto.delivery.DeliveryStatusResponse;
import com.sparta.logistics.infrastructure.feign.dto.hub.HubStockRequest;
import com.sparta.logistics.infrastructure.feign.dto.product.ProductResponse;
import com.sparta.logistics.common.code.ErrorResponseCode;
import com.sparta.logistics.presentation.common.dto.response.GeneralResponse;
import com.sparta.logistics.common.exception.ApiException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderExternalService {

    private final ProductClient productClient;
    private final HubClient hubClient;
    private final DeliveryClient deliveryClient;

    // Product
    @CircuitBreaker(name = "productService", fallbackMethod = "fallbackProduct")
    public GeneralResponse<ProductResponse> getProduct(UUID productId) {
        return productClient.getProduct(productId);
    }

    private GeneralResponse<ProductResponse> fallbackProduct(UUID productId, Throwable throwable) {
        throw new ApiException(ErrorResponseCode.ORDER_PRODUCT_LOOKUP_FAILED);
    }

    // Hub
    @CircuitBreaker(name = "hubService", fallbackMethod = "fallbackDecreaseStock")
    public GeneralResponse<Void> decreaseStock(HubStockRequest request) {
        return hubClient.decreaseStock(request);
    }

    private GeneralResponse<Void> fallbackDecreaseStock(HubStockRequest request, Throwable throwable) {
        log.error("decreaseStock failed : {}", request, throwable);
        throw new ApiException(ErrorResponseCode.ORDER_STOCK_DECREASE_FAILED);
    }

    @CircuitBreaker(name = "hubService", fallbackMethod = "fallbackIncreaseStock")
    public GeneralResponse<Void> increaseStock(HubStockRequest request) {
        return hubClient.increaseStock(request);
    }

    private GeneralResponse<Void> fallbackIncreaseStock(HubStockRequest request, Throwable throwable) {
        throw new ApiException(ErrorResponseCode.ORDER_STOCK_RESTORE_FAILED);
    }

    // Delivery
    @CircuitBreaker(name = "deliveryService", fallbackMethod = "fallbackCreateDelivery")
    public GeneralResponse<DeliveryResponse> createDelivery(CreateDeliveryRequest request) {
        return deliveryClient.createDelivery(request);
    }

    private GeneralResponse<DeliveryResponse> fallbackCreateDelivery(
            CreateDeliveryRequest request,
            Throwable throwable
    ) {
        throw new ApiException(ErrorResponseCode.ORDER_DELIVERY_CREATE_FAILED);
    }

    @CircuitBreaker(name = "deliveryService", fallbackMethod = "fallbackCancelDelivery")
    public GeneralResponse<Void> cancelDelivery(UUID deliveryId, CancelDeliveryRequest request) {
        return deliveryClient.cancelDelivery(deliveryId, request);
    }

    private GeneralResponse<Void> fallbackCancelDelivery(
            UUID deliveryId,
            CancelDeliveryRequest request,
            Throwable throwable
    ) {
        throw new ApiException(ErrorResponseCode.ORDER_DELIVERY_CANCEL_FAILED);
    }

    @CircuitBreaker(name = "deliveryService", fallbackMethod = "fallbackGetDeliveryStatus")
    public GeneralResponse<DeliveryStatusResponse> getDeliveryStatus(UUID deliveryId) {
        return deliveryClient.getDeliveryStatus(deliveryId);
    }

    private GeneralResponse<DeliveryStatusResponse> fallbackGetDeliveryStatus(
            UUID deliveryId,
            Throwable throwable
    ) {
        throw new ApiException(ErrorResponseCode.ORDER_DELIVERY_STATUS_LOOKUP_FAILED);
    }

}
