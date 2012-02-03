<html>
<head>
    <meta name="layout" content="${grailsApplication.config.invitation.only.admin.layout ?: 'main'}" />
    <title>Invitations - Batch invites</title>
</head>
<body>

<h1>Batch invites</h1>
<p>
    Use this form to add users to the invitation list and send them an email at the same time.
    Note that your email message should be clear about whether or not they are currently approved. 
    If you check "approved" then they will be able to access the service immediately. If you don't check this
    they will just be added to the "pending" invitations list.
</p>
<bean:require beanName="form" className="com.grailsrocks.invitationonly.BatchInviteForm"/>
<g:form controller="adminInvitations" action="performBatchInvite">
    <bean:withBean beanName="form">
      <bean:field property="senderAddress"/>
      <bean:field property="groupName"/>
      <bean:checkBox property="approved"/>
      <bean:field property="subject"/>
      <bean:field property="addresses"/>
      <bean:textArea property="message" cols="70" rows="10"/>
    </bean:withBean>
    <g:actionSubmit action="performBatchInvite" value="Send invitations"/>
</g:form>

</body>
</html>