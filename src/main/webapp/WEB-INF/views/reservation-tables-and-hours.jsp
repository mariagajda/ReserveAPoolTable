<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Reserve a Service</title>

</head>
<body>
<h1>Reserve a Service</h1>
<form action="/date" method="post">
    <label>Pick date
        <input type="date" name="date" value="${date}"/>
    </label>
    <input type=submit value="Check availability"/>
</form>


<h3>Available hours & tables:</h3>
<table>
    <%--    <thead>--%>
    <%--    <tr>--%>
    <%--        <c:forEach items="${tables}" var="table">--%>
    <%--            <th><p>Table No.</p>--%>
    <%--                <p>${table.tableNumber}</p></th>--%>
    <%--        </c:forEach>--%>
    <%--    </tr>--%>
    <%--    </thead>--%>
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
                            <td style="background-color: lightgreen"> ${reservation.startTime} - ${reservation.endTime}
                        </c:when>
                        <c:otherwise>
                            <td style="background-color: indianred"> ${reservation.startTime} - ${reservation.endTime}
                        </c:otherwise>
                    </c:choose>
                    </td>
                </c:if>

            </c:forEach>
        </tr>
    </c:forEach>
    </tbody>
</table>

<div>
    <h4>Your reservation details:</h4>
    <p>Date: ${date}</p>
    <p>Table: </p>
    <p>Total Price: </p>

</div>
</body>
</html>