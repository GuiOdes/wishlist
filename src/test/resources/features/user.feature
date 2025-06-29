# language: en
Feature: User Wishlist Management
  Scenario: Add an item to the wishlist
    Given I am a user with a empty wishlist
    When I add an item id to my wishlist
    Then The item should be added to my wishlist