<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
<title>Reserve a Service</title>
</head>
<body>
<%@include file="includes/appHeader.jsp" %>

<h2>Welcome in our Pool Table Reservation System! </h2>
<div>
    <p>Are you our registered user? Please <a href="<c:url value="/login"/>">Sign in!</a>.</p>
    <p>Are a regular player? Do you want to register?  <a href="/user/register">Click here!</a></p>
    <p>Reservation for unregistered user? No problem! <a href="/reservation/date">Click here!</a></p>
</div>
</body>
</html>