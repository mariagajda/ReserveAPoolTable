<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Panel</title>

</head>
<body>
<div>
    <a href="/reservation/add">Add possible reservations</a>
    You have set possible reservations up to ${lastReservationdate}.
</div>
<div>
    <a href="/holiday-workdays/list">Holiday-workdays</a>
</div>
<div>
    <a href="/price/list">Prices</a>
</div>
<div>
    <a href="" >Reserve Service - Admin Panel</a>
</div>
</body>
</html>
