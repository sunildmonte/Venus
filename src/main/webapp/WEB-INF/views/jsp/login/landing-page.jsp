<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Temporary Landing Page</title>
</head>

<body>

This is a temporary landing page.

<div id="logout">
  <c:url value="/logout" var="logoutProcessingUrl"/>
  <sf:form action="${logoutProcessingUrl}" method="post">
    <button type="submit" class="btn">Logout</button>
  </sf:form>
</div>

</body>

</html>

