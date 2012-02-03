package com.grailsrocks.invitationonly

class AdminInvitationsController {

    def invitationService
    
    static defaultAction = 'main'
    
    def main = {
        [stats:invitationService.statistics, groups: invitationService.groups]
    }
    
    def batchInvite = {
        
    }
    
    def performBatchInvite = { BatchInviteForm form ->
        if (form.hasErrors()) {
            render(view:'batchInvite', model:[form:form])
        } else {
            def c = invitationService.inviteUsersToGroupAndMail( form)
            flash.message = "$c users invited!"
            redirect(action:'main')
        }
    }

    def batchApprove = {
        [groups: invitationService.groups]
    }
    
    def performBatchApproveWithMail = { BatchApproveForm form ->
        if (form.hasErrors()) {
            render(view:'batchApprove', model:[form:form, groups: invitationService.groups])
        } else {
            def c = invitationService.approveUsersAndMail( form.group, form.numberToApprove, form)
            flash.message = "$c users approved for group '${form.group}' and emails sent!"
            redirect(action:'main')
        }
    }

    def performBatchApproveNoMail = { BatchApproveForm form ->
        if (form.hasErrors()) {
            render(view:'batchApprove', model:[form:form, groups: invitationService.groups])
        } else {
            def c = invitationService.approveUsers( form.group, form.numberToApprove)
            flash.message = "$c users approved for group '${form.group}'!"
            redirect(action:'main')
        }
    }

    def export = {
        def axis = params.axis
        def data
        switch (axis) {
            case 'approved': 
                data = invitationService.getApprovedUsersInGroup(params.group)
                break
            case 'pending': 
                data = invitationService.getPendingUsersInGroup(params.group)
                break
            case 'all':
                data = invitationService.getInvitedUsersInGroup(params.group)
                break
        }
            
        def out = response.writer
        response.contentType = "text/csv"
        def c = data.size()
        out << "Total of ${c} email addresses of ${axis} users for group ${params.group} as of "+new Date()
        out << '\n'
        data.each { e -> 
            out << e+'\n' 
        }
        out.flush()
        return null
    }
    
    def approve = {
    }
    
    def performApproveWithMail = { SingleApproveForm form ->
        if (!form.hasErrors()) {
            def inv = invitationService.approveAndMail(params.email, params.group, form)
            if (!inv) {
                flash.message("No such invitation for ${params.email}, can't approve it!")
                redirect(action:'list')
            } else {
                flash.message = "User ${params.email} approved for '${params.group}'!"
                redirect(action:'list')
            }
        } else {
            flash.message = "Please check your form for errors"
            render(view:'approve', model:[form:form])
        }
    }
    
    def performApproveNoMail = {
        def inv = invitationService.approve(params.email, params.group)
        if (!inv) {
            flash.message("No such invitation for ${params.email}, can't approve it!")
            redirect(action:'list')
        } else {
            flash.message = "User ${params.email} approved for '${params.group}'!"
            redirect(action:'list')
        }
    }
    
    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [userInvitationInstanceList: UserInvitation.list(params), userInvitationInstanceTotal: UserInvitation.count()]
    }

    def create = {
        def userInvitationInstance = new UserInvitation()
        userInvitationInstance.properties = params
        return [userInvitationInstance: userInvitationInstance]
    }

    def save = {
        def userInvitationInstance = new UserInvitation(params)
        if (userInvitationInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'userInvitation.label', default: 'UserInvitation'), userInvitationInstance.id])}"
            redirect(action: "show", id: userInvitationInstance.id)
        }
        else {
            render(view: "create", model: [userInvitationInstance: userInvitationInstance])
        }
    }

    def show = {
        def userInvitationInstance = UserInvitation.get(params.id)
        if (!userInvitationInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'userInvitation.label', default: 'UserInvitation'), params.id])}"
            redirect(action: "list")
        }
        else {
            [userInvitationInstance: userInvitationInstance]
        }
    }

    def edit = {
        def userInvitationInstance = UserInvitation.get(params.id)
        if (!userInvitationInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'userInvitation.label', default: 'UserInvitation'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [userInvitationInstance: userInvitationInstance]
        }
    }

    def update = {
        def userInvitationInstance = UserInvitation.get(params.id)
        if (userInvitationInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (userInvitationInstance.version > version) {
                    
                    userInvitationInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'userInvitation.label', default: 'UserInvitation')] as Object[], "Another user has updated this UserInvitation while you were editing")
                    render(view: "edit", model: [userInvitationInstance: userInvitationInstance])
                    return
                }
            }
            userInvitationInstance.properties = params
            if (!userInvitationInstance.hasErrors() && userInvitationInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'userInvitation.label', default: 'UserInvitation'), userInvitationInstance.id])}"
                redirect(action: "show", id: userInvitationInstance.id)
            }
            else {
                render(view: "edit", model: [userInvitationInstance: userInvitationInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'userInvitation.label', default: 'UserInvitation'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def userInvitationInstance = UserInvitation.get(params.id)
        if (userInvitationInstance) {
            try {
                userInvitationInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'userInvitation.label', default: 'UserInvitation'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'userInvitation.label', default: 'UserInvitation'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'userInvitation.label', default: 'UserInvitation'), params.id])}"
            redirect(action: "list")
        }
    }
}
