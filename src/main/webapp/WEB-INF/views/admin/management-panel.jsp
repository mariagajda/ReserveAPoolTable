<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<sec:authorize access="hasRole('ADMIN')">
    <html>
    <head>
        <title>Admin Panel</title>
        <link rel="stylesheet" href="<c:url value="/resources/css/appCss.css"/>"/>
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
        <p>You have set available reservations up to ${lastReservationDateInDatabase} ${lastReservationDateWarning}.</p>
        <p>If you want to add new available reservations fill the form below. </p>
        <p>Remember to add holiday workdays for new period before adding new available reservations to set right prices
            group for this days. If you want to change prices also do it before adding reservations. It will be
            impossible to change it afterwards.</p>
        <div>
            <h4>Reservations terms for new period:</h4>
                <%--@elvariable id="reservationParameters" type="pl.coderslab.reserveapooltable.entity.ReservationParameters"--%>
            <form:form action="/admin/management-panel" modelAttribute="reservationParameters" method="post">
                <label>Reservations for period from
                    <form:input type="date" path="firstDay" value="${firstReservationDate}" min="${minFrom}"/>


                    <form:errors path="firstDay"  cssClass="error"/></label>
                <label>to:
                    <form:input type="date" path="lastDay" value="${firstReservationDate}" min="${minFrom}"/>

                    <form:errors path="lastDay" cssClass="error"/></label><br/>
                <label>Minimal duration of reservation:
                    <form:input type="number" path="duration" value="30"/>
                    <form:errors path="duration" cssClass="error"/>
                    min
                </label>
                <h4>Openings hours for new period:</h4>
                <p>Monday - Thursday
                    <label>From:
                        <form:input type="time" path="monToThuTimeFrom" value="15:00"/>
                        <form:errors path="monToThuTimeFrom" cssClass="error"/>
                    </label>
                    <label> To:
                        <form:input type="time" path="monToThuTimeTo" value="01:00"/>
                        <form:errors path="monToThuTimeTo" cssClass="error"/>
                    </label></p>
                <p>Friday <label>From:
                    <form:input type="time" path="friTimeFrom" value="15:00"/>
                    <form:errors path="friTimeFrom"  cssClass="error"/>
                </label>
                    <label> To:
                        <form:input type="time" path="friTimeTo" value="02:00"/>
                        <form:errors path="friTimeTo" cssClass="error"/>
                    </label></p>
                <p>Saturday <label>From:
                    <form:input type="time" path="satTimeFrom" value="15:00"/>
                    <form:errors path="satTimeFrom" cssClass="error"/>
                </label>
                    <label> To:
                        <form:input type="time" path="satTimeTo" value="02:00"/>
                        <form:errors path="satTimeTo" cssClass="error"/>
                    </label></p>
                <p>Sunday & holidays <label>From:
                    <form:input type="time" path="sunTimeFrom" value="15:00"/>
                    <form:errors path="sunTimeFrom" cssClass="error"/>
                </label>
                    <label>To:
                        <form:input type="time" path="sunTimeTo" value="00:00"/>
                        <form:errors path="sunTimeTo" cssClass="error"/>
                    </label></p>
                <input type="submit" value="Add reservations">
            </form:form>
        </div>
    </div>

    <div>
        <a href="/reservation/date">Reserve Service as Admin</a>
    </div>
    </body>
    </html>
</sec:authorize>
