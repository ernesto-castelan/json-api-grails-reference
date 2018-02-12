package json.api.grails.reference

import demo.Article
import demo.Review
import demo.User
import grails.util.Environment

class BootStrap {

    def init = { servletContext ->
        if (Environment.current == Environment.DEVELOPMENT) {
            if (!User.count()) {
                new User(username:'mike', fullname:'Michael Wazowski').save(flush:true)
                new User(username:'sulley', fullname:'James P. Sullivan').save(flush:true)
                new User(username:'randall', fullname:'Randall Boggs').save(flush:true)
                assert User.count() == 3
            }
            if (!Article.count()) {
                new Article(author:User.first(),
                            title:'Lorem ipsum',
                            content:'Donec pharetra quis tellus vel mattis. Sed non turpis ultrices.').save(flush:true)
                assert Article.count() == 1
            }
            if (!Review.count()) {
                new Review(author:User.first(),
                           article:Article.first(),
                           score:5,
                           comment:'Malesuada arcu eu.').save(flush:true)
                new Review(author:User.last(),
                           article:Article.first(),
                           score:4).save(flush:true)
                assert Review.count() == 2
            }
        }
    }

    def destroy = {
    }
}
