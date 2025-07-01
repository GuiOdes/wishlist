package com.guiodes.wishlist.infra.mongo.repository;

import com.guiodes.wishlist.builders.WishListBuilder;
import com.guiodes.wishlist.configs.IntegrationTests;
import com.guiodes.wishlist.domain.model.WishListModel;
import com.guiodes.wishlist.infra.mongo.document.WishListDocument;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

class WishListRepositoryIT extends IntegrationTests {

    @Autowired
    private WishListRepository wishListRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void shouldSaveAWishList() {
        UUID userId = randomUUID();
        WishListModel wishList = new WishListModel(userId);
        Query findQuery = query(where("userId").is(userId));

        WishListDocument findBefore = mongoTemplate.findOne(findQuery, WishListDocument.class);

        assertThat(findBefore).isNull();

        var savedWishList = wishListRepository.saveWishList(wishList);

        WishListDocument foundWishList = mongoTemplate.findOne(findQuery, WishListDocument.class);

        assertThat(foundWishList).isNotNull();
        assertThat(foundWishList.toModel()).isEqualTo(savedWishList);
    }

    @Test
    void shouldFindWishListByUserId() {
        UUID userId = randomUUID();
        WishListDocument wishList = WishListBuilder.builder().withUserId(userId).buildDocument();

        assertThat(wishListRepository.findByUserId(userId)).isEmpty();

        mongoTemplate.save(wishList);

        var foundWishList = wishListRepository.findByUserId(userId);

        assertThat(foundWishList).isPresent();
        assertThat(foundWishList.get()).isEqualTo(wishList.toModel());
    }

    @Test
    void shouldVerifyProductExistsInWishList() {
        UUID userId = randomUUID();
        UUID productId = randomUUID();
        WishListDocument wishList = WishListBuilder.builder()
                .withUserId(userId)
                .addProduct(productId)
                .buildDocument();

        boolean existsBefore = wishListRepository.existsProductInWishList(userId, productId);

        assertThat(existsBefore).isFalse();

        mongoTemplate.save(wishList);

        boolean exists = wishListRepository.existsProductInWishList(userId, productId);

        assertThat(exists).isTrue();
    }
}
