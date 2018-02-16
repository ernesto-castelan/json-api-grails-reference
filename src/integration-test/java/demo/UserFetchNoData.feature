@nodata
Feature: User data fetching with no data

Background:
    Given url serverUrl

Scenario: Fetch empty user list
    Given path '/users'
    When method get
    Then status 200
    And match response == {'data':[]}

Scenario: Fetch non existing user
    Given path '/users/1'
    When method get
    Then status 404
