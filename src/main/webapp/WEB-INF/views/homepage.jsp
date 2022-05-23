<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>Reserve a Service</title>
</head>
<body>
<h2>Welcome in our Reservation System! </h2>
<div>
    <p>Are you our registered user? Please <a href="<c:url value="/login"/>">Sign in!</a>.</p>
    <p>Are a regular player? Do you want to register?  <a href="<c:url value="/user/register"/>">Click here!</a></p>
    <p>Reservation for unregistered user? No problem! <a href="<c:url value="/reservation/date"/>">Click here!</a></p>
</div>
</body>
</html>