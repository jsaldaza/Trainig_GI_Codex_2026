@smoke @login
Feature: Login
  As a standard user
  I want to log into SauceDemo
  So that I can access products

  Scenario: Successful login
    Given the user is on the login page
    When the user logs in with valid credentials
    Then the user should see the products page
