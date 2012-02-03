
<%@ page import="com.grailsrocks.invitationonly.UserInvitation" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="${grailsApplication.config.invitation.only.admin.layout ?: 'main'}" />
        <g:set var="entityName" value="${message(code: 'userInvitation.label', default: 'UserInvitation')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="userInvitation.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: userInvitationInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="userInvitation.email.label" default="Email" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: userInvitationInstance, field: "email")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="userInvitation.groupName.label" default="Group Name" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: userInvitationInstance, field: "groupName")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="userInvitation.approved.label" default="Approved" /></td>
                            
                            <td valign="top" class="value"><g:formatBoolean boolean="${userInvitationInstance?.approved}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="userInvitation.dateCreated.label" default="Date Created" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${userInvitationInstance?.dateCreated}" /></td>
                            
                        </tr>
                        
                        
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="userInvitation.dateApproved.label" default="Date Approved" /></td>

                            <td valign="top" class="value"><g:formatDate date="${userInvitationInstance?.dateApproved}" /></td>

                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="userInvitation.dateFirstParticipated.label" default="Date First Participated" /></td>

                            <td valign="top" class="value"><g:formatDate date="${userInvitationInstance?.dateFirstParticipated}" /></td>

                        </tr>
                        
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${userInvitationInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
