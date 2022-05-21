<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Prices per hour</title>

</head>
<body>
<h3>Prices for table reservation:</h3>
<table>
    <thead>
    <tr>
        <th></th>
        <th>Monday - Thursday</th>
        <th>Friday</th>
        <th>Saturday</th>
        <th>Sunday and holidays<sup>*</sup></th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>before 18:00</td>
        <td><c:out value="${monToThursdayBefore18.pricePerHour}"/> PLN/hour <a href="<c:url value="/price/edit/${monToThursdayBefore18.id}"/>">Edit</a></td>
        <td><c:out value="${fridayBefore18.pricePerHour}"/> PLN/hour <a href="<c:url value="/price/edit/${fridayBefore18.id}"/>">Edit</a></td>
        <td><c:out value="${saturdayBefore18.pricePerHour}"/> PLN/hour <a href="<c:url value="/price/edit/${saturdayBefore18.id}"/>">Edit</a></td>
        <td><c:out value="${sundayHolidaysBefore18.pricePerHour}"/> PLN/hour <a href="<c:url value="/price/edit/${sundayHolidaysBefore18.id}"/>">Edit</a></td>
    </tr>
    <tr>
        <td>after 18:00</td>
        <td><c:out value="${monToThursdayAfter18.pricePerHour}"/> PLN/hour <a href="<c:url value="/price/edit/${monToThursdayAfter18.id}"/>">Edit</a></td>
        <td><c:out value="${fridayAfter18.pricePerHour}"/> PLN/hour <a href="<c:url value="/price/edit/${fridayAfter18.id}"/>">Edit</a></td>
        <td><c:out value="${saturdayAfter18.pricePerHour}"/> PLN/hour <a href="<c:url value="/price/edit/${saturdayAfter18.id}"/>">Edit</a></td>
        <td><c:out value="${sundayHolidaysAfter18.pricePerHour}"/> PLN/hour <a href="<c:url value="/price/edit/${sundayHolidaysAfter18.id}"/>">Edit</a></td>
    </tr>
    </tbody>
</table>
<p><sup>*</sup>holidays on Friday or Saturday = Friday's or Saturday's prices</p>

<p>For registered users: if you have more than 5 reservation we have for You 5% discount from regular prices!</p>
</body>
</html>