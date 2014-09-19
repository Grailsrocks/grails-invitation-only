package com.grailsrocks.invitationonly

import grails.util.Holders
import org.codehaus.groovy.grails.validation.Validateable

@Validateable
class BatchApproveForm {

    // TODO use spring bean injection instead of ApplicationHolder.
    def grailsApplication = Holders.grailsApplication

    String subject  = "Your request to use " + grailsApplication.metadata.'app.name' + ' has been approved'
    String senderAddress = ""
    String message = """
Hi,

We've approved your invitation request for ${grailsApplication.metadata.'app.name'}!

Just click on the link below to take part:

${grailsApplication.config.grails.serverURL ?: 'No grails.serverURL set'}

Thanks!
"""
    Integer numberToApprove
    String group

    static constraints =  {
        group blank:false
        numberToApprove min:1, max:10000
        subject maxSize:80, blank: false
        senderAddress email:true, maxSize:80, blank: false
        message maxSize:10000, blank: false
    }
}