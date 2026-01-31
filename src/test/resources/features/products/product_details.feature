@products @regression
Feature: Product details
  As a standard user
  I want to see product details
  So that I can add items from the detail page

  Background:
    Given the user is logged in

  Scenario: Add product to cart from details page
    When the user opens product details for "Sauce Labs Backpack"
    And the user adds the product to the cart from details
    And the user returns to products
    And the user opens the cart
    Then the cart should contain the product "Sauce Labs Backpack"
