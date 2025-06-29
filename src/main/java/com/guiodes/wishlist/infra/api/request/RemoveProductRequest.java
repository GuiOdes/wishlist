package com.guiodes.wishlist.infra.api.request;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record RemoveProductRequest(
        @NotBlank(message = "User ID cannot be blank")
        UUID userId,
        @NotBlank(message = "Product ID cannot be blank")
        UUID productId
) {}
