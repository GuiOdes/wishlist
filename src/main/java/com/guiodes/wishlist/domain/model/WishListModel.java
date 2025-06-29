package com.guiodes.wishlist.domain.model;

import java.util.List;
import java.util.UUID;

public record WishListModel(
    UUID id,
    UUID userId,
    List<UUID> productList
) {

    public WishListModel(UUID userId) {
        this(UUID.randomUUID(), userId, List.of());
    }
}
