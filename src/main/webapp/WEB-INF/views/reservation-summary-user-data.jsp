<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<h1>Reserve a Service</h1>
<div>
    <h3>Your reservation details:</h3>
    <p>Date: <c:out value="${reservationsToConfirm.get(0).getDate()}"/></p>
    <c:forEach items="${reservationsToConfirm}" var="reservation">
        <p>Table no. ${reservation.table.tableNumber}: ${reservation.startDateTime.getHour()}:${reservation.startDateTime.getMinute()}
            - ${reservation.endDateTime.getHour()}:${reservation.endDateTime.getMinute()}</p>
    </c:forEach>
    <c:set var="sum" scope="page" value="0.0"/>
    <p>
        Price: <c:out value="${priceSum}"/> PLN

    </p>
</div>
<div>
    <h4>Are you a regular player? Log in!</h4>
    <form action="/user/log" method="post">
        <label>Email:
            <input type="email" name="email"/>
        </label><br/>
        <label>Password:
            <input type="password" name="password"/>
        </label><br/>
        <input type="submit" value="Log in"/><br/>
        <c:forEach items="${reservationsToConfirm}" var="reservation">
            <input type="hidden" name="reservationsIdList" value="${reservation.id}"/>
        </c:forEach>
    </form>
    <p>Do you want to register? <a href="<c:url value="user/register"/>">Click here</a></p>
</div>
<div>
    <h4>Only this reservation? Fill a form:</h4>
    <%--@elvariable id="user" type="pl.coderslab.reserveapooltable.entity.User"--%>
    <form:form action="/user/add" modelAttribute="user" method="post">
        <label>Name:
            <form:input path="name"/>
            <form:errors path="name" cssClass="error"/>
        </label><br/>
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
        <c:forEach items="${reservationsToConfirm}" var="reservation">
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
</body>
</html>