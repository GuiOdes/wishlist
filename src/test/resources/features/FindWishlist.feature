# language: en
Feature: Find wishlist
  Scenario: Find a wishlist that does not exists
    Given I am a user with a wishlist that does not exist
    When I try to find my wishlist
    Then The wishlist should be created and returned to me

  Scenario: Find an empty wishlist
    Given I am a user with an empty wishlist
    When I try to find my wishlist
    Then The wishlist should be returned to me without products

  Scenario: Find a non-empty wishlist
    Given I am a user with a non-empty wishlist
    When I try to find my wishlist
    Then The wishlist should be returned to me with my products
