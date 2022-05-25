<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Reserve a Service</title>
    <style>
        .error {
            color: red;
        }
    </style>
</head>
<body>
<%@include file="includes/appHeader.jsp" %>

<h1>Reserve a Service</h1>
<div>
    <h3>Your reservation details:</h3>
    <p>Date: <c:out value="${reservationsBasket.date}"/></p>
    <c:forEach items="${reservationsBasket.reservations}" var="reservation">
        <p>Table
            no. ${reservation.table.tableNumber}: ${reservation.startDateTime.getHour()}:${reservation.startDateTime.getMinute()}
            - ${reservation.endDateTime.getHour()}:${reservation.endDateTime.getMinute()}</p>
    </c:forEach>
</div>

<sec:authorize access="isAuthenticated()">
        <p>Discount: <fmt:formatNumber value="${reservationsBasket.user.discount}" type="percent"/></p>
        <p>
            <c:choose>
                <c:when test="${reservationsBasket.user.discount > 0}">
                    Price: <span class="discount"><fmt:formatNumber value="${reservationsBasket.priceSum}" type="number"
                                                                    maxFractionDigits="2"
                                                                    minFractionDigits="2"/></span> /<fmt:formatNumber
                        value="${reservationsBasket.priceSum*(1-reservationsBasket.user.discount)}" type="number" maxFractionDigits="2"
                        minFractionDigits="2"/> PLN
                </c:when>
                <c:otherwise>
                    Price: <fmt:formatNumber value="${reservationsBasket.priceSum}" type="number" maxFractionDigits="2"
                                             minFractionDigits="2"/> PLN
                </c:otherwise>
            </c:choose>
        </p>

        </div>
        <div>
        <form action="/reservation/payment" method="get">
            <label>Payment method:
                <select name="paymentMethod">
                    <option value="">Select...</option>
                    <option value="transfer">Online payment</option>
                    <option value="inPlace">Pay in place</option>
                </select>
            </label><br/>
            <input type="submit" value="Make a reservation"/>
        </form>
        </div>
</sec:authorize>
<sec:authorize access="isAnonymous()">
        <p>Price: <fmt:formatNumber value="${reservationsBasket.priceSum}" type="number" maxFractionDigits="2" minFractionDigits="2"/>
            PLN</p>

        <div><h4>Fill a form:</h4>
                <%--@elvariable id="user" type="pl.coderslab.reserveapooltable.entity.User"--%>
            <form:form action="/user/add" modelAttribute="user" method="post">
                <label>Email:
                    <form:input path="email"/>
                    <form:errors path="email"/>
                </label><br/>
                <label>Phone number:
                    <form:input path="phoneNumber"/>
                    <form:errors path="phoneNumber" cssClass="error"/>
                </label><br/>
                <label>Payment method:
                    <select name="paymentMethod">
                        <option value="">Select...</option>
                        <option value="transfer">Online payment</option>
                        <option value="inPlace">Pay in place</option>
                    </select>
                </label>
                <c:forEach items="${reservationsBasket.reservations}" var="reservation">
                    <input type="hidden" name="reservationsIdList" value="${reservation.id}"/>
                </c:forEach>
                <label>
                    <form:checkbox path="usageAcceptance"/>
                    <form:errors path="usageAcceptance" cssClass="error"/>
                    I accept the Terms of Use and Privacy Policy
                </label><br/>
                <input type="submit" value="Make a reservation"/>
            </form:form>
        </div>
</sec:authorize>

</body>
</html>