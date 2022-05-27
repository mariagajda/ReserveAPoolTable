<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Reserve a Service</title>
</head>

<body>
<%@include file="includes/appHeader.jsp" %>

<h1>Reserve a Service</h1>
<c:choose>
    <c:when test="${noReservations}">
        <p>We are sorry, but we don't have available hours and tables to reserve</p>
    </c:when>
<c:otherwise>
    <form method="post">
        <label>Pick date
            <input type="date" name="dateStr" min="${minDate}" max="${maxDate}"/>
        </label>
        <input type=submit value="Check availability"/>
    </form>
</c:otherwise>
</c:choose>

<sec:authorize access="hasRole('ADMIN')">
    <div><a href="/admin/management-panel">Admin Management Panel</a></div>
</sec:authorize>

<h3>Prices for table reservation:</h3>
<table>
    <thead>
    <tr>
        <th></th>
        <th>Monday to Thursday</th>
        <th>Friday</th>
        <th>Saturday</th>
        <th>Sunday and holidays<sup>*</sup></th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>before 18:00</td>
        <td><c:out value="${monToThursdayBefore18.pricePerHour}"/> PLN</td>
        <td><c:out value="${fridayBefore18.pricePerHour}"/> PLN</td>
        <td><c:out value="${saturdayBefore18.pricePerHour}"/> PLN</td>
        <td><c:out value="${sundayHolidaysBefore18.pricePerHour}"/> PLN</td>
    </tr>
    <tr>
        <td>after 18:00</td>
        <td><c:out value="${monToThursdayAfter18.pricePerHour}"/> PLN</td>
        <td><c:out value="${fridayAfter18.pricePerHour}"/> PLN</td>
        <td><c:out value="${saturdayAfter18.pricePerHour}"/> PLN</td>
        <td><c:out value="${sundayHolidaysAfter18.pricePerHour}"/> PLN</td>
    </tr>
    </tbody>
</table>
<p><sup>*</sup>holidays on Friday or Saturday = Friday's or Saturday's prices</p>

<p>For registered users: if you have more than 5 reservation we have for You 5% discount from regular prices!</p>
</body>
</html>