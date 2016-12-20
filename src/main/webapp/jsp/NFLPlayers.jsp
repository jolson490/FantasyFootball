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
    <c:forEach var="currentPlayer" items="${playersAttribute}" varStatus="loop">
      <%-- Using the first player in the list, print a table heading (the name of each field within the model). --%>
      <c:if test="${loop.first}">
          <tr>
              <c:forEach var="field" items="${currentPlayer['class'].declaredFields}">
                  <th>${field.name}</th>
              </c:forEach>
              <th></th> <%-- (Edit) --%>
              <th></th> <%-- (Delete) --%>
          </tr>
      </c:if>
      
      <%-- For the current player, print the value of each of its fields. --%>
      <c:if test="${not empty currentPlayer['class'].declaredFields}">
        <tr>
          <c:forEach var="field" items="${currentPlayer['class'].declaredFields}">
            <td>${currentPlayer[field.name]}</td>            
          </c:forEach>
          <td><a href="${pageContext.request.contextPath}/editNFLPlayer/${currentPlayer.playerPK}/">Edit</a></td>
          <td><a href="${pageContext.request.contextPath}/deleteNFLPlayer/${currentPlayer.playerPK}/">Delete</a></td>
        </tr>
      </c:if>
    </c:forEach>
  </table>
  </div>
  <br>

  <a href="${pageContext.request.contextPath}/">Main page</a>
</body>
</html>
