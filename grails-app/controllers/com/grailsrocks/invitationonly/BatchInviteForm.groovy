package com.grailsrocks.invitationonly

import org.codehaus.groovy.grails.validation.Validateable
import org.codehaus.groovy.grails.commons.ApplicationHolder
import  org.codehaus.groovy.grails.commons.ConfigurationHolder

@Validateable
class BatchInviteForm {
    
    String subject  = "You've been invited to: " + ApplicationHolder.application.metadata.'app.name'
    String addresses 
    String senderAddress = ""
    String message = """
Hi,

We'd love it if you'd give ${ApplicationHolder.application.metadata.'app.name'} a try.

Just click on the link below:

${ConfigurationHolder.config.grails.serverURL ?: 'No grails.serverURL set'}

Thanks!
"""
    String groupName 
    boolean approved = false
    
    static constraints =  {
        subject(maxSize:80, nullable: false, blank: false)
        addresses(maxSize:50000, nullable: false, blank: false)
        senderAddress(email:true, maxSize:80, nullable: false, blank: false)
        message(maxSize:10000, nullable: false, blank: false)
        groupName(maxSize:80, nullable: false, blank: false)
    }
}