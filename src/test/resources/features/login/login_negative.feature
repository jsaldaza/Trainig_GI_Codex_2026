@login @regression @negative
Feature: Login - negative scenarios
  As a user
  I want to see clear error messages
  So that I understand why I cannot log in

  Scenario Outline: Invalid credentials show an error message
    Given the user is on the login page
    When the user logs in with username "<username>" and password "<password>"
    Then a login error message should be displayed
    And the login error message should contain "<message>"

    Examples:
      | username         | password     | message                                         |
      | wrong_user       | secret_sauce | Username and password do not match any user    |
      | standard_user    | wrong_pass   | Username and password do not match any user    |
      |                 | secret_sauce | Username is required                           |
      | standard_user    |             | Password is required                           |
      | locked_out_user  | secret_sauce | Sorry, this user has been locked out           |
