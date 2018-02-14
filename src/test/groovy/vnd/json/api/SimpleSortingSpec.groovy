package vnd.json.api

import org.grails.testing.GrailsUnitTest
import spock.lang.Specification
import spock.lang.Unroll

class SimpleSortingSpec extends Specification implements GrailsUnitTest {

    @Unroll("Sorting returns #expectedErrorCount errors and correct data when #reason")
    void "Test sorting"() {
        given: "A sorting instance"
            SimpleSorting sorting = new SimpleSorting(params, ['username', 'website'])

        when: "Obtaining the sorting map"
            Map sortMap = sorting.map

        then: "Result is expected"
            sortMap == expectedMap
            sorting.errors.size() == expectedErrorCount

        where:
            params                          ||expectedErrorCount    |expectedMap                    |reason
            ['sort':'username']             ||0                     |[sort:'username', order:'asc'] |'sorting by username asc'
            ['sort':'website']              ||0                     |[sort:'website', order:'asc']  |'sorting by website asc'
            ['sort':'-website']             ||0                     |[sort:'website', order:'desc'] |'sorting by website desc'
            ['sort':'-username,website']    ||1                     |[:]                            |'sorting by two fields'
            ['sort':'nodef']                ||1                     |[:]                            |'sorting by non allowed field'
            ['sort':'']                     ||0                     |[:]                            |'sort parameter is empty'
            ['nosort':'something']          ||0                     |[:]                            |'sort parameter is not specified'
            [:]                             ||0                     |[:]                            |'request parameters are empty'
    }

    @Unroll("Sorting returns #expectedErrorCount errors and correct data when sorting is not supported and #reason")
    void "Test sorting with no allowed fields"() {
        given: "A sorting instance"
            SimpleSorting sorting = new SimpleSorting(params, [])

        when: "Obtaining the sorting map"
            Map sortMap = sorting.map

        then: "Result is expected"
            sortMap == expectedMap
            sorting.errors.size() == expectedErrorCount

        where:
            params                          ||expectedErrorCount    |expectedMap    |reason
            ['sort':'username']             ||1                     |[:]            |'sorting by username is requested'
            ['sort':'']                     ||0                     |[:]            |'sort parameter is empty'
            ['nosort':'something']          ||0                     |[:]            |'sort parameter is not specified'
            [:]                             ||0                     |[:]            |'request parameters are empty'
    }
}
