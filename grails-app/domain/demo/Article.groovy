package demo

class Article {

    User author
    String title
    String content
    Date dateCreated
    Date lastUpdated

    static constraints = {
        author  nullable:false
        title   nullable:false, blank:false, maxSize:255
        content nullable:false, blank:false
    }

    static mapping = {
        version false
        content type:'text'
    }
}
