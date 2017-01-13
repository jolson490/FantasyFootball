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
    <c:forEach var="currentTeam" items="${teamsAttribute}" varStatus="loop">
      <%-- Using the first team in the list, print a table heading (the name of each field within the model). --%>
      <c:if test="${loop.first}">
          <tr>
              <c:forEach var="field" items="${currentTeam['class'].declaredFields}">
                  <th>${field.name}</th>
              </c:forEach>
          </tr>
      </c:if>
      <%-- For the current team, print the value of each of its fields. --%>
      <c:if test="${not empty currentTeam['class'].declaredFields}">
        <tr>
          <c:forEach var="field" items="${currentTeam['class'].declaredFields}">
            <c:catch var="catchException">
              <td>${currentTeam[field.name]}</td>
            </c:catch>
            <%-- There is always an exception with serialVersionUID, so don't bother to print any exception(s). --%>
            <%--
            <c:if test="${catchException != null}">
              <p>The exception is: ${catchException}</p><br>
            </c:if>
            --%>
          </c:forEach>
        </tr>
      </c:if>
    </c:forEach>
  </table>
  <br>

  <a href="${pageContext.request.contextPath}/">Main page</a>
</body>
</html>
