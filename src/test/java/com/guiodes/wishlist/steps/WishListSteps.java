package com.guiodes.wishlist.steps;

import com.guiodes.wishlist.configs.IntegrationTests;
import com.guiodes.wishlist.domain.model.WishListModel;
import com.guiodes.wishlist.infra.api.request.AddProductRequest;
import com.guiodes.wishlist.infra.api.response.ContainsProductInWishListResponse;
import com.guiodes.wishlist.infra.api.response.ErrorResponse;
import com.guiodes.wishlist.infra.api.response.WishListResponse;
import com.guiodes.wishlist.infra.mongo.document.WishListDocument;
import com.guiodes.wishlist.infra.mongo.repository.WishListRepository;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@CucumberContextConfiguration
public class WishListSteps extends IntegrationTests {

    private final UUID userId = UUID.fromString("70d7e8d9-04b9-4aa9-8331-15eba801f48d");
    private final UUID productId = UUID.fromString("70d7e8d9-04b9-4aa9-8331-15eba801f48e");
    private ResponseEntity<?> response;

    @Autowired
    private WishListRepository wishListRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Before
    public void setUp() {
        mongoTemplate.dropCollection("WishList");
    }

    @Given("I am a user with a wishlist with 20 products")
    public void userWithWishListWith20Products() {
        WishListModel wishList = new WishListModel(userId);
        for (int i = 0; i < 20; i++) {
            wishList.addProduct(UUID.randomUUID());
        }
        wishListRepository.saveWishList(wishList);
    }

    @Given("I am a user with an empty wishlist")
    public void iAmAUserWithAEmptyWishlist() {
        wishListRepository.saveWishList(new WishListModel(userId));
    }

    @Given("I am a user with a non-empty wishlist")
    public void iAmAUserWithANonEmptyWishlist() {
        WishListModel wishList = new WishListModel(userId);
        wishList.addProduct(productId);
        wishListRepository.saveWishList(wishList);
    }

    @Given("I am a user with a wishlist that does not exist")
    public void iAmAUserWithAWishlistThatDoesNotExist() {
        // No action needed, the repository will handle the non-existence
    }

    @When("I add a product id to my wishlist")
    public void iAddAnProductIdToMyWishlist() {
        testRestTemplate.postForEntity("/api/v1/wishlist",
                new AddProductRequest(userId, productId),
                WishListDocument.class
        );
    }

    @When("I try to add a product id to my wishlist for the first time")
    public void iTryToAddAProductIdToMyWishlistForTheFirstTime() {
        response = testRestTemplate.postForEntity("/api/v1/wishlist",
                new AddProductRequest(userId, productId),
                WishListDocument.class
        );
    }

    @When("I try to add a product id that already exists in my wishlist")
    public void iAddAnExistingProductIdToMyWishlist() {
        response = testRestTemplate.postForEntity("/api/v1/wishlist",
                new AddProductRequest(userId, productId),
                ErrorResponse.class);
    }

    @When("I try to add a product id to my wishlist with 20 products")
    public void iAddProductIdToMyWishlistWith20Products() {
        response = testRestTemplate.postForEntity("/api/v1/wishlist",
                new AddProductRequest(userId, UUID.randomUUID()),
                ErrorResponse.class);
    }

    @When("I try to find my wishlist")
    public void iTryToFindMyWishlist() {
        response = testRestTemplate.getForEntity("/api/v1/wishlist/" + userId, WishListResponse.class);
    }

    @Then("The wishlist should be created and returned to me")
    public void theWishlistShouldBeCreatedAndReturnedToMe() {
        WishListResponse wishListResponse = (WishListResponse) response.getBody();

        assertThat(wishListResponse).isNotNull();
        assertThat(wishListResponse.userId()).isEqualTo(userId);
        assertThat(wishListResponse.productList()).isEmpty();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(wishListRepository.findByUserId(userId)).isPresent();
    }

    @Then("The wishlist should be returned to me with my products")
    public void theWishlistShouldBeReturnedToMe() {
        WishListResponse wishListResponse = (WishListResponse) response.getBody();

        assertThat(wishListResponse).isNotNull();
        assertThat(wishListResponse.productList()).contains(productId);
        assertThat(wishListResponse.userId()).isEqualTo(userId);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(wishListRepository.findByUserId(userId)).isPresent();
    }

