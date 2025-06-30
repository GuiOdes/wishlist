package com.guiodes.wishlist.infra.api.response;

public record ErrorResponse(
        String message,
        int status
) { }
