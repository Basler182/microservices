package de.runtimeterror.clients.orders;

public record OrderResponse (Long productId, String productName, Integer quantity) {
}
