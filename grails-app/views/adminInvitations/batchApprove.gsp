<html>
<head>
    <meta name="layout" content="${grailsApplication.config.invitation.only.admin.layout ?: 'main'}" />
    <title>Invitations - Batch approve</title>
</head>
<body>
    <h1>Approve a batch of users</h1>

    <g:if test="${flash.message}">
        <p class="info">
            ${flash.message.encodeAsHTML()}
        </p>
    </g:if>
    
    <p>You have chosen to bulk-approve users.
       Remember to choose the correct invitation group!
       An email can be sent to them to let them know what to do to take part:
    </p>
    
    <bean:require beanName="form" className="com.grailsrocks.invitationonly.BatchApproveForm"/>
    <g:form controller="adminInvitations">
        <bean:withBean beanName="form">
          <bean:field property="numberToApprove"/>
          <bean:select property="group" from="${groups}"/>
          <bean:field property="senderAddress"/>
          <bean:field property="subject"/>
          <bean:textArea property="message" cols="70" rows="10"/>
        </bean:withBean>
        <g:actionSubmit action="performBatchApproveWithMail" value="Approve and send Email"/>
        <g:actionSubmit action="performBatchApproveNoMail" value="Approve without Email"/>
    </g:form>
        
</body>