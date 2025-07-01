package com.guiodes.wishlist.application.usecase;

import com.guiodes.wishlist.application.port.WishListRepositoryGateway;
import com.guiodes.wishlist.configs.UnitTests;
import com.guiodes.wishlist.domain.model.WishListModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FindWishListUseCaseTest implements UnitTests {

    @Mock
    private WishListRepositoryGateway wishListRepositoryGateway;

    @InjectMocks
    private FindWishListUseCase findWishListUseCase;

    private final UUID userId = UUID.randomUUID();

    @Test
    @DisplayName("Should find wish list by user ID when it exists")
    void shouldFindWishListByUserIdWhenItExists() {
        when(wishListRepositoryGateway.findByUserId(userId)).thenReturn(Optional.empty());

        findWishListUseCase.execute(userId);

        verify(wishListRepositoryGateway).findByUserId(userId);
    }


    @Test
    @DisplayName("Should create a wishlist and return empty when wish list does not exist")
    void shouldCreateWishListAndReturnEmptyWhenWishListDoesNotExist() {
        WishListModel response = new WishListModel(userId);

        when(wishListRepositoryGateway.findByUserId(userId)).thenReturn(Optional.empty());
        when(wishListRepositoryGateway.saveWishList(any(WishListModel.class))).thenReturn(response);

        findWishListUseCase.execute(userId);

        verify(wishListRepositoryGateway).findByUserId(userId);
        verify(wishListRepositoryGateway).saveWishList(any(WishListModel.class));
    }
}
