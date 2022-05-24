Feature: test

  Scenario: test
    Given Integer data named key1
    And Integer data from 100 to 114 with increment 5 and NULL limit behavior named key2
    And Generator group "key2,key1"
    When generate 4 data
    Then format "key2" value is 105
    And format "key1" value is 2

  Scenario: sequential time
    Given Integer data from 0 to 59 with increment 10 named seconds
    And Integer data from 0 to 59 named minutes
    And Integer data from 0 to 23 named hours
    And Generator group "seconds,minutes,hours"
    And String format "%02d:%02d:%02d" "hours,minutes,seconds" named time
    Then format "time" value is "00:00:00"
    When generate 1 data
    Then format "time" value is "00:00:10"
    When generate 6 data
    Then format "time" value is "00:01:10"
    # adding 360 x 10 = 3600 seconds is same as adding 1 hour
    When generate 360 data
    Then format "time" value is "01:01:10"

  Scenario: colours
    Given Value data "blue,green,red,amber,orange,black,white" named "colours"
    And List data from "colours" min 0 max 7 named "favourite_colours"
    And display "favourite_colours"
    When generate 5 data
    Then format "favourite_colours" value is like "\[\S*]"
    And dont display "favourite_colours"
