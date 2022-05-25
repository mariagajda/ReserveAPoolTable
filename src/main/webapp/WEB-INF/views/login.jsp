<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
<title>Log in</title>
</head>
<body>
<%@include file="includes/appHeader.jsp" %>

<h1>Login</h1>
<form action="/login" method="post">
    <div><label> User Name: <input type="text" name="username" value=""/> </label></div>
    <div><label> Password: <input type="password" name="password"/> </label></div>
    <div><input type="submit" value="Sign In"/></div>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
</body>
</html>
