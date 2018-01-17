package demo

class Review {

    User author
    Article article
    String comment
    Integer score

    static constraints = {
        author  nullable:false
        article nullable:false
        comment nullable:true, blank:false, maxSize:255
        score   nullable:true, range:1..5
    }

    static mapping = {
        version false
    }
}
