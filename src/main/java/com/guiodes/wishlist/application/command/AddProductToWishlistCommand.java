package com.guiodes.wishlist.application.command;

import java.util.UUID;

public record AddProductToWishlistCommand(
        UUID userId,
        UUID productId
) {}
