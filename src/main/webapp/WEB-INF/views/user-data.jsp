<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Reserve a Service</title>

</head>
<body>
<h1>Reserve a Service</h1>
<div>
    <h3>Your reservation details:</h3>
    <p>Date: <c:out value="${date}"/></p>
    <c:forEach items="${reservationsToConfirm}" var="reservation">
        <p>Table no. ${reservation.table.tableNumber}: ${reservation.startTime} - ${reservation.endTime}</p>
    </c:forEach>
</div>

</body>
</html>