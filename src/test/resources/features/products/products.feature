@products
Feature: Products
  As a standard user
  I want to add products to the cart
  So that I can buy them

  Background:
    Given the user is logged in
    And the user resets the app state

  @smoke
  Scenario: Add a product to the cart
    When the user adds the product "Sauce Labs Backpack" to the cart
    And the user opens the cart
    Then the cart should contain the product "Sauce Labs Backpack"

  @regression
  Scenario Outline: Each product can be added to the cart
    When the user adds the product "<product>" to the cart
    And the user opens the cart
    Then the cart should contain the product "<product>"

    Examples:
      | product                 |
      | Sauce Labs Backpack     |
      | Sauce Labs Bike Light   |
      | Sauce Labs Bolt T-Shirt |
