package com.guiodes.wishlist.infra.api.response;

import java.sql.Timestamp;

public record ErrorResponse(
        String message,
        int status,
        String path,
        Timestamp timestamp
) {

    public ErrorResponse(String message, int status, String path) {
        this(message, status, path, new Timestamp(System.currentTimeMillis()));
    }
}
