<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update Book Form</title>
    <style>
        .error {
            color: red;
        }
    </style>
</head>
<body>
<%--@elvariable id="price" type="pl.coderslab.entity.Price"--%>
<form:form modelAttribute="price" method="post">
    <label for="dayOfWeek">Day of week: </label>
    <form:input path="dayOfWeek" itemLabel="dayOfWeek"/> PLN/hour
    <form:errors path="dayOfWeek" cssClass="error"/>
    <br/>
    <label for="startTime">Start time: </label>
    <form:input path="startTime" itemLabel="startTime"/> PLN/hour
    <form:errors path="startTime" cssClass="error"/>
    <br/>
    <label for="endTime">End time: </label>
    <form:input path="endTime" itemLabel="endTime"/> PLN/hour
    <form:errors path="endTime" cssClass="error"/>
    <br/>

    <label for="pricePerHour">Price per hour: </label>
    <form:input path="pricePerHour" itemLabel="pricePerHour"/> PLN/hour
    <form:errors path="pricePerHour" cssClass="error"/>
    <br/>

    <input type="submit">
</form:form>
</body>
</html>
