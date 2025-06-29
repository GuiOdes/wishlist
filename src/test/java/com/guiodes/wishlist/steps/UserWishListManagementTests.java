package com.guiodes.wishlist.steps;

import com.guiodes.wishlist.configs.TestcontainersConfiguration;
import com.guiodes.wishlist.domain.model.WishListModel;
import com.guiodes.wishlist.infra.api.request.AddProductRequest;
import com.guiodes.wishlist.infra.mongo.document.WishListDocument;
import com.guiodes.wishlist.infra.mongo.repository.WishListRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@CucumberContextConfiguration
public class UserWishListManagementTests extends TestcontainersConfiguration {

    private final UUID userId = UUID.randomUUID();
    private final UUID productId = UUID.randomUUID();

    @Autowired
    private WishListRepository wishListRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Given("I am a user with a empty wishlist")
    public void iAmAUserWithAEmptyWishlist() {
        wishListRepository.saveWishList(new WishListModel(userId));
    }

    @When("I add an item id to my wishlist")
    public void iAddAnItemIdToMyWishlist() {
        testRestTemplate.postForEntity("/api/v1/wishlist",
            new AddProductRequest(userId, productId),
            WishListDocument.class
        );
    }

    @Then("The item should be added to my wishlist")
    public void theItemShouldBeAddedToMyWishlist() {
        Optional<WishListModel> wishList = wishListRepository.findByUserId(userId);

        assertThat(wishList).isPresent();
        assertThat(wishList.get().productList()).contains(productId);
    }
}
