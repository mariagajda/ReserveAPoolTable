<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Admin Panel</title>

</head>
<body>
<div>
    <p>You have set possible reservations up to ${lastReservationDateInDatabase} ${lastReservationDateWarning}.</p>
    <h3>If you want to add possible reservations fill the form:</h3>
    <form action="/admin/reservations/add" method="post">
        <label>Reservations for period from <input type="date" name="firstDay" value="${firstReservationDate}"> to:
            <input type="date"
                   name="lastDay"/></label>
        <label>Minimal duration of reservation: <input type="number" name="duration" value="30"/> min </label>
        <h4>Openings hours:</h4>
        <p>Monday - Thursday <label>From: <input type="time" name="monToThuTimeFrom" value="15:00"></label><label> To: <input
                type="time" name="monToThuTimeTo" value="01:00"></label></p>
        <p>Friday <label>From: <input type="time" name="friTimeFrom" value="15:00"></label><label> To: <input
                type="time" name="friTimeTo" value="02:00"></label></p>
        <p>Saturday <label>From: <input type="time" name="satTimeFrom" value="15:00"></label><label> To: <input
                type="time" name="satTimeTo" value="02:00"></label></p>
        <p>Sunday & holidays <label>From: <input type="time" name="sunTimeFrom" value="15:00"></label><label> To: <input
                type="time" name="sunTimeTo" value="00:00"></label></p>
        <input type="submit" value="Add reservations">
    </form>
</div>
<div>
    <a href="/holiday-workdays/list">Holiday-workdays</a>
</div>
<div>
    <a href="/price/list">Prices</a>
</div>
<div>
    <a href="">Reserve Service - Admin Panel</a>
</div>
</body>
</html>

