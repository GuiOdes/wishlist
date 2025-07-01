package com.guiodes.wishlist.configs.containers;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class TestContainersInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Container
    static final MongoDBContainer mongoDbContainer = new MongoDBContainer("mongo:latest")
            .withExposedPorts(27017);

    @Override
    public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
        mongoDbContainer.start();
        System.setProperty("spring.data.mongodb.uri", mongoDbContainer.getReplicaSetUrl());
        System.setProperty("spring.data.mongodb.database", "wishlist");
    }
}
