package vnd.json.api

import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Fieldset parses and stores the sparse fieldset request according to
 * http://jsonapi.org/format/1.0/#fetching-sparse-fieldsets
 *
 * Rendering the requested fields is responsibility of the views.
 */
class Fieldset {

    /** Regex pattern for the fields parameters */
    private static final Pattern PARAM_FIELDS_PATTERN = ~/^fields\[(.+)]$/

    /** Internal fields list */
    private final Map fields

    /**
     * Default constructor. Populates the internal field list.
     *
     * @param params Request parameters
     */
    Fieldset(Map params) {
        fields = parseParams(params)
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

    /**
     * Finds and parses the fields parameters from the request parameters.
     *
     * @param params Request parameters
     * @return A map with the structure [type:[field,field,...], type:[field, ...], ...]
     */
    private static Map parseParams(Map params) {
        Map fieldsParams = params.findAll { String key, String value -> key ==~ PARAM_FIELDS_PATTERN }
        fieldsParams.collectEntries { String key, String value ->
            Matcher regexMatch = (key =~ PARAM_FIELDS_PATTERN)
            String resourceType = regexMatch[0][1]
            [(resourceType):value.split(',')]
        }
    }
}
