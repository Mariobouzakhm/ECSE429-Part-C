Feature: Remove a todo
  As a user I want to remove a todo to delete any todos that I do not need anymore.

  Background:
    Given The application is running
    And Todos exist:
      | todo_title            | status  | todo_desc             |
      | Prank Clowns          | false   | water interior plants |
      | Have fun              | true    | asssignment 2         |
      | Think deeply          | false   | Quiz 1                |

    And Following projects exist:
      | project_title   | description | active | completed |
      | House remodel   |             | false  | false     |
      | Surprise party  |             | false  | false     |
      | Lab             |             | false  | false     |
    And todos to a project exist:
      | todo_title              | p_title         |
      | Clean Counter           | House remodel   |
      | Buy birthday candles    | Surprise party  |
      | Take measurements       | Lab             |

  Scenario Outline: The user successfully removes an existing todo with no projects or todos (Normal Flow)
    When I remove a todo with title "<todo_title>"
    Then I should receive a confirmation that my operation was successful
    And todo "<todo_title>" should not show

    Examples:
      | todo_title        |
      | Prank Clowns      |
      | Have fun          |
      | Think deeply      |

  Scenario Outline: The user successfully removes a todo related to a project (Alternate Flow)
    When I remove a todo with title "<todo_title>"
    Then I should receive a confirmation that my operation was successful
    And todo "<todo_title>" for project "<p_title>" should not show

    Examples:
      | todo_title              | p_title         |
      | Clean Counter           | House remodel   |
      | Buy birthday candles    | Surprise party  |
      | Take measurements       | Lab             |

  Scenario Outline: The user attempts to delete a todo that does not exist (Error Flow)
    Given The todo "<todo_title>" does not exist
    When I remove a non-existent todo with title "<todo_title>"
    Then I should receive an error informing me that the requested resource was not found

    Examples:
      | todo_title |
      | School    |
      | Vacation  |
      | Winter    |
