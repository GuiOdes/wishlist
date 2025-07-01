package com.guiodes.wishlist.application.usecase;

import com.guiodes.wishlist.application.command.AddProductToWishlistCommand;
import com.guiodes.wishlist.application.port.WishListRepositoryGateway;
import com.guiodes.wishlist.domain.exception.DuplicatedProductException;
import com.guiodes.wishlist.domain.exception.MaxSizeReachedException;
import com.guiodes.wishlist.domain.model.WishListModel;
import com.guiodes.wishlist.infra.configs.WishListItemsProperties;

import java.util.Objects;

public class AddProductToWishlistUseCase {

    private final WishListRepositoryGateway wishListRepositoryGateway;
    private final WishListItemsProperties wishListItemsProperties;

    public AddProductToWishlistUseCase(
            WishListRepositoryGateway wishListRepositoryGateway,
            WishListItemsProperties wishListItemsProperties
    ) {
        this.wishListRepositoryGateway = wishListRepositoryGateway;
        this.wishListItemsProperties = wishListItemsProperties;
    }

    public WishListModel execute(AddProductToWishlistCommand command) {
        return wishListRepositoryGateway.findByUserId(command.userId())
                .map(wishList -> addProductToExistingWishList(wishList, command))
                .orElseGet(() -> createNewWishList(command));
    }

    private WishListModel createNewWishList(AddProductToWishlistCommand command) {
        WishListModel newWishList = new WishListModel(command.userId());

        newWishList.addProduct(command.productId());

        return wishListRepositoryGateway.saveWishList(newWishList);
    }

    private WishListModel addProductToExistingWishList(WishListModel wishList, AddProductToWishlistCommand command) {

        Objects.requireNonNull(command, "AddProductToWishlistCommand must not be null");

        validateCommand(wishList, command);

        wishList.addProduct(command.productId());

        return wishListRepositoryGateway.saveWishList(wishList);
    }

    private void validateCommand(WishListModel wishList, AddProductToWishlistCommand command) {
        if (wishList.productList().contains(command.productId())) {
            throw new DuplicatedProductException(command.productId());
        }

        if (wishList.productList().size() >= wishListItemsProperties.maxSize()) {
            throw new MaxSizeReachedException(command.userId(), wishListItemsProperties.maxSize());
        }
    }
}
