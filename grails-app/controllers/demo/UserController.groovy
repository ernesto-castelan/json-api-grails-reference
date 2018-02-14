package demo

import vnd.json.api.Fieldset
import vnd.json.api.SimpleSorting

class UserController {

    static allowedMethods = [index:'GET', show:'GET']
    static responseFormats = ['json']

    def index() {
        Fieldset fieldset = new Fieldset(params)
        SimpleSorting sorting = new SimpleSorting(params, User.sortFields)

        if (sorting.errors) {
            respond sorting.errors, status:400, view:'/jsonapi/errors'
            return
        }

        List<User> userList = User.list(sorting.map)
        respond userList:userList, fieldset:fieldset
    }

    def show(Long id) {
        Fieldset fieldset = new Fieldset(params)
        SimpleSorting sorting = new SimpleSorting(params, [])

        if (sorting.errors) {
            respond sorting.errors, status:400, view:'/jsonapi/errors'
            return
        }

        User user = User.get(id)

        if (!user) {
            response.status = response.SC_NOT_FOUND
            return
        }

        respond user:user, fieldset:fieldset
    }
}
