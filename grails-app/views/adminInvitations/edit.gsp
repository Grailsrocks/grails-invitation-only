
<%@ page import="com.grailsrocks.invitationonly.UserInvitation" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="${grailsApplication.config.invitation.only.admin.layout ?: 'main'}" />
        <g:set var="entityName" value="${message(code: 'userInvitation.label', default: 'UserInvitation')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${userInvitationInstance}">
            <div class="errors">
                <g:renderErrors bean="${userInvitationInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${userInvitationInstance?.id}" />
                <g:hiddenField name="version" value="${userInvitationInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="email"><g:message code="userInvitation.email.label" default="Email" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInvitationInstance, field: 'email', 'errors')}">
                                    <g:textField name="email" maxlength="80" value="${userInvitationInstance?.email}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="groupName"><g:message code="userInvitation.groupName.label" default="Group Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInvitationInstance, field: 'groupName', 'errors')}">
                                    <g:textField name="groupName" maxlength="40" value="${userInvitationInstance?.groupName}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="approved"><g:message code="userInvitation.approved.label" default="Approved" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInvitationInstance, field: 'approved', 'errors')}">
                                    <g:checkBox name="approved" value="${userInvitationInstance?.approved}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
