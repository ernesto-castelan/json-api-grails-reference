package demo


import vnd.json.api.Fieldset

class UserController {

    static allowedMethods = [index:'GET', show:'GET']
    static responseFormats = ['json']

    def index() {
        Fieldset fieldset = new Fieldset(params)
        List<User> userList = User.list()
        respond userList:userList, fieldset:fieldset
    }

    def show(Long id) {
        Fieldset fieldset = new Fieldset(params)
        User user = User.get(id)

        if (!user) {
            response.status = response.SC_NOT_FOUND
            return
        }

        respond user:user, fieldset:fieldset
    }
}
