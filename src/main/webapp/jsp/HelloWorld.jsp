<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ILM Fantasy Football</title>
</head>

<body onload="getCurrentWeek();">
<FORM action="/ILMServices-FantasyFootball/Fantasy" method="post">
<script>
function getCurrentWeek(){
	document.getElementsByName('currentWeek')[0].value = ${currentWeek};
}
</script>
Current Week
<SELECT name="currentWeek">
<OPTION value=1>Week 1</OPTION>
<OPTION value=2>Week 2</OPTION>
<OPTION value=3>Week 3</OPTION>
<OPTION value=4>Week 4</OPTION>
<OPTION value=5>Week 5</OPTION>
<OPTION value=6>Week 6</OPTION>
</SELECT>

<INPUT type="submit" name="Submit Button" value="Add Week">

</FORM>
</body>

</html>
