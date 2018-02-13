package vnd.json.api

import java.util.regex.Matcher
import java.util.regex.Pattern

class Fieldset {

    /** Regex pattern for the fields parameters */
    private static final Pattern FIELDS_PATTERN = ~/^fields\[(.+)]$/

    /** Internal fields list */
    private Map fields

    /**
     * Default constructor. Populates the internal field list from the request parameters.
     *
     * @param params Request parameters
     */
    Fieldset(Map params) {
        Map fieldsParams = params.findAll { String key, String value -> key ==~ FIELDS_PATTERN }
        fields = fieldsParams.collectEntries { String key, String value ->
            Matcher regexMatch = (key =~ FIELDS_PATTERN)
            String fieldName = regexMatch[0][1]
            [(fieldName):value.split(',')]
        }
    }

    /**
     * Indicates if a field should be rendered for a specific resource type.
     * Resource types that are not explicitly specified, are considered to include all fields.
     *
     * @param resourceType The resource type name
     * @param fieldName The field name
     * @return true if the field of the resource type is to be rendered, false otherwise.
     */
    Boolean contains(String resourceType, String fieldName) {
        if (fields.containsKey(resourceType)) fields[resourceType].contains(fieldName)
        else true
    }
}
