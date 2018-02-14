package vnd.json.api

/**
 * Error represents a JSON API error object according to
 * http://jsonapi.org/format/1.0/#error-objects
 */
class Error {

    /** A unique identifier for this particular occurrence of the problem */
    String id

    /** A link to further details about this particular occurrence of the problem */
    String about

    /** The HTTP status code applicable to this problem */
    Integer status

    /** An application-specific error code */
    String code

    /** A short, human-readable summary of the problem */
    String title

    /** A human-readable explanation specific to this occurrence of the problem */
    String detail

    /** A JSON Pointer to the to the source of the error */
    String sourcePointer

    /** Name of the query parameter that caused the error */
    String sourceParameter

    /** Non-standard meta-information about the error */
    Map meta
}
