package com.guiodes.wishlist.infra.mongo.repository;

import com.guiodes.wishlist.application.port.WishListRepositoryGateway;
import com.guiodes.wishlist.domain.model.WishListModel;
import com.guiodes.wishlist.infra.mongo.document.WishListDocument;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
public class WishListRepository implements WishListRepositoryGateway {

    private final MongoTemplate mongoTemplate;

    public WishListRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public WishListModel saveWishList(WishListModel wishList) {
        WishListDocument wishListDocument = new WishListDocument(wishList);

        return mongoTemplate.save(wishListDocument).toModel();
    }

    @Override
    public Optional<WishListModel> findByUserId(UUID userId) {
        return Optional.ofNullable(
            mongoTemplate.findOne(
                query(where("userId").is(userId)),
                WishListDocument.class
            )
        ).map(WishListDocument::toModel);
    }
}
