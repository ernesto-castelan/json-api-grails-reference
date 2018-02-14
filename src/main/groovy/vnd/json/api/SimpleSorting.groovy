package vnd.json.api

/**
 * SimpleSorting handles the sorting request according to
 * http://jsonapi.org/format/1.0/#fetching-sorting
 *
 * Limitations:
 * - Only one sort field is supported
 * - No dot-separated fields are supported
 */
class SimpleSorting {

    private static final String PARAM_SORT_NAME = 'sort'
    private static final String FIELD_SEPARATOR = ','
    private static final String DESCENDING_PREFIX = '-'

    /** Internal sorting list */
    private List<Map> sortList

    /** Internal error list */
    private Error error

    /**
     * Default constructor. Validate parameters and populates the internal sorting list.
     *
     * @param params Request parameters
     */
    SimpleSorting(Map params, List<String> allowedFields) {
        parseParams(params)
        validate(allowedFields)
    }

    /**
     * Returns a sorting map useful for most GORM functions
     *
     * @return A sorting map
     */
    Map getMap() {
        sortList ? sortList[0] : [:]
    }

    /**
     * Returns the errors generated by this request
     *
     * @return A JSON API error list
     */
    List<Error> getErrors() {
        error ? [error] : []
    }

    /**
     * Parses the sort parameter from the request parameters and populates the internal sorting list.
     *
     * @param params Request parameters
     */
    private void parseParams(Map params) {
        if (params[PARAM_SORT_NAME]) {
            sortList = params[PARAM_SORT_NAME].split(FIELD_SEPARATOR).collect { String field ->
                if (field.startsWith(DESCENDING_PREFIX)) [sort:field.drop(DESCENDING_PREFIX.length()), order:'desc']
                else [sort:field, order:'asc']
            }
        }
    }

    /**
     * Validates the internal sorting list against the limitations and the allowed fields.
     * In case of error, populates the internal error list and clears the internal sorting list.
     *
     * @param allowedFields Allowed sort fields
     */
    private void validate(List<String> allowedFields) {
        if (sortList) {
            if (!allowedFields) {
                error = new Error(status:400,
                                  code:'sort-not-supported',
                                  title:'Sorting is not supported for this endpoint')
            }
            else if (sortList.size() > 1) {
                error = new Error(status:400,
                                  code:'sort-many-fields',
                                  title:'Multiple sort fields are not supported for this endpoint')
            }
            else if (!allowedFields.contains(sortList[0].sort)) {
                error = new Error(status:400,
                                  code:'sort-bad-field',
                                  title:'The specified sort field is not valid for this resource')
            }
        }
        if (error) {
            sortList = null
        }
    }
}
