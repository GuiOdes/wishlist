# language: en
Feature: Check if exists product in WishList
  Scenario: Check if a product exists in a wishlist that does not exist
    Given I am a user with a wishlist that does not exist
    When I try to check if a product id exists in my wishlist
    Then Don't create the wishlist and return false

  Scenario: Check if a product exists in an empty wishlist
    Given I am a user with an empty wishlist
    When I try to check if a product id exists in my wishlist
    Then Don't create the wishlist and return false

  Scenario: Check if a product exists in a non-empty wishlist
    Given I am a user with a non-empty wishlist
    When I try to check if a product id exists in my wishlist
    Then The product should be checked and return true