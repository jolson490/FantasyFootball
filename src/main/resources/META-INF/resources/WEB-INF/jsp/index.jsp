<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ILM Fantasy Football</title>
</head>
<body>
  <h2>ILM's Fantasy Football</h2><br>
  <a href="${pageContext.request.contextPath}/showTeams">Show Fantasy Teams page</a><br>
  <a href="${pageContext.request.contextPath}/nflPlayers">NFL Players page</a><br>
  <a href="${pageContext.request.contextPath}/chooseWeek">Choose Week page</a><br><br>
  
  The version of this application that is deployed was last updated at: ${lastUpdated} <br><br>
  
  <i>DISCLAIMER: This is a POC (Proof of Concept) application - for more info, see the <a target="_blank" href="https://github.com/ILMServices/FantasyFootball/blob/master/README.md">GitHub repository</a>.</i>
</body>
</html>
