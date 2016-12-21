<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Error - ILM Fantasy Football</title>
<style>
  myError {color: red;}
</style>
</head>

<body>
  <h1><myError>${errorMsg}</myError></h1>
  <br>
  <a href="${pageContext.request.contextPath}/">Main page</a>
</body>
</html>
