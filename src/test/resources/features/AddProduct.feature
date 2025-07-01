# language: en
Feature: Add product to wishlist
  Scenario: Add a product to the wishlist which does not exist
    Given I am a user with a wishlist that does not exist
    When I try to add a product id to my wishlist for the first time
    Then The wishlist should be created and the product should be added to my wishlist

  Scenario: Add a product to the wishlist with an empty wishlist
    Given I am a user with an empty wishlist
    When I add a product id to my wishlist
    Then The product should be added to my wishlist

  Scenario: Add a product to the wishlist with a non-empty wishlist
    Given I am a user with a non-empty wishlist
    When I add a product id to my wishlist
    Then The product should be added to my wishlist

  Scenario: Add an existing product to the wishlist
    Given I am a user with a non-empty wishlist
    When I try to add a product id that already exists in my wishlist
    Then The product should not be added to my wishlist

  Scenario: Add a product to the wishlist which exceeds the limit
    Given I am a user with a wishlist with 20 products
    When I try to add a product id to my wishlist with 20 products
    Then I should receive a error and the product should not be added to my wishlist