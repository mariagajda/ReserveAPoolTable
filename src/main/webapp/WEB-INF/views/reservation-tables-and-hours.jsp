<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="pl">
<head>
    <title>Reserve a Service</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/appCss.css"/>"/>
</head>
<body>
<%@include file="includes/appHeader.jsp" %>

<h1>Reserve a Service</h1>
<div>
    <form action="/reservation/date" method="post">
        <label>Pick date
            <input type="date" name="dateStr" value="${date}"/>
        </label>
        <input type=submit value="Check availability"/>
    </form>
</div>
<div>
    <img src="<c:url value="/resources/images/tablesArrangement.jpg"/>" alt="tables arrangement" width="300" height="200"/>
</div>
<div>
    <form action="/reservation/datetablesandhours" method="post">
        <h3>Available hours & pool tables:</h3>
        <table>
            <tbody>
            <c:forEach items="${tables}" var="table">
                <c:set var="tableNumber" value="${table.tableNumber}"/>
                <tr>
                    <th>
                        Table No.${table.tableNumber}:
                    </th>

                    <c:forEach items="${reservations}" var="reservation">
                        <c:set var="tableNumberFromReservation" value="${reservation.table.tableNumber}"/>
                        <c:if test="${tableNumber eq tableNumberFromReservation}">
                            <c:choose>
                                <c:when test="${reservation.isAvailable()}">
                                    <td class="availableClass">
                                    <input type="checkbox" name="pickedReservations" value="${reservation.id}"
                                           id="checkbox"/>
                                    ${reservation.startDateTime.getHour()}:<fmt:formatNumber type="number"
                                                                                             minIntegerDigits="2"
                                                                                             value="${reservation.startDateTime.getMinute()}"/>
                                    - ${reservation.endDateTime.getHour()}:<fmt:formatNumber type="number"
                                                                                             minIntegerDigits="2"
                                                                                             value="${reservation.endDateTime.getMinute()}"/>
                                </c:when>
                                <c:otherwise>
                                    <td class="unavailableClass">
                                    ${reservation.startDateTime.getHour()}:<fmt:formatNumber type="number"
                                                                                             minIntegerDigits="2"
                                                                                             value="${reservation.startDateTime.getMinute()}"/>
                                    - ${reservation.endDateTime.getHour()}:<fmt:formatNumber type="number"
                                                                                             minIntegerDigits="2"
                                                                                             value="${reservation.endDateTime.getMinute()}"/>
                                </c:otherwise>
                            </c:choose>
                            </td>
                        </c:if>

                    </c:forEach>

                </tr>

            </c:forEach>
            </tbody>
        </table>
        <br/>
        <input type="submit" value="Confirm reservation"/>

    </form>
    <br/>
    <table>
        <tbody>
        <tr>
            <td class="availableClass"></td>
            <td> - available</td>
        </tr>
        <tr>
            <td class="unavailableClass"></td>
            <td> - unavailable</td>
        </tr>
        <tr>
            <td class="currentChoiceClass"></td>
            <td> - your choice</td>
        </tr>
        </tbody>
    </table>
</div>
</body>
<script type="text/javascript" src="<c:url value="/resources/js/app.js"/>"></script>

</html>