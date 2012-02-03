package com.grailsrocks.invitationonly

class InvitationServiceTests extends GroovyTestCase {
    void testStatistics() {
        def s = new InvitationService()

        s.inviteUser('keentester@somedomain.com', 'beta')
        s.inviteUser('stalker@applovers.com', 'beta')
        s.inviteUser('realcustomer@alovelyfluffyplace.org', 'alpha')
        s.inviteUser('stalker@applovers.com', 'alpha')

        def stats = s.statistics
        println stats
        assert stats.totalInvitationsPerGroup.beta == 2
        assert stats.totalInvitationsPerGroup.alpha == 2
        assert stats.totalApprovedInvitationsPerGroup.beta == null
        assert stats.totalApprovedInvitationsPerGroup.alpha == null
        assert stats.totalPendingInvitationsPerGroup.beta == 2
        assert stats.totalPendingInvitationsPerGroup.alpha == 2

        s.approve('keentester@somedomain.com', 'beta')

        stats = s.statistics
        println stats
        assert stats.totalInvitationsPerGroup.beta == 2
        assert stats.totalInvitationsPerGroup.alpha == 2
        assert stats.totalApprovedInvitationsPerGroup.beta == 1
        assert stats.totalApprovedInvitationsPerGroup.alpha == null
        assert stats.totalPendingInvitationsPerGroup.beta == 1
        assert stats.totalPendingInvitationsPerGroup.alpha == 2

        s.approve('stalker@applovers.com', 'alpha')

        stats = s.statistics
        println stats
        assert stats.totalInvitationsPerGroup.beta == 2
        assert stats.totalInvitationsPerGroup.alpha == 2
        assert stats.totalApprovedInvitationsPerGroup.beta == 1
        assert stats.totalApprovedInvitationsPerGroup.alpha == 1
        assert stats.totalPendingInvitationsPerGroup.beta == 1
        assert stats.totalPendingInvitationsPerGroup.alpha == 1
    }
    
    void testRepeatedInviteRemainsUnapproved() {
        def s = new InvitationService()

        assertFalse s.inviteUser('keentester@somedomain.com', 'beta').approved
        assertFalse s.inviteUser('keentester@somedomain.com', 'beta').approved

    }
}