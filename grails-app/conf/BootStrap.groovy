import grails.util.Environment

class BootStrap {
    def invitationService
    
    def init = { sc ->
        if (Environment.current == Environment.DEVELOPMENT) {
            //TODO see config.groovy for mail settings before run-app
            invitationService.inviteUser('keentester@somedomain.com', 'beta')
            invitationService.inviteUser('stalker@applovers.com', 'alpha')
        }
    }
    
    def destroy = {
        
    }
}