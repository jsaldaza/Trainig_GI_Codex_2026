@menu @regression
Feature: Menu
  As a standard user
  I want to use the hamburger menu
  So that I can navigate and manage my session

  Background:
    Given the user is logged in

  Scenario: All Items navigates to products
    When the user opens the cart
    And the user opens the menu
    And the user selects the menu option "All Items"
    Then the user should see the products page

  Scenario: About navigates to Sauce Labs
    When the user opens the menu
    And the user selects the menu option "About"
    Then the current url should contain "saucelabs.com"

  Scenario: Logout returns to login
    When the user opens the menu
    And the user selects the menu option "Logout"
    Then the user should see the login page

  Scenario: Reset App State clears the cart
    When the user adds the product "Sauce Labs Backpack" to the cart
    And the user opens the menu
    And the user selects the menu option "Reset App State"
    And the user opens the cart
    Then the cart should be empty
