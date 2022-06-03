<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Logout</title>
</head>
<body>
<%@include file="includes/appHeader.jsp" %>

<form action="/logout" method="post">
    <input type="submit" value="logout">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
</body>
</html>
