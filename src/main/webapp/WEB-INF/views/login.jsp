<html>
<head>

</head>
<body>
<h1>Login</h1>
<form action="/user/login" method="post">
    <div><label> User Name: <input type="text" name="username" value=""/> </label></div>
    <div><label> Password: <input type="password" name="password"/> </label></div>
    <div><input type="submit" value="Sign In"/></div>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
</body>
</html>
