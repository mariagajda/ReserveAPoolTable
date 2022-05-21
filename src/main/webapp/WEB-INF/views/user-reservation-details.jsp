<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Reservation details</title>
    <style>
        .discount {
            color: red;
            text-decoration: line-through;
        }
    </style>
</head>
<body>
<h1>Reservation details</h1>
<div>
    <h3>Your reservation details:</h3>
    <p>Date: <c:out value="${reservationsToConfirm.get(0).getDate()}"/></p>
    <c:forEach items="${reservationsToConfirm}" var="reservation">
        <p>Table no. ${reservation.table.tableNumber}: ${reservation.startDateTime.getHour()}:${reservation.startDateTime.getMinute()}
            - ${reservation.endDateTime.getHour()}:${reservation.endDateTime.getMinute()}</p>
    </c:forEach>
    <p>Discount: <fmt:formatNumber value="${user.discount}" type="percent"/></p>
<%--    <c:set var="sum" scope="page" value="0.00"/>--%>
    <p>
    <c:choose>
    <c:when test="${user.discount > 0}">
        Price: <span class="discount"><fmt:formatNumber value="${priceSum}" type="number" maxFractionDigits="2" minFractionDigits="2"/></span> /<fmt:formatNumber value="${priceSum*(1-user.discount)}" type="number" maxFractionDigits="2" minFractionDigits="2"/> PLN
    </c:when>
        <c:otherwise>
            Price: <fmt:formatNumber value="${priceSum}" type="number" maxFractionDigits="2" minFractionDigits="2"/> PLN
        </c:otherwise>
        </c:choose>
    </p>
<%--        <c:forEach items="${reservationsToConfirm}" var="reservation">--%>
<%--            <c:set var="sum" scope="page" value="${sum + reservation.pricePerReservation}"/>--%>
<%--        </c:forEach>--%>
<%--        <span class="discount"><fmt:formatNumber value="${sum}" type="number" maxFractionDigits="2" minFractionDigits="2"/></span>/<fmt:formatNumber value="${sum*(1-user.discount)}" type="number" maxFractionDigits="2" minFractionDigits="2"/> PLN--%>
</div>
<form action="/reservation/details", method="post">
    <label>Payment method:
        <select name="paymentMethod">
            <option value="">Select...</option>
            <option value="transfer">Online payment</option>
            <option value="inPlace">Pay in place</option>
        </select>
    </label><br/>
    <input type="submit" value="Make a reservation"/>
</form>
</body>
</html>