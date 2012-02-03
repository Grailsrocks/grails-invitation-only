
<%@ page import="com.grailsrocks.invitationonly.UserInvitation" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="${grailsApplication.config.invitation.only.admin.layout ?: 'main'}" />
        <g:set var="entityName" value="${message(code: 'userInvitation.label', default: 'UserInvitation')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'userInvitation.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="email" title="${message(code: 'userInvitation.email.label', default: 'Email')}" />
                        
                            <g:sortableColumn property="groupName" title="${message(code: 'userInvitation.groupName.label', default: 'Group Name')}" />
                        
                            <g:sortableColumn property="approved" title="${message(code: 'userInvitation.approved.label', default: 'Approved')}" />
                        
                            <g:sortableColumn property="dateCreated" title="${message(code: 'userInvitation.dateCreated.label', default: 'Date Created')}" />

                            <g:sortableColumn property="dateApproved" title="${message(code: 'userInvitation.dateApproved.label', default: 'Date Approved')}" />

                            <g:sortableColumn property="dateFirstParticipated" title="${message(code: 'userInvitation.dateFirstParticipated.label', default: 'Participated')}" />
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${userInvitationInstanceList}" status="i" var="userInvitationInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${userInvitationInstance.id}">${fieldValue(bean: userInvitationInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: userInvitationInstance, field: "email")}</td>
                        
                            <td>${fieldValue(bean: userInvitationInstance, field: "groupName")}</td>
                        
                            <td><g:formatBoolean boolean="${userInvitationInstance.approved}" /></td>
                        
                            <td><g:formatDate date="${userInvitationInstance.dateCreated}" /></td>
                        
                            <td><g:formatDate date="${userInvitationInstance.dateApproved}" /></td>
                        
                            <td><g:formatDate date="${userInvitationInstance.dateFirstParticipated}" /></td>

                            <td><g:if test="${!userInvitationInstance.approved}">
                                <g:link action="approve" params="[email:userInvitationInstance.email, group:userInvitationInstance.groupName]">Approve</g:link>
                                </g:if>
                            </td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${userInvitationInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
