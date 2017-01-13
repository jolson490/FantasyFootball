<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Error - ILM Fantasy Football</title>
<link href="<c:url value="/resources/css/main.css" />" rel="stylesheet">
</head>

<body>
  <h1 class="error">${errorMsg}</h1>
  <br>
  <a href="${pageContext.request.contextPath}/">Main page</a>
</body>
</html>
