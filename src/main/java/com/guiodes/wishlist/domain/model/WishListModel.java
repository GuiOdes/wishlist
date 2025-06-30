package com.guiodes.wishlist.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record WishListModel(
        UUID id,
        UUID userId,
        List<UUID> productList
) {

    public WishListModel(UUID userId) {
        this(UUID.randomUUID(), userId, new ArrayList<>());
    }

    public void addProduct(UUID productId) {
        if (!this.productList.contains(productId)) {
            this.productList.add(productId);
        }
    }

    public void removeProduct(UUID productId) {
        this.productList.remove(productId);
    }
}
