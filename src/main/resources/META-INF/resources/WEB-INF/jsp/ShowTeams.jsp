<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Show Fantasy Teams - ILM Fantasy Football</title>
</head>
<body>
  <table border="1">
    <%-- Print a table heading (the name of some of the fields within the model). --%>
    <tr>
      <th>Username</th>
      <th>Mascot</th>
    </tr>
    
    <c:forEach var="currentTeam" items="${teamsAttribute}" varStatus="loop">
      <%-- For the current team, print the value of some of its fields. --%>
      <tr>
        <td>${currentTeam.username}</td>
        <td>${currentTeam.mascot}</td>
      </tr>
    </c:forEach>
  </table>
  <br>

  <a href="${pageContext.request.contextPath}/">Main page</a>
</body>
</html>
