Feature: User data fetching with test data

Background:
    Given url serverUrl

Scenario: Fetch user list
    Given path '/users'
    When method get
    Then status 200
    And match response.data == '#[6]'
    And match response.data[0] contains { 'type':'users', 'id':'#string'}
    And match response.data[0].attributes contains { 'username':'alfa', 'fullname':'alfa zulu', 'website':'#null' }

Scenario: Fetch user attributes
    Given path '/users/1'
    When method get
    Then status 200
    And match response.data contains { 'type':'users', 'id':'1'}
    And match response.data.attributes contains { 'username':'alfa', 'fullname':'alfa zulu', 'website':'#null' }
