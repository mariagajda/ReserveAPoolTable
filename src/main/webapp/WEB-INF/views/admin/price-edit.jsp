<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize access="hasRole('ADMIN')">
<html>
<head>
    <title>Price to edit</title>
    <style>
        .error {
            color: red;
        }
    </style>
</head>
<body>
<%@include file="../includes/appHeader.jsp" %>

<%--@elvariable id="price" type="pl.coderslab.entity.Price"--%>
<form:form modelAttribute="price" method="post">
    <label for="priceGroup">PriceGroup: ${price.priceGroup} </label>
    <form:hidden path="priceGroup" itemLabel="priceGroup"/>
    <br/>
    <label for="isNightTime">Is it nighttime price: </label>
    <form:input path="isNightTime" itemLabel="isNightTime"/>
    <form:errors path="isNightTime" cssClass="error"/>
    <br/>


    <label for="pricePerHour">Price per hour: </label>
    <form:input path="pricePerHour" itemLabel="pricePerHour"/> PLN/hour
    <form:errors path="pricePerHour" cssClass="error"/>
    <br/>

    <input type="submit">
</form:form>
<a href="/price/list">Back to the price list</a>
</body>
</html>
</sec:authorize>