<html>
<head>
    <meta name="layout" content="${grailsApplication.config.invitation.only.admin.layout ?: 'main'}" />
    <title>Invitations - Approve user</title>
</head>
<body>
    <h1>Approve user</h1>

    <g:if test="${flash.message}">
        <p class="info">
            ${flash.message.encodeAsHTML()}
        </p>
    </g:if>
    
    <p>You have chosen to approve user "${params.email}" for group "${params.group}". You can send them an email to let them know what to do to take part:</p>
    
    <bean:require beanName="form" className="com.grailsrocks.invitationonly.SingleApproveForm"/>
    <g:form controller="adminInvitations">
        <input type="hidden" name="email" value="${params.email.encodeAsHTML()}"/>
        <input type="hidden" name="group" value="${params.group.encodeAsHTML()}"/>
        Mail To: ${params.email.encodeAsHTML()}<br/>
        <bean:withBean beanName="form">
          <bean:field property="senderAddress"/>
          <bean:field property="subject"/>
          <bean:textArea property="message" cols="70" rows="10"/>
        </bean:withBean>
        <g:actionSubmit action="performApproveWithMail" value="Approve and send Email"/>
        <g:actionSubmit action="performApproveNoMail" value="Approve without Email"/>
    </g:form>
        
</body>