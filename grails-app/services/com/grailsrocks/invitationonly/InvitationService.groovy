package com.grailsrocks.invitationonly

import groovy.util.ConfigObject

class InvitationService {

    static transactional = true

    def mailService
    def grailsApplication
    
    @Lazy def adminAddress = { configValueOrNull(grailsApplication.config.invitation.only.admin.email) ?: 'admin@localhost' }()

    @Lazy def serverAddress = { configValueOrNull(grailsApplication.config.invitation.only.server.email) ?: 'server@localhost' }()

    // Note: this must not be typed or it breaks in Groovy 1.7.2
    @Lazy def adminNotifications = { 
        def v = configValueOrNull(grailsApplication.config.invitation.only.admin.notify)
        return v == null ? true : v.asBoolean() // toBoolean does not work in Groovy pre-1.7.6
    }()

    def configValueOrNull(v) {
        return v instanceof ConfigObject ? null : v
    }
    
    protected collateGroupCounts(crit) {
        def res = UserInvitation.createCriteria().list(crit)
        def countByGroup = [:]
        res.each { r ->
            countByGroup[r[1]] = r[0]
        }
        return countByGroup
    }

    def inviteUsersToGroupAndMail(def args) {
        assert args.groupName
        assert args.subject
        assert args.addresses
        assert args.message
        assert args.senderAddress
        assert args.approved != null
        
        def addresses = args.addresses
        
        if (addresses instanceof String) {
            // whitespace split
            def newaddresses = addresses.split()
            addresses = []
            // comma split
            newaddresses.each { a ->
                addresses.addAll( a.tokenize(',')*.trim() )
            }
        }

        // Workaround groovy "feature" that calls Boolean.toBoolean(null) if we don't toString
        def app = args.approved.toString().toBoolean()

        log.info "Bulk inviting ${addresses} to group ${args.groupName}"
        addresses.each { recip -> 
            log.debug "Inviting ${recip} to group ${args.groupName} with pre-approval: ${app}"
            inviteUser(recip, args.groupName, app, true)
            log.debug "Sending invite mail to ${recip} for group ${args.groupName}"
            mailService.sendMail {
                to recip
                from args.senderAddress
                subject args.subject
                body args.message
            }
        }
        
        log.debug "Admin notifications enabled: ${adminNotifications}, mails will be sent to ${adminAddress}"
        if (adminNotifications) {
            log.debug "Sending admin notification to $adminAddress"
            mailService.sendMail {
                to adminAddress
                from serverAddress
                subject "New user invitations issued"
                body "A total of ${addresses.size()} new users were invitation to ${args.groupName}.\n\nThe invitations are currently ${app ? 'approved' : 'pending approval'}"
            }
        }
        
        return addresses.size()
    }
    
    def getStatistics() {
        def countByGroup = collateGroupCounts {
             projections {
                    count('id')
                    groupProperty('groupName')
             }
        }
        def countByGroupApproved = collateGroupCounts {
             projections {
                    count('id')
                    groupProperty('groupName')
             }
             eq('approved', true)
        }
        def countByGroupPending = collateGroupCounts {
             projections {
                    count('id')
                    groupProperty('groupName')
             }
             eq('approved', false)
        }
        def countByGroupParticipated = collateGroupCounts {
             projections {
                    count('id')
                    groupProperty('groupName')
             }
             isNotNull('dateFirstParticipated')
        }
        
        def totalsByGroup = [:]
        countByGroup.keySet().each { g -> totalsByGroup[g] = [pending:0, approved:0, participated: 0] }
        countByGroupApproved.each { e -> totalsByGroup[e.key].approved += e.value }
        countByGroupPending.each { e -> totalsByGroup[e.key].pending += e.value }
        countByGroupParticipated.each { e -> totalsByGroup[e.key].participated += e.value }
        
        [   totalInvitationsPerGroup:countByGroup, 
            totalApprovedInvitationsPerGroup:countByGroupApproved, 
            totalPendingInvitationsPerGroup:countByGroupPending,
            totalPaticipatedPerGroup:countByGroupParticipated,
            totalsByGroup:totalsByGroup]
    }
    
    /**
     * Create a new invitation for the user and group, in UNAPPROVED state unless specified otherwise
     */
    def inviteUser(email, group, approved = false, suppressNotifications = false) {
        log.info "Inviting user $email to group ${group} with approval: $approved"
        // Don't create duplicates!
        def invite = UserInvitation.findByEmailAndGroupName(email, group)
        if (!invite) {
            invite = new UserInvitation(email:email, groupName:group, approved:approved)
            invite.validate()
            if (invite.hasErrors()) {
                log.error "Couldn't invite $email, errors: ${invite.errors}"
            }
            assert invite.save()
        } else {
            // Might have changed from non-approved to approved
            if (approved) invite.approve()
        }
        
        log.debug "Admin notifications enabled: ${adminNotifications}, mails will be sent to ${adminAddress}"
        if (adminNotifications && !suppressNotifications) {
            log.debug "Sending admin notification to $adminAddress"
            mailService.sendMail {
                to adminAddress
                from serverAddress
                subject "New user invitation"
                body "The user ${email} requested an invitation to $group.\n\nThe invitation is currently ${approved ? 'approved' : 'pending approval'}"
            }
        }
        
        return invite
    }
    
    boolean isUserInvited(email, group) {
        UserInvitation.findByEmailAndGroupName(email, group) != null
    }
    
    boolean isUserApproved(email, group) {
        UserInvitation.findByEmailAndGroupName(email, group)?.approved
    }
    
