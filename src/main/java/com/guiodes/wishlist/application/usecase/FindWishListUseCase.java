package com.guiodes.wishlist.application.usecase;

import com.guiodes.wishlist.application.port.WishListRepositoryGateway;
import com.guiodes.wishlist.domain.model.WishListModel;

import java.util.UUID;

public class FindWishListUseCase {

    private final WishListRepositoryGateway wishListRepositoryGateway;

    public FindWishListUseCase(WishListRepositoryGateway wishListRepositoryGateway) {
        this.wishListRepositoryGateway = wishListRepositoryGateway;
    }

    public WishListModel execute(UUID userId) {
        return wishListRepositoryGateway.findByUserId(userId)
                .orElseGet(() -> {
                    System.out.printf("Wishlist não encontrada para userId=%s. Criando nova.%n", userId);
                    return wishListRepositoryGateway.saveWishList(new WishListModel(userId));
                });
    }
}
