package com.grailsrocks.invitationonly

class UserInvitation {
    String email
    String groupName
    Boolean approved = false
    
    Date dateCreated
    Date dateApproved
    Date dateFirstParticipated
    
    static constraints = {
        email(email:true, maxSize:80, nullable: false, blank: false)
        groupName(maxSize:40, nullable: false, blank: false)
        dateApproved(nullable: true)
        dateFirstParticipated(nullable: true)
    }
    
    void approve() {
        this.approved = true
        if (!this.dateApproved) {
            this.dateApproved = new Date()
        }
    }
    
    void participated() {
        if (!this.dateFirstParticipated) {
            this.dateFirstParticipated = new Date()
        }
    }
}
