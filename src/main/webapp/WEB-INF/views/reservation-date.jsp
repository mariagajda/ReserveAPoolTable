<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Reserve a Service</title>

</head>
<body>
<h1>Reserve a Service</h1>
<form method="post">
    <label>Pick date
        <input type="date" name="dateStr"/>
    </label>
    <input type=submit value="Check availability"/>
</form>

<h3>Prices for table reservation:</h3>
<table>
    <thead>
    <tr>
        <th></th>
        <th>${monToThursdayBefore18.dayOfWeek}</th>
        <th>${fridayBefore18.dayOfWeek}</th>
        <th>${saturdayBefore18.dayOfWeek}</th>
        <th>${sundayHolidaysBefore18.dayOfWeek}<sup>*</sup></th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>before ${monToThursdayBefore18.endTime}</td>
        <td><c:out value="${monToThursdayBefore18.pricePerHour}"/> <a href=""</td>
        <td><c:out value="${fridayBefore18.pricePerHour}"/></td>
        <td><c:out value="${saturdayBefore18.pricePerHour}"/></td>
        <td><c:out value="${sundayHolidaysBefore18.pricePerHour}"/></td>
    </tr>
    <tr>
        <td>after ${monToThursdayAfter18.startTime}</td>
        <td><c:out value="${monToThursdayAfter18.pricePerHour}"/> </td>
        <td><c:out value="${fridayAfter18.pricePerHour}"/></td>
        <td><c:out value="${saturdayAfter18.pricePerHour}"/></td>
        <td><c:out value="${sundayHolidaysAfter18.pricePerHour}"/></td>
    </tr>
    </tbody>
</table>
<p><sup>*</sup>holidays on Friday or Saturday = Friday's or Saturday's prices</p>

<p>For registered users: if you have more than 5 reservation we have for You 5% discount from regular prices!</p>
</body>
</html>