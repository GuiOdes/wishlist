package com.guiodes.wishlist.application.usecase;

import com.guiodes.wishlist.application.command.AddProductToWishlistCommand;
import com.guiodes.wishlist.application.port.WishListRepositoryGateway;
import com.guiodes.wishlist.configs.UnitTests;
import com.guiodes.wishlist.domain.exception.MaxSizeReachedException;
import com.guiodes.wishlist.domain.model.WishListModel;
import com.guiodes.wishlist.infra.configs.WishListItemsProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AddProductToWishlistUseCaseTest implements UnitTests {

    @Mock
    private WishListRepositoryGateway wishListRepositoryGateway;

    @Mock
    private WishListItemsProperties wishListItemsProperties;

    @InjectMocks
    private AddProductToWishlistUseCase addProductToWishlistUseCase;

    private final UUID userId = UUID.randomUUID();
    private final UUID productId = UUID.randomUUID();
    private final AddProductToWishlistCommand command = new AddProductToWishlistCommand(userId, productId);

    @Test
    @DisplayName("Should add product to wishlist when wishlist does not exist")
    void shouldAddProductToWishlistWhenWishListDoesNotExist() {

        when(wishListRepositoryGateway.findByUserId(userId)).thenReturn(Optional.empty());
        when(wishListRepositoryGateway.saveWishList(any())).thenReturn(new WishListModel(userId));

        addProductToWishlistUseCase.execute(command);

        verify(wishListRepositoryGateway).findByUserId(userId);
        verify(wishListRepositoryGateway).saveWishList(any(WishListModel.class));
    }

    @Test
    @DisplayName("Should add product to existing wishlist")
    void shouldAddProductToExistingWishlist() {
        WishListModel existingWishList = new WishListModel(userId);

        when(wishListRepositoryGateway.findByUserId(userId)).thenReturn(Optional.of(existingWishList));
        when(wishListRepositoryGateway.saveWishList(any())).thenReturn(existingWishList);
        when(wishListItemsProperties.maxSize()).thenReturn(10);

        addProductToWishlistUseCase.execute(command);

        verify(wishListRepositoryGateway).findByUserId(userId);
        verify(wishListRepositoryGateway).saveWishList(existingWishList);
        verify(wishListItemsProperties).maxSize();
    }

    @Test
    @DisplayName("Should throw exception when product is already in wishlist")
    void shouldThrowExceptionWhenProductIsAlreadyInWishlist() {
        WishListModel existingWishList = new WishListModel(userId);
        existingWishList.addProduct(productId);

        when(wishListRepositoryGateway.findByUserId(userId)).thenReturn(Optional.of(existingWishList));

        assertThatCode(() -> addProductToWishlistUseCase.execute(command))
                .isInstanceOf(com.guiodes.wishlist.domain.exception.DuplicatedProductException.class)
                .hasMessageContaining("Product %s already exists in the user wishlist!".formatted(productId));

        verify(wishListRepositoryGateway).findByUserId(userId);
    }

    @Test
    @DisplayName("Should throw exception when wishlist exceeds max size")
    void shouldThrowExceptionWhenWishlistExceedsMaxSize() {
        WishListModel existingWishList = new WishListModel(userId);
        for (int i = 0; i < 10; i++) {
            existingWishList.addProduct(UUID.randomUUID());
        }

        when(wishListRepositoryGateway.findByUserId(userId)).thenReturn(Optional.of(existingWishList));
        when(wishListItemsProperties.maxSize()).thenReturn(10);

        assertThatCode(() -> addProductToWishlistUseCase.execute(command))
                .isInstanceOf(MaxSizeReachedException.class)
                .hasMessageContaining("The wishlist for user %s has reached the maximum number of items (%d)."
                        .formatted(userId, 10));

        verify(wishListRepositoryGateway).findByUserId(userId);
    }
}