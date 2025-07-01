package com.guiodes.wishlist.builders;

import com.guiodes.wishlist.domain.model.WishListModel;
import com.guiodes.wishlist.infra.mongo.document.WishListDocument;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WishListBuilder {
    private UUID id;
    private UUID userId;
    private final List<UUID> productList;

    private WishListBuilder() {
        this.productList = new ArrayList<>();
    }

    public static WishListBuilder builder() {
        return new WishListBuilder();
    }

    public WishListBuilder withUserId(UUID userId) {
        this.userId = userId;
        return this;
    }

    public WishListBuilder withProducts(List<UUID> products) {
        if (products != null) {
            this.productList.addAll(products);
        }
        return this;
    }

    public WishListBuilder addProduct(UUID product) {
        if (product != null) {
            this.productList.add(product);
        }
        return this;
    }

    private void applyDefaultValues() {
        if (this.id == null) {
            this.id = UUID.randomUUID();
        }

        if (this.userId == null) {
            this.userId = UUID.randomUUID();
        }

        if (this.productList.isEmpty()) {
            this.productList.add(UUID.randomUUID());
            this.productList.add(UUID.randomUUID());
        }
    }

    public WishListDocument buildDocument() {
        applyDefaultValues();
        return new WishListDocument(id, userId, productList);
    }
}
