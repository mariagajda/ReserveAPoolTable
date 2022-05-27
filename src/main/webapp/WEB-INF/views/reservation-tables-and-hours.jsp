<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pl">
<head>
    <title>Reserve a Service</title>
</head>
<body>
<%@include file="includes/appHeader.jsp" %>

<h1>Reserve a Service</h1>
<form action="/reservation/date" method="post">
    <label>Pick date
        <input type="date" name="dateStr" value="${date}"/>
    </label>
    <input type=submit value="Check availability"/>
</form>
<%--<img src="images/img.jpg" alt="tables arrangement">--%>

<form action="/reservation/datetablesandhours" method="post">
    <h3>Available hours & pool tables:</h3>
    <table>
        <tbody>
        <c:forEach items="${tables}" var="table">
        <c:set var="tableNumber" value="${table.tableNumber}"></c:set>
        <tr>
            <th>
                Table No.${table.tableNumber}:
            </th>

            <c:forEach items="${reservations}" var="reservation">
            <c:set var="tableNumberFromReservation" value="${reservation.table.tableNumber}"></c:set>
            <c:if test="${tableNumber eq tableNumberFromReservation}">
            <c:choose>
            <c:when test="${reservation.isAvailable()}">
            <td style="background-color: lightgreen"><input type="checkbox" name="pickedReservations"
                                                            value="${reservation.id}"
                                                            id="checkbox"/>${reservation.startDateTime.getHour()}:<fmt:formatNumber type = "number" minIntegerDigits = "2" value = "${reservation.startDateTime.getMinute()}"/>
                - ${reservation.endDateTime.getHour()}:<fmt:formatNumber type = "number" minIntegerDigits = "2" value = "${reservation.endDateTime.getMinute()}"/>
                </c:when>
                <c:otherwise>
            <td style="background-color: indianred">${reservation.startDateTime.getHour()}:<fmt:formatNumber type = "number" minIntegerDigits = "2" value = "${reservation.startDateTime.getMinute()}"/>
                - ${reservation.endDateTime.getHour()}:<fmt:formatNumber type = "number" minIntegerDigits = "2" value = "${reservation.endDateTime.getMinute()}"/>
                </c:otherwise>
                </c:choose>
            </td>
            </c:if>

            </c:forEach>

</form>
</tr>

</c:forEach>
</tbody>
</table>
<p><br/></p>
<table>
    <tbody>
    <tr>
        <td style="background-color: lightgreen">hh:mm<br/>-<br/>hh:mm</td>
        <td> - termin dostępny</td>
    </tr>
    <tr>
        <td style="background-color: indianred">hh:mm<br/>-<br/>hh:mm<br/></td>
        <td> - termin niedostępny</td>
    </tr>
    </tbody>
</table>
<input type="submit" value="Confirm reservation"/>

</form>
</body>
<script type="text/javascript" src="<c:url value="/src/main/resources/static/js/app.js"/>"/>
<script src="<c:url value="/src/main/resources/static/images/img.jpg"/>" alt="tables arrangement"/>
</html>