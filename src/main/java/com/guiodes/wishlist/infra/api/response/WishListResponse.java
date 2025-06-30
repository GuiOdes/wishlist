package com.guiodes.wishlist.infra.api.response;

import com.guiodes.wishlist.domain.model.WishListModel;

import java.util.List;
import java.util.UUID;

public record WishListResponse(
        UUID id,
        UUID userId,
        List<UUID> productList
) {

    public WishListResponse(WishListModel wishListModel) {
        this(wishListModel.id(), wishListModel.userId(), wishListModel.productList());
    }
}
