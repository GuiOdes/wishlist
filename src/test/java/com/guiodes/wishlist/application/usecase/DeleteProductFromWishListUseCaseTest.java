package com.guiodes.wishlist.application.usecase;

import com.guiodes.wishlist.application.command.AddProductToWishlistCommand;
import com.guiodes.wishlist.application.port.WishListRepositoryGateway;
import com.guiodes.wishlist.configs.UnitTests;
import com.guiodes.wishlist.domain.model.WishListModel;
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

class DeleteProductFromWishListUseCaseTest implements UnitTests {

    @Mock
    private WishListRepositoryGateway wishListRepositoryGateway;

    @InjectMocks
    private DeleteProductFromWishListUseCase deleteProductFromWishListUseCase;

    private final UUID userId = UUID.randomUUID();
    private final UUID productId = UUID.randomUUID();

    @Test
    @DisplayName("Should delete product from wishlist when wishlist exists")
    void shouldDeleteProductFromWishlist() {
        when(wishListRepositoryGateway.findByUserId(userId)).thenReturn(Optional.of(new WishListModel(userId)));

        deleteProductFromWishListUseCase.execute(userId, productId);

        verify(wishListRepositoryGateway).findByUserId(userId);
        verify(wishListRepositoryGateway).saveWishList(any(WishListModel.class));
    }

    @Test
    @DisplayName("Should not throw exception when wishlist does not exist")
    void shouldNotThrowExceptionWhenWishListDoesNotExist() {
        when(wishListRepositoryGateway.findByUserId(userId)).thenReturn(Optional.empty());

        assertThatCode(() -> deleteProductFromWishListUseCase.execute(userId, productId))
                .doesNotThrowAnyException();

        verify(wishListRepositoryGateway).findByUserId(userId);
    }
}
