Feature: Subscribe
  User subscribes to a pusher instance using instance id

  Background:
    Given I am on the "Instance" screen

  Scenario: User subscribes with empty instance id
    Given I set "" as instance id
    When I try to subscribe
    Then I should see message "Please enter your Pusher Instance ID."

  Scenario: User subscribes with empty interests
    Given I set "00000000-0000-0000-0000-000000000000" as instance id
    When I try to subscribe
    Then I should see message "Please add at least 1 interest before subscribing."

  Scenario: User subscribes with no internet access
    Given Internet connection is turned "off"
    And I set "00000000-0000-0000-0000-000000000000" as instance id
    And I add "test" as an interest
    When I try to subscribe
    Then I should see message "Network unavailable."

  Scenario: User subscribes with valid data and internet access
    Given Internet connection is turned "on"
    And I set "00000000-0000-0000-0000-000000000000" as instance id
    And I add "test" as an interest
    When I try to subscribe
    Then I am on the "Notifications" screen

