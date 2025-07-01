package com.guiodes.wishlist.infra.configs;

import com.guiodes.wishlist.application.usecase.AddProductToWishlistUseCase;
import com.guiodes.wishlist.application.usecase.DeleteProductFromWishListUseCase;
import com.guiodes.wishlist.application.usecase.ExistsProductByWishListUseCase;
import com.guiodes.wishlist.application.usecase.FindWishListUseCase;
import com.guiodes.wishlist.infra.mongo.repository.WishListRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfiguration {

    @Bean
    public AddProductToWishlistUseCase createWishListUseCase(
        WishListRepository wishListRepository,
        WishListItemsProperties wishListItemsProperties
    ) {
        return new AddProductToWishlistUseCase(wishListRepository, wishListItemsProperties);
    }

    @Bean
    public FindWishListUseCase findWishListUseCase(WishListRepository wishListRepository) {
        return new FindWishListUseCase(wishListRepository);
    }

    @Bean
    public ExistsProductByWishListUseCase existsWishListUseCase(WishListRepository wishListRepository) {
        return new ExistsProductByWishListUseCase(wishListRepository);
    }

    @Bean
    public DeleteProductFromWishListUseCase deleteWishListProductUseCase(WishListRepository wishListRepository) {
        return new DeleteProductFromWishListUseCase(wishListRepository);
    }
}
