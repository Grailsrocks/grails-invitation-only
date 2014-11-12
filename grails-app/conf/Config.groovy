
// The following properties have been added by the Upgrade process...
grails.views.default.codec="none" // none, html, base64
grails.views.gsp.encoding="UTF-8"

grails.validateable.packages = ['com.grailsrocks']

log4j = {
    error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
            'org.codehaus.groovy.grails.web.pages', //  GSP
            'org.codehaus.groovy.grails.web.sitemesh', //  layouts
            'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
            'org.codehaus.groovy.grails.web.mapping', // URL mapping
            'org.codehaus.groovy.grails.commons', // core / classloading
            'org.codehaus.groovy.grails.plugins', // plugins
            'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
            'org.springframework',
            'org.hibernate',
            'net.sf.ehcache.hibernate'

    warn   'org.mortbay.log'

    debug   'grails.app.controllers.com.grailsrocks', 'grails.app.services.com.grailsrocks',
            'grails.app.domain.com.grailsrocks'
}

invitation.only.admin.layout = "invitationAdmin"

environments {
    development {
        grails {
            mail {
                // Hey you need to update this to run-app
                host = "smtp.gmail.com"
                port = 465
                props = ["mail.smtp.auth":"true",
                    "mail.smtp.socketFactory.port":"465",
                    "mail.smtp.socketFactory.class":"javax.net.ssl.SSLSocketFactory",
                    "mail.smtp.socketFactory.fallback":"false"]
                username = "xyz@gmail.com"
                password = "xyz"
            }
        }
        grails.mail.overrideAddress = "xyzabc@gmail.com"
    }
}