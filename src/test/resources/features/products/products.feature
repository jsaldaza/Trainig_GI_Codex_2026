@products
Feature: Products
  As a standard user
  I want to add products to the cart
  So that I can buy them

  Background:
    Given the user is logged in

  @smoke
  Scenario: Add a product to the cart (UI only)
    When the user adds the product "Sauce Labs Backpack" to the cart
    Then the cart badge should show 1

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
