<html>
<head>
    <meta name="layout" content="${grailsApplication.config.invitation.only.admin.layout ?: 'main'}" />
    <title>Invitations - Batch invites</title>
</head>
<body>

<div class="page-header"><h1>Batch invites</h1></div>
<p>
    Use this form to add users to the invitation list and send them an email at the same time.
    Note that your email message should be clear about whether or not they are currently approved. 
    If you check "approved" then they will be able to access the service immediately. If you don't check this
    they will just be added to the "pending" invitations list.
</p>

<g:form controller="adminInvitations" action="performBatchInvite">
    <f:with bean="${new com.grailsrocks.invitationonly.BatchInviteForm() }">
      <f:field property="senderAddress"/>
      <f:field property="groupName"/>
      <f:field property="approved">
          <g:checkBox name="${property }" value="${value }"/>
      </f:field>
      <f:field property="subject"/>
      <f:field property="addresses"/>
      <f:field property="message">
          <g:textArea name="${property }" cols="70" rows="10">${value }</g:textArea>
      </f:field>
    </f:with>
    <div class="form-actions">
        <g:actionSubmit action="performBatchInvite" class="btn btn-primary" value="Send invitations"/>
    </div>
</g:form>

</body>
</html>