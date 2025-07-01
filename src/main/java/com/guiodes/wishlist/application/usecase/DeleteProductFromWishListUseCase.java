package com.guiodes.wishlist.application.usecase;

import com.guiodes.wishlist.application.port.WishListRepositoryGateway;
import com.guiodes.wishlist.domain.model.WishListModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.UUID;

public class DeleteProductFromWishListUseCase {

    private final Logger logger = LogManager.getLogger(DeleteProductFromWishListUseCase.class);

    private final WishListRepositoryGateway wishListRepositoryGateway;

    public DeleteProductFromWishListUseCase(WishListRepositoryGateway wishListRepositoryGateway) {
        this.wishListRepositoryGateway = wishListRepositoryGateway;
    }

    public void execute(UUID userId, UUID productId) {
        Optional<WishListModel> wishList = wishListRepositoryGateway.findByUserId(userId);

        wishList.ifPresent(wishListModel -> {
            logger.info("Deleting product with ID: {} from wish list of user ID: {}", productId, userId);

            wishListModel.removeProduct(productId);

            wishListRepositoryGateway.saveWishList(wishListModel);
        });
    }
}
