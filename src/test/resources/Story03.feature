@Story03

  Feature: Add category to Todo
    As a student
    I want to be able to categorize my todo list items to
    so I can obtain better visualization on type of todos remaining.

    Background:
      Given The application is running
      Given todo with title "Mock Todo"
      And todo with title "Mock Todo 2"
      And category with title "Category1" and description "myFirstCategory" created
      And category with title "Category2" and description "mySecondCategory" created
      And category with id "1" is associated with todo with id "1"

  # Normal Flow
  Scenario Outline: Add Category to todo item
    When I want to add a category with id "<categoryId>" to the todo with id "<id>"
    Then returned status code is "<status_code>"
    And number of categories associated with todo is "<number>"

    Examples:
      | id | categoryId          | status_code      | number |
      | 1  | 2                   | 201              | 2      |

  # Alternative Flow
    Scenario Outline: Add Category to todo item
      When I want to add a category with id "<categoryId>" to the todo with id "<id>"
      Then returned status code is "<status_code>"
      And number of categories associated with todo is "<number>"

      Examples:
        | id | categoryId          | status_code      | number |
        | 2  | 2                   | 201              | 2      |

  # Error Flow
    Scenario Outline: Add Category to todo item
      Given todo with title "Mock Todo"
      When I want to add a category with id "<categoryId>" to the todo with non existing id "<id>"
      Then returned status code is "<status_code>"
      And error message "<error>" is returned

      Examples:
        | id | categoryId          | status_code      | error                                                |
        | 0  | 1                   | 404              | No such todo entity instance with GUID or ID 0 found |