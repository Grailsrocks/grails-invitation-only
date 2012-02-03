
// The following properties have been added by the Upgrade process...
grails.views.default.codec="none" // none, html, base64
grails.views.gsp.encoding="UTF-8"

grails.validateable.packages = ['com.grailsrocks']

log4j = {
    root {
        info 'stdout'
    }
    
    error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
	       'org.codehaus.groovy.grails.web.pages' //  GSP
    info   'grails.app'

    debug   'grails.app.controller',
            'grails.app.service',
            'grails.app.task',
            'grails.app.domain'
//            'org.codehaus.groovy.grails.web.mapping' // URL mapping
    
}

invitation.only.admin.layout = "invitationAdmin"

environments {
    development {
        grails {
            mail {
                // Hey you need to update this to run-app
                host = "mail.anyware.co.uk"
            }
        }
    }
    
    test {
        log4j = {
            root {
                info 'stdout'
            }

            warn  'org.codehaus.groovy.grails.web.servlet',  //  controllers
        	       'org.codehaus.groovy.grails.web.pages' //  GSP
            debug   'grails.app', 'com.grailsrocks'

        }
    }
}