package com.grailsrocks.invitationonly

import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.codehaus.groovy.grails.validation.Validateable

@Validateable
class SingleApproveForm {

    def grailsApplication = ApplicationHolder.application

    String subject  = "Your request to use " + grailsApplication.metadata.'app.name' + ' has been approved'
    String senderAddress = ""
    String message = """
Hi,

We've approved your invitation request for ${ApplicationHolder.application.metadata.'app.name'}!

Just click on the link below to take part:

${grailsApplication.config.grails.serverURL ?: 'No grails.serverURL set'}

Thanks!
"""

    static constraints =  {
        subject(maxSize:80, nullable: false, blank: false)
        senderAddress(email:true, maxSize:80, nullable: false, blank: false)
        message(maxSize:10000, nullable: false, blank: false)
    }
}