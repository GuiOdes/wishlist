package com.guiodes.wishlist.application.usecase;

import com.guiodes.wishlist.application.port.WishListRepositoryGateway;
import com.guiodes.wishlist.domain.model.WishListModel;

import java.util.Optional;
import java.util.UUID;

public class DeleteWishListProductUseCase {

    private final WishListRepositoryGateway wishListRepositoryGateway;

    public DeleteWishListProductUseCase(WishListRepositoryGateway wishListRepositoryGateway) {
        this.wishListRepositoryGateway = wishListRepositoryGateway;
    }

    public void execute(UUID userId, UUID productId) {
        Optional<WishListModel> wishList = wishListRepositoryGateway.findByUserId(userId);

        wishList.ifPresent(wishListModel -> {
            wishListModel.removeProduct(productId);

            wishListRepositoryGateway.saveWishList(wishListModel);
        });
    }
}
