<html>
<head>
    <meta name="layout" content="${grailsApplication.config.invitation.only.admin.layout ?: 'main'}" />
    <title>Invitations - Batch approve</title>
</head>
<body>
    <div class="page-header">
        <h1>Approve a batch of users</h1>
    </div>

    <g:if test="${flash.message}">
        <p class="info">
            ${flash.message.encodeAsHTML()}
        </p>
    </g:if>
    
    <p>You have chosen to bulk-approve users.
       Remember to choose the correct invitation group!
       An email can be sent to them to let them know what to do to take part:
    </p>

    <g:form controller="adminInvitations">
        <f:with bean="${new com.grailsrocks.invitationonly.BatchApproveForm()}">
          <f:field property="numberToApprove"/>
          <f:field property="group" value="${groups }">
            <g:select name="${property }" from="${value}"/>
          </f:field>
          <f:field property="senderAddress"/>
          <f:field property="subject"/>
          <f:field property="message">
              <g:textArea name="${property }" cols="70" rows="10">${value }</g:textArea>
          </f:field>
        </f:with>
        <div class="form-actions">
            <g:actionSubmit action="performBatchApproveWithMail" class="btn btn-primary" value="Approve and send Email"/>
            <g:actionSubmit action="performBatchApproveNoMail" class="btn" value="Approve without Email"/>
        </div>
    </g:form>
        
</body>