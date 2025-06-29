package com.guiodes.wishlist.configs;

import com.guiodes.wishlist.configs.containers.CustomMongoContainer;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestcontainersConfiguration extends CustomMongoContainer {}
