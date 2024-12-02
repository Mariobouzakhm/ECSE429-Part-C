@Story04

  Feature: Marking Todo as done/undone
    As a student
    I want to mark a Todo Item as Done/Undone
    so I can obtain better visualization on type of todos remaining.

  Background:
    Given The application is running

  # Normal Flow
  Scenario Outline: Change Todo Status
    Given todo with title "Mock Todo" and status "<initial_status>"
    When I want to change the status of todo to "<new_status>"
    Then returned status code is "<status_code>"
    And the new status of todo is "<final_status>"

    Examples:
      | initial_status | new_status | status_code | final_status |
      | true           | false      | 200         | false        |

  # Alternative Flow
  Scenario Outline: Change Todo Status
    Given todo with title "Mock Todo" and status "<initial_status>"
    When I want to change the status of todo to "<new_status>"
    Then returned status code is "<status_code>"
    And the new status of todo is "<final_status>"

    Examples:
      | initial_status | new_status | status_code | final_status |
      | true           | false      | 200         | false        |

  # Error Flow
  Scenario Outline: Change Todo Status
    Given todo with title "Mock Todo"
    When I want to wrongly change the status of todo to "<status>"
    Then returned status code is "<status_code>"
    And error message "<error>" is returned

    Examples:
      | status | status_code | error |
      |        | 400         | No such todo entity instance with GUID or ID 0 found  |