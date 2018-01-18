package demo


import grails.rest.*
import grails.converters.*

class UserController {

    static allowedMethods = [index:'GET', show:'GET']
    static responseFormats = ['json']

    def index() {
        List<User> userList = User.list()
        respond userList
    }

    def show(Long id) {
        User user = User.get(id)

        if (!user) {
            response.status = response.SC_NOT_FOUND
            return
        }

        respond user
    }
}
