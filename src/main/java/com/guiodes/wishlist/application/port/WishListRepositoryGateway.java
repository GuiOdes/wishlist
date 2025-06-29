package com.guiodes.wishlist.application.port;

import com.guiodes.wishlist.domain.model.WishListModel;

import java.util.Optional;
import java.util.UUID;

public interface WishListRepositoryGateway {
    WishListModel saveWishList(WishListModel wishList);
    Optional<WishListModel> findByUserId(UUID userId);
}
