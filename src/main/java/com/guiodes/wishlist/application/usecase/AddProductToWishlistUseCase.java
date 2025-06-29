package com.guiodes.wishlist.application.usecase;

import com.guiodes.wishlist.application.command.AddProductToWishlistCommand;
import com.guiodes.wishlist.application.port.WishListRepositoryGateway;
import com.guiodes.wishlist.domain.model.WishListModel;

public class AddProductToWishlistUseCase {

    private final WishListRepositoryGateway wishListRepositoryGateway;

    public AddProductToWishlistUseCase(WishListRepositoryGateway wishListRepositoryGateway) {
        this.wishListRepositoryGateway = wishListRepositoryGateway;
    }

    public WishListModel execute(AddProductToWishlistCommand command) {
        return wishListRepositoryGateway.findByUserId(command.userId())
                .map(wishList -> addProductToExistingWishList(wishList, command))
                .orElseGet(() -> createNewWishList(command));
    }

    private WishListModel addProductToExistingWishList(WishListModel wishList, AddProductToWishlistCommand command) {
        wishList.productList().add(command.productId());
        return wishListRepositoryGateway.saveWishList(wishList);
    }

    private WishListModel createNewWishList(AddProductToWishlistCommand command) {
        WishListModel newWishList = new WishListModel(command.userId());
        newWishList.productList().add(command.productId());

        return wishListRepositoryGateway.saveWishList(newWishList);
    }
}
