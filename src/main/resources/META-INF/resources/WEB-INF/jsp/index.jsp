<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ILM Fantasy Football</title>
<link href="<c:url value="/css/main.css" />" rel="stylesheet">
</head>
<body>
  <span class="strongFont">ILM Fantasy Football</span>
  <i> (running in a Docker container, in AWS Elastic Beanstalk - with CI/CD via GitHub, Jenkins, Maven, and AWS CodePipeline)</i>
  <br><br>

  <a href="${pageContext.request.contextPath}/showTeams">Show Fantasy Teams page</a><br>
  <a href="${pageContext.request.contextPath}/nflPlayers">NFL Players page</a><br>
  <a href="${pageContext.request.contextPath}/chooseWeek">Choose Week page</a><br><br>
  
  The version of this application that is deployed was last updated at: ${lastUpdated} <br><br>
  
  <i>DISCLAIMER: This is a POC (Proof of Concept) application - for more info, see the <a target="_blank" href="https://github.com/ILMServices/FantasyFootball/blob/master/README.md">GitHub repository</a>.</i>
</body>
</html>
