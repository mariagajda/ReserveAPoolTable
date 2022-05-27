<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<sec:authorize access="hasRole('ADMIN')">
    <html>
    <head>
        <title>Admin Panel</title>

    </head>
    <body>
    <%@include file="../includes/appHeader.jsp" %>
    <h3>
        <a href="/holiday-workdays/list">Holiday-workdays</a>
    </h3>
    <h3>
        <a href="/price/list">Prices</a>
    </h3>
    <h3>Reservations terms</h3>
    <div>
        <p>You have set possible reservations up to ${lastReservationDateInDatabase} ${lastReservationDateWarning}.</p>
        <p>If you want to add new possible reservations fill the form below. </p>
        <p>Remember to add holiday workdays for new period before adding new available reservations to set right prices group for
            this days. If you want to change prices also do it before adding reservations. It will be impossible to change it afterwards.</p>
        <div>
            <h4>Reservations terms for new period:</h4>
            <form action="/admin/management-panel" method="post">
                <label>Reservations for period from
                    <input type="date" name="firstDay" value="${firstReservationDate}" min="${minFrom}"> to:
                    <input type="date" name="lastDay" value="${firstReservationDate}" min="${minFrom}"/></label><br/>
                <label>Minimal duration of reservation: <input type="number" name="duration" value="30"/> min </label>
                <h4>Openings hours for new period:</h4>
                <p>Monday - Thursday <label>From: <input type="time" name="monToThuTimeFrom"
                                                         value="15:00"></label><label> To: <input
                        type="time" name="monToThuTimeTo" value="01:00"></label></p>
                <p>Friday <label>From: <input type="time" name="friTimeFrom" value="15:00"></label><label> To: <input
                        type="time" name="friTimeTo" value="02:00"></label></p>
                <p>Saturday <label>From: <input type="time" name="satTimeFrom" value="15:00"></label><label> To: <input
                        type="time" name="satTimeTo" value="02:00"></label></p>
                <p>Sunday & holidays <label>From: <input type="time" name="sunTimeFrom" value="15:00"></label><label>
                    To: <input
                        type="time" name="sunTimeTo" value="00:00"></label></p>
                <input type="submit" value="Add reservations">
            </form>
        </div>
    </div>

    <div>
        <a href="/reservation/date">Reserve Service as Admin</a>
    </div>
    </body>
    </html>
</sec:authorize>
