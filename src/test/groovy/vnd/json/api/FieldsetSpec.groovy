package vnd.json.api

import org.grails.testing.GrailsUnitTest
import spock.lang.Specification
import spock.lang.Unroll

class FieldsetSpec extends Specification implements GrailsUnitTest {

    @Unroll("contains('#type', '#field') returns #expectedResult because #reason")
    void "Test contains method"() {
        given: "Request parameters"
            Map params = ['fields[users]':'username,website',
                          'fields[tests]':'',
                          'fields[multi-tests]':'multi-field',
                          'no_fields[data-a]':'value-a',
                          'fields[data-b]no':'value-b',
                          'data-c':'value-c']
        and: "A fieldset instance"
            Fieldset fieldset = new Fieldset(params)

        when: "Calling the method"
            Boolean result = fieldset.contains(type, field)

        then: "Result is expected"
            result == expectedResult

        where:
            type            |field          ||expectedResult    |reason
            'users'         |'username'     ||true              |'field users.username is included'
            'users'         |'website'      ||true              |'field users.username is included'
            'users'         |'something'    ||false             |'field users.something is not included'
            'tests'         |'something'    ||false             |'no fields are included for tests type'
            'multi-test'    |'multi-field'  ||true              |'type and field with dashes are valid'
            'nodef'         |'something'    ||true              |'any field is valid for not specified types'
            'data-a'        |'nodef'        ||true              |'data-a is not processed as valid fieldset'
            'data-b'        |'nodef'        ||true              |'data-b is not processed as valid fieldset'
            'data-c'        |'nodef'        ||true              |'data-c is not processed as valid fieldset'
    }

    void "Test contains method with empty params"() {
        given: "A fieldset instance"
            Fieldset fieldset = new Fieldset([:])

        when: "Calling the method"
            Boolean result = fieldset.contains('nodef', 'something')

        then: "Result is expected"
            result == true
    }
}
