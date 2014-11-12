<html>
<head>
    <meta name="layout" content="${grailsApplication.config.invitation.only.admin.layout ?: 'main'}" />
    <title>Invitations - Main</title>
</head>
<body>
    <div class="page-header"><h1>Invitations</h1></div>
    
    <g:if test="${flash.message}">
        <p class="info">
            ${flash.message.encodeAsHTML()}
        </p>
    </g:if>
    
    <h2>Statistics</h2><hr>
    
    <table class="table table-bordered table-hover table-striped">
        <tr><th>Invitation group</th><th>Approved</th><th>Pending</th><th>Total in group</th><th>Total participated</th></tr>
        <g:each in="${stats.totalInvitationsPerGroup.keySet().sort()}" var="groupName">
            <tr>
                <td>${groupName.encodeAsHTML()}</td>
                <td>${stats.totalsByGroup[groupName].approved}</td>
                <td>${stats.totalsByGroup[groupName].pending}</td>
                <td>${stats.totalsByGroup[groupName].approved + stats.totalsByGroup[groupName].pending}</td>
                <td>${stats.totalsByGroup[groupName].participated ?: 0}</td>
            </tr>
        </g:each>
    </table>

    <p>Existing invitation groups: ${groups.join(', ').encodeAsHTML()}</p>

    <h2>Actions</h2><hr>
    
    <g:link action="list">View/edit invitations</g:link> |
    <g:link action="batchInvite">Send batch of invitations</g:link> |
    <g:link action="batchApprove">Approve a batch of invitations</g:link><br/>
    <br>
    Download email addresses by group:
    <table class="table table-bordered table-hover table-striped">
    <g:each in="${groups}" var="g">
        <tr><td>${g.encodeAsHTML()}</td>
            <td><g:link action="export" params="[group:g, axis:'pending']">Pending</g:link></td>
            <td><g:link action="export" params="[group:g, axis:'approved']">Approved</g:link></td>
            <td><g:link action="export" params="[group:g, axis:'all']">All</g:link></td>
        </tr>
    </g:each>
    </table>
    
</body>
</html>