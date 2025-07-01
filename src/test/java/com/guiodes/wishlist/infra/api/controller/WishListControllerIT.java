package com.guiodes.wishlist.infra.api.controller;

import com.guiodes.wishlist.builders.WishListBuilder;
import com.guiodes.wishlist.configs.IntegrationTests;
import com.guiodes.wishlist.infra.api.request.AddProductRequest;
import com.guiodes.wishlist.infra.api.response.ContainsProductInWishListResponse;
import com.guiodes.wishlist.infra.api.response.WishListResponse;
import com.guiodes.wishlist.infra.mongo.document.WishListDocument;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class WishListControllerIT extends IntegrationTests {

    private static final String BASE_URL = "/api/v1/wishlist";

    private final UUID userId = UUID.randomUUID();
    private final UUID productId = UUID.randomUUID();
    private final WishListDocument wishListDocument = WishListBuilder.builder()
            .withUserId(userId)
            .withProducts(List.of(productId))
            .buildDocument();

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void shouldAddProductToWishList() {
        assertThat(mongoTemplate.findAll(WishListDocument.class)).isEmpty();

        AddProductRequest request = new AddProductRequest(userId, productId);

        var response = restTemplate.postForEntity(BASE_URL, request, WishListResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().userId()).isEqualTo(userId);
        assertThat(response.getBody().productList()).contains(productId);

        WishListDocument wishListDocument = mongoTemplate.findAll(WishListDocument.class).getFirst();

        assertThat(wishListDocument).isNotNull();
        assertThat(wishListDocument.userId()).isEqualTo(userId);
        assertThat(wishListDocument.productList()).contains(productId);
    }

    @Test
    void shouldFindWishListByUserId() {
        mongoTemplate.save(wishListDocument);

        var response = restTemplate.getForEntity(BASE_URL + "/" + userId, WishListResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().userId()).isEqualTo(userId);
        assertThat(response.getBody().productList()).contains(productId);
    }

    @Test
    void shouldCheckIfProductExistsInWishList() {
        var response = restTemplate.getForEntity(BASE_URL + "/" + userId + "/product/" + productId + "/exists", ContainsProductInWishListResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().isInWishlist()).isFalse();

        mongoTemplate.save(wishListDocument);

        response = restTemplate.getForEntity(BASE_URL + "/" + userId + "/product/" + productId + "/exists", ContainsProductInWishListResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().isInWishlist()).isTrue();
    }

    @Test
    void shouldDeleteProductFromWishList() {
        mongoTemplate.save(wishListDocument);

        assertThat(mongoTemplate.findAll(WishListDocument.class)).contains(wishListDocument);
        assertThat(wishListDocument.productList()).contains(productId);

        restTemplate.delete(BASE_URL + "/" + userId + "/product/" + productId);

        WishListDocument updatedWishList = mongoTemplate.findAll(WishListDocument.class).getFirst();

        assertThat(updatedWishList.productList()).doesNotContain(productId);
    }
}
