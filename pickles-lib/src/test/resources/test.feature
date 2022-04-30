Feature: test

  Scenario: test
    Given Integer generator named "key1"
    And Integer generator from 100 to 114 with increment 5 and "NULL" limit behavior named "key2"
    And Generators group "key2,key1"
    When generate 6 messages
    Then generator "key2" value is 105
    Then generator "key1" value is 2