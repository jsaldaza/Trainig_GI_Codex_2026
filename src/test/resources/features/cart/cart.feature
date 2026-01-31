@cart @regression
Feature: Cart
  As a standard user
  I want to manage my cart
  So that I can prepare my purchase

  Background:
    Given the user is logged in
    And the user resets the app state

  Scenario: Add multiple products updates the cart badge and list
    When the user adds the product "Sauce Labs Backpack" to the cart
    And the user adds the product "Sauce Labs Bike Light" to the cart
    Then the cart badge should show 2
    When the user opens the cart
    Then the cart should contain the product "Sauce Labs Backpack"
    And the cart should contain the product "Sauce Labs Bike Light"
    And the cart should have 2 items

  Scenario: Remove product from the cart
    When the user adds the product "Sauce Labs Backpack" to the cart
    And the user opens the cart
    And the user removes the product "Sauce Labs Backpack" from the cart
    Then the cart should be empty

  Scenario: Continue shopping returns to products
    When the user adds the product "Sauce Labs Backpack" to the cart
    And the user opens the cart
    And the user continues shopping
    Then the user should see the products page
