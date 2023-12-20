Feature: Add Interests
  User can add interests

  Background:
    Given I am on the "Instance" screen

  Scenario: User adds interests from home screen
    When I add "reddit" as an interest
    And I add "upwork" as an interest
    Then I should see "reddit" as an interest
    And I should see "upwork" as an interest

  Scenario: User adds duplicate interests from home screen
    When I add "reddit" as an interest
    And I add "reddit" as an interest
    Then I should see message "Interest already exists."

  Scenario: User adds empty interests from home screen
    When I add "" as an interest
    Then I should see message "Interest can't be empty."


