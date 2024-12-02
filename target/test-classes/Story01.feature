@Story01

  Feature: Create a todo list items
    As a student
    I create TODO items to be able to keep track of tasks associated with projects and courses
    so I can manage deadlines properly and avoid balance my courselead

  Background:
    Given The application is running


  # Normal Flow
  Scenario Outline: Create todo list
    When I create a new todo list item called "<title>", with completed status "<status>", and description "<description>"
    Then returned status code is "<status_code>"
    And todo with title "<title>" is created

    Examples:
      | title               | status            | description          | status_code |
      | ECSE 429 Project B  | false             | Due in Two days      | 201         |

  # Alternate Flow
  Scenario Outline: Create todo list
    When I create a new todo list item called "<title>", with completed status "<status>", and description "<description>"
    Then returned status code is "<status_code>"
    And todo with title "<title>" is created

    Examples:
      | title               | status           | description          | status_code |
      | ECSE 429 Project B  |                  |                      | 201         |

  # Error Flow
  Scenario Outline: Create todo list
    When I create a new todo list item called "<title>", with completed status "<status>", and description "<description>"
    Then returned status code is "<status_code>"
    And error message "<error>" is returned

    Examples:
      | title       | status           | description          | status_code | error                                       |
      |             | true             |                      | 400         | Failed Validation: title : can not be empty |