<html>
<head>
    <meta name="layout" content="${grailsApplication.config.invitation.only.admin.layout ?: 'main'}" />
    <title>Invitations - Approve user</title>
</head>
<body>
    <div class="page-header"><h1>Approve User</h1></div>

    <g:if test="${flash.message}">
        <p class="info">
            ${flash.message.encodeAsHTML()}
        </p>
    </g:if>
    
    <p>You have chosen to approve user "${params.email}" for group "${params.group}".
        You can send them an email to let them know what to do to take part:</p>
    
    <g:form controller="adminInvitations">
        <input type="hidden" name="email" value="${params.email.encodeAsHTML()}"/>
        <input type="hidden" name="group" value="${params.group.encodeAsHTML()}"/>
        Mail To: ${params.email.encodeAsHTML()}<br/><br>
        <f:with bean="${new com.grailsrocks.invitationonly.SingleApproveForm()}">
          <f:field property="senderAddress" default="${params.email.encodeAsHTML()}" />
          <f:field property="subject"/>
          <f:field property="message">
              <g:textArea name="${property }" cols="70" rows="10">${value }</g:textArea>
          </f:field>
        </f:with>
        <div class="form-actions">
            <g:actionSubmit action="performApproveWithMail" class="btn btn-primary" value="Approve and send Email"/>
            <g:actionSubmit action="performApproveNoMail" class="btn" value="Approve without Email"/>
        </div>
    </g:form>
        
</body>