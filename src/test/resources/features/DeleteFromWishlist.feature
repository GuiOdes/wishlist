# language: en
Feature: Delete from wishlist
  Scenario: Try to delete a product from a wishlist that does not exist
    Given I am a user with a wishlist that does not exist
    When I try to delete a product id from my wishlist for the first time
    Then Don't create the wishlist and don't return error

  Scenario: Delete a product from a non empty wishlist
    Given I am a user with a non-empty wishlist
    When I try to delete a product id from my wishlist
    Then The product should be deleted from my wishlist

  Scenario: Delete a product from an empty wishlist
    Given I am a user with an empty wishlist
    When I try to delete a product id from my wishlist
    Then Don't return error