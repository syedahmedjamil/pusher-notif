Feature: View Notification
  User can view notification for the subscribed interest

  Background:
    Given I am subscribed to instance

  Scenario: User views notification from status bar
    When I receive push notification
    Then I should see notification in status bar

  Scenario: User views notification from notification screen
    When I receive push notification
    Then I should see notification in the list