    @Then("The wishlist should be returned to me without products")
    public void theWishlistShouldBeReturnedToMeWithoutProducts() {
        WishListResponse wishListResponse = (WishListResponse) response.getBody();

        assertThat(wishListResponse).isNotNull();
        assertThat(wishListResponse.productList()).isEmpty();
        assertThat(wishListResponse.userId()).isEqualTo(userId);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(wishListRepository.findByUserId(userId)).isPresent();
    }

    @Then("The wishlist should be created and the product should be added to my wishlist")
    public void theWishlistShouldBeCreatedAndTheProductShouldBeAddedToMyWishlist() {
        Optional<WishListModel> wishList = wishListRepository.findByUserId(userId);

        assertThat(wishList).isPresent();
        assertThat(wishList.get().productList()).contains(productId);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Then("The product should be added to my wishlist")
    public void theProductShouldBeAddedToMyWishlist() {
        Optional<WishListModel> wishList = wishListRepository.findByUserId(userId);

        assertThat(wishList).isPresent();
        assertThat(wishList.get().productList()).contains(productId);
    }

    @When("I add another product id to my wishlist")
    public void iAddAnotherProductIdToMyWishlist() {
        UUID anotherProductId = UUID.randomUUID();
        testRestTemplate.postForEntity("/api/v1/wishlist",
                new AddProductRequest(userId, anotherProductId),
                WishListDocument.class
        );
    }

    @When("I try to delete a product id from my wishlist for the first time")
    public void iTryToDeleteAProductIdFromMyWishlistForTheFirstTime() {
        response = testRestTemplate.exchange(
            "/api/v1/wishlist/" + userId + "/product/" + productId,
            HttpMethod.DELETE,
            null,
            Void.class
        );
    }

    @When("I try to delete a product id from my wishlist")
    public void iTryToDeleteAProductIdFromMyWishlist() {
        response = testRestTemplate.exchange(
            "/api/v1/wishlist/" + userId + "/product/" + productId,
            HttpMethod.DELETE,
            null,
            Void.class
        );
    }

    @When("I try to check if a product id exists in my wishlist")
    public void iTryToCheckIfAProductIdExistsInMyWishlist() {
        response = testRestTemplate.getForEntity(
            "/api/v1/wishlist/" + userId + "/product/" + productId + "/exists",
                ContainsProductInWishListResponse.class
        );
    }

    @Then("The new product should be added to my wishlist")
    public void theNewProductShouldBeAddedToMyWishlist() {
        Optional<WishListModel> wishList = wishListRepository.findByUserId(userId);

        assertThat(wishList).isPresent();
        assertThat(wishList.get().productList()).contains(productId);
        assertThat(wishList.get().productList()).hasSize(2);
    }

    @Then("The product should not be added to my wishlist")
    public void theProductShouldNotBeAddedAgainToMyWishlist() {
        Optional<WishListModel> wishList = wishListRepository.findByUserId(userId);

        assertThat(wishList).isPresent();
        assertThat(wishList.get().productList()).contains(productId);
        assertThat(wishList.get().productList()).hasSize(1);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody()).isNotNull();
    }

    @Then("I should receive a error and the product should not be added to my wishlist")
    public void iShouldReceiveAConflictError() {
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
    }

    @Then("Don't create the wishlist and don't return error")
    public void dontCreateTheWishlistAndDontReturnError() {
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.getBody()).isNull();
    }

    @Then("The product should be deleted from my wishlist")
    public void theProductShouldBeDeletedFromMyWishlist() {
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.getBody()).isNull();

        Optional<WishListModel> wishList = wishListRepository.findByUserId(userId);
        assertThat(wishList).isPresent();
        assertThat(wishList.get().productList()).doesNotContain(productId);
    }

    @Then("Don't return error")
    public void dontReturnError() {
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    }

    @Then("Don't create the wishlist and return false")
    public void dontCreateTheWishlistAndReturnFalse() {
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isInstanceOf(ContainsProductInWishListResponse.class);

        ContainsProductInWishListResponse checkResponse = (ContainsProductInWishListResponse) response.getBody();

        assertThat(checkResponse).isNotNull();
        assertThat(checkResponse.isInWishlist()).isFalse();
    }

    @Then("The product should be checked and return true")
    public void theProductShouldBeCheckedAndReturnTrue() {
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isInstanceOf(ContainsProductInWishListResponse.class);

        ContainsProductInWishListResponse checkResponse = (ContainsProductInWishListResponse) response.getBody();

        assertThat(checkResponse).isNotNull();
        assertThat(checkResponse.isInWishlist()).isTrue();
    }
}
