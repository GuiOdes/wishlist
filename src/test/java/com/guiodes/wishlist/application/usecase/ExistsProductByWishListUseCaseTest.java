package com.guiodes.wishlist.application.usecase;

import com.guiodes.wishlist.application.port.WishListRepositoryGateway;
import com.guiodes.wishlist.configs.UnitTests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ExistsProductByWishListUseCaseTest implements UnitTests {

    @Mock
    private WishListRepositoryGateway wishListRepositoryGateway;

    @InjectMocks
    private ExistsProductByWishListUseCase existsProductByWishListUseCase;

    private final UUID userId = UUID.randomUUID();
    private final UUID productId = UUID.randomUUID();

    @Test
    @DisplayName("Should return false when product does not exist in wish list")
    void shouldReturnFalseWhenProductDoesNotExistInWishList() {
        when(wishListRepositoryGateway.existsProductInWishList(userId, productId)).thenReturn(false);

        Boolean result = existsProductByWishListUseCase.execute(userId, productId);

        assertThat(result).isFalse();
        verify(wishListRepositoryGateway).existsProductInWishList(userId, productId);
    }

    @Test
    @DisplayName("Should return true when product exists in wish list")
    void shouldReturnTrueWhenProductExistsInWishList() {
        when(wishListRepositoryGateway.existsProductInWishList(userId, productId)).thenReturn(true);

        Boolean result = existsProductByWishListUseCase.execute(userId, productId);

        assertThat(result).isTrue();
        verify(wishListRepositoryGateway).existsProductInWishList(userId, productId);
    }
}