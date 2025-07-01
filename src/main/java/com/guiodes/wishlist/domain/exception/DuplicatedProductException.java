package com.guiodes.wishlist.domain.exception;

import java.util.UUID;

public class DuplicatedProductException extends RuntimeException {

    public DuplicatedProductException(UUID productId) {
        super("Product %s already exists in the user wishlist!".formatted(productId));
    }
}
