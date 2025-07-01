package com.guiodes.wishlist.infra.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "wishlist.items")
public record WishListItemsProperties(
    int maxSize
) { }
