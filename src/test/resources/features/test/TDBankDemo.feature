Feature: TD Bank Demo

  Scenario: Navigate to the About Us Page
    Given I am on the 'https://www.td.com/us/en/personal-banking' website
    When I select a the About Us option in the bottom nav
    Then the About Us page is displayed
