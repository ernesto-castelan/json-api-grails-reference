# JSON API Grails Reference

A reference implementation of [JSON API](http://jsonapi.org/) in [Grails 3](https://grails.org/).

Made for:
+ [JSON API 1.0](http://jsonapi.org/format/1.0/)
+ [Grails 3.3.0](http://docs.grails.org/3.3.0/)

## Implementation status

**WORK IN PROGRESS**

+ [ ] Content Negotiation
    + [ ] Server Responsibilities
+ [ ] Fetching Data
    + [x] Fetching Resources (`users`)
    + [ ] Fetching Relationships
    + [ ] Inclusion of Related Resources
    + [x] Sparse Fieldsets (`users`)
    + [x] Sorting (`users`)
    + [ ] Pagination
    + [ ] Filtering
+ [ ] Creating, Updating and Deleting Resources
    + [ ] Creating Resources
    + [ ] Updating Resources
    + [ ] Updating Relationships
    + [ ] Deleting Resources
+ [ ] Errors
    + [ ] Processing Errors
    + [ ] Error Objects

## Known limitations
+ Sparse Fieldsets
    + Using fieldsets can leave empty `attributes` and `relationships` objects
+ Sorting
    + Only one sort field is supported
    + No dot-separated fields are supported
