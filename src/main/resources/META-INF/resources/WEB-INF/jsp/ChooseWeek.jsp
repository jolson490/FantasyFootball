<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Choose Week - ILM Fantasy Football</title>
<style>
.error 
{
  color: #ff0000;
  font-weight: bold;
}
</style>
</head>

<body>
  <%-- The form data is bound to the model specified by modelAttribute. --%>
  <form:form action="showWeek" method="post" modelAttribute="weekAttribute">
  
  Choose a week:
  <br>
  <%-- The purpose of 'path' is to bind to the 'week' variable within the model. 
       And 'items' specifies the list of objects to use for generating the 'option' tags (i.e. choices in the drop-down menu). --%>
  <form:select path="week" items="${weeksMap}"/>
  
  <form:errors path="week" cssClass="error"/>
  <br><br>
  <INPUT type="submit" value="Show Week">
  
  </form:form>
</body>

</html>
