import grails.util.Environment

class BootStrap {
    def invitationService
    
    def init = { sc ->
        if (Environment.current == Environment.DEVELOPMENT) {
            invitationService.inviteUser('keentester@somedomain.com', 'beta')
            invitationService.inviteUser('stalker@applovers.com', 'alpha')
        }
    }
    
    def destroy = {
        
    }
}