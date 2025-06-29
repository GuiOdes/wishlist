package com.guiodes.wishlist.infra.configs;

import com.guiodes.wishlist.application.usecase.AddProductToWishlistUseCase;
import com.guiodes.wishlist.infra.mongo.repository.WishListRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfiguration {

    @Bean
    public AddProductToWishlistUseCase createWishListUseCase(WishListRepository wishListRepository) {
        return new AddProductToWishlistUseCase(wishListRepository);
    }
}
