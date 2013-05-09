package com.grailsrocks.invitationonly

import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.codehaus.groovy.grails.validation.Validateable

@Validateable
class BatchInviteForm {

    def grailsApplication = ApplicationHolder.application

    String subject  = "You've been invited to: " + grailsApplication.metadata.'app.name'
    String addresses
    String senderAddress = ""
    String message = """
Hi,

We'd love it if you'd give ${ApplicationHolder.application.metadata.'app.name'} a try.

Just click on the link below:

${grailsApplication.config.grails.serverURL ?: 'No grails.serverURL set'}

Thanks!
"""
    String groupName
    boolean approved = false

    static constraints =  {
        subject maxSize:80, blank: false
        addresses maxSize:50000, blank: false
        senderAddress email:true, maxSize:80, blank: false
        message maxSize:10000, blank: false
        groupName maxSize:80, blank: false
    }
}