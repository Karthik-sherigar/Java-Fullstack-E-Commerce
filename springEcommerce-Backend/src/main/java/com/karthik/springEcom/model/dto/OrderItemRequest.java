package com.karthik.springEcom.model.dto;

public record OrderItemRequest(
        int productId,
        int quantity
) {
}
