<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create an NFL Player - ILM Fantasy Football</title>
</head>
<body>
  <div class="row">
  <h1>Add a player</h1>
    <%-- The form data is bound to the model specified by modelAttribute. --%>
    <form:form action="createNFLPlayer" method="post" modelAttribute="playerAttribute">
      <%-- TODO: test invalid input (& corresponding error messages) - also applies to EditNFLPlayer. --%>
      <div>
        <label>First Name:</label>
        <input type="text" name="firstName"/>
        <form:errors path="firstName" cssClass="error"/>
      </div>
      <div>
        <label>Last Name:</label>
        <input type="text" name="lastName"/>
        <form:errors path="lastName" cssClass="error"/>
      </div>
      <div>
        <label>Position:</label>
        
        <%-- The purpose of 'path' is to bind to the 'position' variable within the model. 
             And 'items' specifies the list of objects to use for generating the 'option' tags (i.e. choices in the drop-down menu). --%>
        <form:select path="position" items="${positionsList}"/>
        
        <form:errors path="position" cssClass="error"/>
      </div>
      <div>
        <label>NFL Ranking:</label>
        <input type="text" name="nflRanking"/>
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
        <button type="submit">Create</button>
      </div>    
    </form:form>
  </div>
</body>
</html>
