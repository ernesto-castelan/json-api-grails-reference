package demo

class User {

    String username
    String fullname
    String website
    Date dateCreated
    Date lastUpdated

    static constraints = {
        username    nullable:false, blank:false, maxSize:50, unique:true, matches:'[0-9a-z]+'
        fullname    nullable:false, blank:false, maxSize:255
        website     nullable:true, blank:false, maxSize:255, url:true
    }

    static mapping = {
        version false
    }

    static String getResourceType() {
        'users'
    }

    static List<String> getSortFields() {
        ['id', 'username', 'fullname', 'website', 'dateCreated', 'lastUpdated']
    }
}
