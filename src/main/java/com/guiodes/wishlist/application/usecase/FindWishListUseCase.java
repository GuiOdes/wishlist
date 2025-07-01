package com.guiodes.wishlist.application.usecase;

import com.guiodes.wishlist.application.port.WishListRepositoryGateway;
import com.guiodes.wishlist.domain.model.WishListModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

public class FindWishListUseCase {

    private final Logger logger = LogManager.getLogger(FindWishListUseCase.class);

    private final WishListRepositoryGateway wishListRepositoryGateway;

    public FindWishListUseCase(WishListRepositoryGateway wishListRepositoryGateway) {
        this.wishListRepositoryGateway = wishListRepositoryGateway;
    }

    public WishListModel execute(UUID userId) {
        return wishListRepositoryGateway.findByUserId(userId)
                .orElseGet(() -> {
                    logger.info("Wishlist não encontrada para userId={}. Criando nova.", userId);
                    return wishListRepositoryGateway.saveWishList(new WishListModel(userId));
                });
    }
}
