package com.guiodes.wishlist.application.usecase;

import com.guiodes.wishlist.application.port.WishListRepositoryGateway;

import java.util.UUID;

public class ExistsProductByWishListUseCase {

    private final WishListRepositoryGateway wishListRepositoryGateway;

    public ExistsProductByWishListUseCase(WishListRepositoryGateway wishListRepositoryGateway) {
        this.wishListRepositoryGateway = wishListRepositoryGateway;
    }

    public Boolean execute(UUID userId, UUID productId) {
        return wishListRepositoryGateway.existsProductInWishList(userId, productId);
    }
}
