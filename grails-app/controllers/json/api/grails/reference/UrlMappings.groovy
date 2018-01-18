package json.api.grails.reference

class UrlMappings {

    static mappings = {

        group "/users", {
            get "/" (controller:'user', action:'index')
            get "/$id" (controller:'user', action:'show')
        }

    }
}
