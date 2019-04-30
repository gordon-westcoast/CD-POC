Feature: OPG Login Screen
Scenario: Login screen contains correct header
Given I have opened the browser
When I navigate to the OPG login page
Then The header label should read "OPG Administration Login (Test)"
