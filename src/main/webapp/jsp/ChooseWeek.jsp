<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Choose Week - ILM Fantasy Football</title>
</head>

<body>
<form:form action="showWeek" method="post" modelAttribute="weekModel">

Choose a week<%-- TODO-data-weeklyTeams uncomment text: to show the players/teams for--%>:
<br>
<form:select path="week" items="${weeksMap}"/>
<br><br>
<INPUT type="submit" value="Show Week">

</form:form>
</body>

</html>