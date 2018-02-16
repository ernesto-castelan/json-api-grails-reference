Feature: User data fetching with test data

Background:
    Given url serverUrl

Scenario: Fetch user list
    Given path '/users'
    When method get
    Then status 200
    And match response.data == '#[6]'
    And match response.data[0] contains {'type':'users', 'id':'#string'}
    And match response.data[0].attributes == {'username':'alfa', 'fullname':'alfa zulu', 'website':'#null'}

Scenario: Fetch single user
    Given path '/users/1'
    When method get
    Then status 200
    And match response.data contains {'type':'users', 'id':'1'}
    And match response.data.attributes == {'username':'alfa', 'fullname':'alfa zulu', 'website':'#null'}

Scenario: Fetch user list with sparse fieldsets
    Given path '/users'
    And params {'fields[users]':'username'}
    When method get
    Then status 200
    And match response.data == '#[6]'
    And match response.data[0] contains {'type':'users', 'id':'#string'}
    And match response.data[0].attributes == {'username':'alfa'}

Scenario: Fetch single user with sparse fieldsets
    Given path '/users/1'
    And params {'fields[users]':'fullname,website'}
    When method get
    Then status 200
    And match response.data contains {'type':'users', 'id':'1'}
    And match response.data.attributes == {'fullname':'alfa zulu', 'website':'#null'}

Scenario: Fetch user list with sorting
    Given path '/users'
    And params {'sort':'-username'}
    When method get
    Then status 200
    And match response.data == '#[6]'
    And match response.data[*].attributes.username == ['foxtrot', 'echo', 'delta', 'charlie', 'bravo', 'alfa']

Scenario: Fetch single user with sorting is not supported
    Given path '/users/1'
    And params {'sort':'-username'}
    When method get
    Then status 400
    And match response.errors[0] contains {'code':'sort-not-supported'}


