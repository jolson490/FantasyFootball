<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit an NFL Player - ILM Fantasy Football</title>
<link href="<c:url value="/css/main.css" />" rel="stylesheet" type="text/css">
</head>

<body>
  <div class="row">
  <h1>Edit a player</h1>
    <%-- The form data is bound to the model specified by modelAttribute. --%>
    <form:form action="saveEditedNFLPlayer" method="post" modelAttribute="playerToEdit">
      <div>
        <label>First Name:</label>
        <input type="text" name="firstName" value="${playerToEdit.firstName}" readonly class="readonly">
        <form:errors path="firstName" cssClass="error"/>
      </div>
      <div>
        <label>Last Name:</label>
        <input type="text" name="lastName" value="${playerToEdit.lastName}" readonly class="readonly">
        <form:errors path="lastName" cssClass="error"/>
      </div>
      <div>
        <label>Position:</label>
        <input type="text" name="position" value="${playerToEdit.position}" readonly class="readonly">
        <form:errors path="position" cssClass="error"/>
      </div>
      
      <div>
        <label>NFL Ranking:</label>
        <input type="number" name="nflRanking" value="${playerToEdit.nflRanking}" min="0"/> <%-- Set the default/initial value to the existing value. --%>
        <form:errors path="nflRanking" cssClass="error"/>
      </div>
      <div>
        <label>NFL Team:</label>
        <form:select path="nflTeam">
          <form:options items="${nflTeamsList}" itemValue="locationAbbreviation" itemLabel="locationAbbreviation"/>
        </form:select>
        <form:errors path="nflTeam" cssClass="error"/>
      </div>
      <div>
        <button type="submit">Save</button>
      </div>    
    </form:form>
  </div>
</body>
</html>
