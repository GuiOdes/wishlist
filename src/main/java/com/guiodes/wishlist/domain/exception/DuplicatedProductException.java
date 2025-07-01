package com.guiodes.wishlist.domain.exception;

public class DuplicatedProductException extends RuntimeException {

    public DuplicatedProductException(String message) {
        super(message);
    }
}
