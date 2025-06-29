package com.guiodes.wishlist.infra.mongo.document;

import com.guiodes.wishlist.domain.model.WishListModel;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Document("WishList")
public record WishListDocument(@Id UUID id, UUID userId, List<UUID> productList) {

    public WishListDocument(WishListModel wishListModel) {
        this(wishListModel.id(), wishListModel.userId(), wishListModel.productList());
    }

    public WishListModel toModel() {
        return new WishListModel(id, userId, productList);
    }
}
