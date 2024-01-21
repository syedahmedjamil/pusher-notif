Feature: Remove Interests
  User can remove interests

  Background:
    Given I am on the "Instance" screen
    And I add "reddit" as an interest
    And I add "upwork" as an interest

  Scenario: User removes interests from home screen
    When I remove "reddit" as an interest
    And I should not see "reddit" as an interest
    And I should see "upwork" as an interest