    /**
     * Call this to approve a user's invitation to unlock their access to the specified group/feature
     */
    def approve(email, group, boolean suppressNotifications = false) {
        def u = UserInvitation.findByEmailAndGroupName(email, group)
        if (u) {
            log.info "Approved user $email for group ${group}"
            u.approve()

            log.debug "Admin notifications enabled: ${adminNotifications}, mails will be sent to ${adminAddress}"
            if (adminNotifications && !suppressNotifications) {
                log.debug "Sending admin notification to $adminAddress"
                mailService.sendMail {
                    to adminAddress
                    from serverAddress
                    subject "User invitation approved"
                    body "The user ${email} was approved for group $group."
                }
            }
            return u
        } else {
            return null
        }
    }
    
    /**
     * Call this to approve a user's invitation to unlock their access to the specified group/feature
     * and send them a mail message at the same time
     */
    def approveAndMail(email, group, args) {
        def u = approve(email, group)
        log.debug "Sending approval mail to ${email} for group ${group}"
        mailService.sendMail {
            to email
            from args.senderAddress
            subject args.subject
            body args.message
        }
    }
    
    /**
     * Call this to indicate that a user has participated in the specified group, so that
     * the date of this can be recorded. Used to tell when/if people actually start using the
     * service after their invitation is approved.
     */
    def userParticipated(email, group) {
        def u = UserInvitation.findByEmailAndGroupName(email, group)
        if (u) {
            log.info "Recording participation for user $email for group ${group}"
            u.participated()

            log.debug "Admin notifications enabled: ${adminNotifications}, mails will be sent to ${adminAddress}"
            if (adminNotifications) {
                log.debug "Sending admin notification to $adminAddress"
                mailService.sendMail {
                    to adminAddress
                    from serverAddress
                    subject "User participated"
                    body "The user ${email} in group $group has participated after being approved."
                }
            }

            return u
        } else {
            return null
        }
    }

    /**
     * Unlock a batch of invitees for a specific invitation group, up to a certain number, in FIRST COME FIRST SERVED order
     * @return the number of invites approved
     */
    def batchApproveUsers(group, totalToUnlock, Closure c) {
        // @todo use scrollable result set here to avoid blowing heap on big updates! OR a single UPDATE if can get affected row count?
        def u = UserInvitation.findAllByGroupNameAndApproved(group, false, [max:totalToUnlock, sort:'dateCreated', order:'asc'])
        def n = 0
        u.each { invite ->
            approve(invite.email, group, true)
            if (c) {
                c(invite)
            }
            n++
        }
        
        log.debug "Admin notifications enabled: ${adminNotifications}, mails will be sent to ${adminAddress}"
        if (adminNotifications) {
            log.debug "Sending admin notification to $adminAddress"
            mailService.sendMail {
                to adminAddress
                from serverAddress
                subject "Batch invitation approval"
                body "A total of ${n} user invitations for group $group were approved."
            }
        }
        return n
    }
    
    /**
     * Unlock a batch of invitees for a specific invitation group, up to a certain number, in FIRST COME FIRST SERVED order
     * @return the number of invites approved
     */
    def approveUsers(group, totalToUnlock) {
        batchApproveUsers(group, totalToUnlock, null)
    }

    /**
     * Unlock a batch of invitees for a specific invitation group, up to a certain number, in FIRST COME FIRST SERVED order
     * @return the number of invites approved
     */
    def approveUsersAndMail(group, totalToUnlock, form) {
        batchApproveUsers(group, totalToUnlock) { u ->
            mailService.sendMail {
                to u.email
                from form.senderAddress
                subject form.subject
                body form.message
            }
        }
    }

    /**
     * Iterate over invited users in a specific group, passing the UserInvitiation object to the closure for each
     */
    void forEachInvitedUserInGroup(group, Closure c) {
        // @todo use scrollable result set
        UserInvitation.findAllByGroupName(group).each { invite ->
            c(invite)
        }
    }

    /**
     * Iterate over APPROVED invited users in a specific group, passing the UserInvitiation object to the closure for each
     */
    void forEachApprovedUserInGroup(group, Closure c) {
        // @todo use scrollable result set
        UserInvitation.findAllByGroupNameAndApproved(group, true).each { invite ->
            c(invite)
        }
    }

    /**
     * Iterate over PENDING invited users in a specific group, passing the UserInvitiation object to the closure for each
     */
    void forEachPendingUserInGroup(group, Closure c) {
        // @todo use scrollable result set
        UserInvitation.findAllByGroupNameAndApproved(group, false).each { invite ->
            c(invite)
        }
    }
    
    /**
     * Iterate over invited users in a specific group, passing the UserInvitiation object to the closure for each
     */
    def getInvitedUsersInGroup(group) {
        // @todo use scrollable result set
        UserInvitation.withCriteria { 
            projections {
                distinct('email')
            }
            eq('groupName', group)
        }
    }

    /**
     * Iterate over APPROVED invited users in a specific group, passing the UserInvitiation object to the closure for each
     */
    def getApprovedUsersInGroup(group) {
        // @todo use scrollable result set
        UserInvitation.withCriteria { 
            projections {
                distinct('email')
            }
            eq('approved', true)
            eq('groupName', group)
        }
    }

    /**
     * Iterate over PENDING invited users in a specific group, passing the UserInvitiation object to the closure for each
     */
    def getPendingUsersInGroup(group) {
        // @todo use scrollable result set
        UserInvitation.withCriteria { 
            projections {
                distinct('email')
            }
            eq('approved', false)
            eq('groupName', group)
        }
    }
    
    // @todo This is not ideal but we don't have group as a domain class yet, this is fine
    // for most cases and hides the details for the future
    def getGroups() {
        UserInvitation.withCriteria {
            projections {
                distinct('groupName')
            }
        }
    }
}
