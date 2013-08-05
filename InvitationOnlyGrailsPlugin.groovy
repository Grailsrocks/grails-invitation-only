class InvitationOnlyGrailsPlugin {

    def version = "1.1"
    def grailsVersion = "2.0 < *"
    def groupId = "com.cc.plugins"
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    def author = "Marc Palmer"
    def authorEmail = "marc@grailsrocks.com"
    def title = "Simple way to track and check user-invites by email address"
    def description = '''\\
A simple mechanism for tracking who is invited to trial your service, and checking if an address is permitted to try it.
'''

    def documentation = "http://grails.org/plugin/invitation-only"

}