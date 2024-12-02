@Story02

  Feature: Changing todo description
    As a student
    I want to change the description of a todo item
    so it better reflects the current needs of the todo item.


    Background:
      Given The application is running

  # Normal Flow
  Scenario Outline: Change todo description
    Given a todo with description "<description>"
    When I change the description of the todo to "<new_description>"
    Then returned status code is "<status_code>"
    And the new description of the todo is "<final_description>"

    Examples:
      | description     | new_description     | status_code | final_description   |
      | Due in two days | needs a lot of work | 200         | needs a lot of work |

  # Alternate Flow
    Scenario Outline: Change todo description
      Given a todo with description "<description>"
      When I change the description of the todo to "<new_description>"
      Then returned status code is "<status_code>"
      And the new description of the todo is "<final_description>"

      Examples:
        | description     | new_description     | status_code | final_description   |
        | Due in two days |                     | 200         |                     |

  # Error Flow
    Scenario Outline: Change todo description
      Given a todo with non-existing description "<non_existing_description>"
      When I change the description of the todo to "<new_description>"
      Then returned status code is "<status_code>"
      And error message "<error>" is returned

      Examples:
        | non_existing_description         | new_description     | status_code | error   |
        | description_missing              |                     | 404         | No such todo entity instance with GUID or ID 0 found |