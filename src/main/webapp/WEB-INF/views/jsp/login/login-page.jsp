<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
</head>

<body>

 <c:url value="/login" var="loginProcessingUrl"/>
 <sf:form action="${loginProcessingUrl}" method="post" modelAttribute="login">
    <fieldset>
        <legend>Please Login</legend>
        <c:if test="${param.error != null}">
            <div>
                Failed to login.
                <c:if test="${SPRING_SECURITY_LAST_EXCEPTION != null}">
                  Reason: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
                </c:if>
            </div>
        </c:if>
        <c:if test="${param.logout != null}">
            <div>
                You have been logged out.
            </div>
        </c:if>
        <p>
            <label for="username">Username</label>
            <sf:input path="username" id="username" />
        </p>
        <p>
            <label for="password">Password</label>
            <sf:password path="password" id="password" />
        </p>
        <p>
            <label for="rm">Remember Me:</label>
            <input type="checkbox" name="rm" id="rm" />
        </p>
        <div>
            <button type="submit" class="btn">Log in</button>
        </div>
        
    </fieldset>
 </sf:form>

</body>

</html>

