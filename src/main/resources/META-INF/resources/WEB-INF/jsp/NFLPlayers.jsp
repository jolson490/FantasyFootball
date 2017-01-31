<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>NFL Players - ILM Fantasy Football</title>
</head>
<body>
  <a href="${pageContext.request.contextPath}/newNFLPlayer">Add a player</a><br>
  
  <div class="row">
  <h1>Players</h1>
  <table border="1">
    <%-- Print a table heading (the name of some of the fields within the model). --%>
    <tr>
      <th>Ranking</th>
      <th>First Name</th>
      <th>Last Name</th>
      <th>Position</th>
      <th>Team</th>
      <th></th> <%-- (Edit) --%>
      <th></th> <%-- (Delete) --%>
    </tr>

    <c:forEach var="currentPlayer" items="${playersAttribute}" varStatus="loop">
      <tr>
        <%-- For the current player, print the value of some of its fields. --%>
        <td>${currentPlayer.nflRanking}</td>
        <td>${currentPlayer.firstName}</td>
        <td>${currentPlayer.lastName}</td>
        <td>${currentPlayer.position}</td>
        <td>${currentPlayer.nflTeam}</td>
        
        <td><a href="${pageContext.request.contextPath}/editNFLPlayer/${currentPlayer.playerPK}/">Edit</a></td>
        <td><a href="${pageContext.request.contextPath}/deleteNFLPlayer/${currentPlayer.playerPK}/">Delete</a></td>
      </tr>
    </c:forEach>
  </table>
  </div>
  <br>

  <a href="${pageContext.request.contextPath}/">Main page</a>
</body>
</html>
