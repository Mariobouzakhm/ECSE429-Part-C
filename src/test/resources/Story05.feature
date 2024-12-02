@Story05

  Feature: Remove category from todo item
    As a student
    I want to be able to remove category from todo
    so I can obtain better visualization on type of todos remaining.

    Background:
      Given The application is running
      Given todo with title "Mock Todo"
      And category with title "Category1" and description "myFirstCategory" created
      And category with title "Category2" and description "mySecondCategory" created
      And category with id "1" is associated with todo with id "1"

  # Normal Flow
  Scenario Outline: Remove Category to todo item
    When I want to remove a category with id "<categoryId>" to the todo with id "<id>"
    Then returned status code is "<status_code>"

    Examples:
      | id | categoryId          | status_code      |
      | 1  | 1                   | 200              |

  # Alternative Flow
  Scenario Outline: Remove Category to todo item
    When I want to remove a category with id "<categoryId>" to the todo with id "<id>"
    Then returned status code is "<status_code>"

    Examples:
      | id | categoryId          | status_code      |
      | 1  | 2                   | 200              |

  # Error Flow
    Scenario Outline: Remove Category to todo item
      When I want to remove a category with id "<categoryId>" to the todo with non existing id "<id>"
      Then returned status code is "<status_code>"
      And error message "<error>" is returned

      Examples:
        | id | categoryId          | status_code      | error                             |
        | 3  | 1                   | 404              | No such todo entity instance with GUID or ID 0 found |