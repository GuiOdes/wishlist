package com.guiodes.wishlist.domain.exception;

import java.util.UUID;

public class MaxSizeReachedException extends RuntimeException {

    public MaxSizeReachedException(UUID userId, int maxSize) {
        super("The wishlist for user %s has reached the maximum number of items (%d).".formatted(userId, maxSize));
    }
}
