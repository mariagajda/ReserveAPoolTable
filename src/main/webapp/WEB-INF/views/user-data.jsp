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
<div>
    <h4>Are you a regular player? Log in!</h4>
    <label>Email: <input type="email" name="email"/></label>
    <label>Password: <input type="password" name="password"/></label>
    <input type="submit" value="Log in"/>
    <p>Do you want to register? <a href="<c:url value="/user/register"/>"/></p>
</div>
<div>
    <h4>Only this reservation? Fill a form:</h4>
    <%--@elvariable id="user" type="pl.coderslab.reserveapooltable.entity.User"--%>
    <form:form modelAttribute="user" method="post">
        <label>Name:
            <form:input path="name"/>
            <form:errors path="name"/>
        </label>
        <label>Email:
            <form:input path="email"/>
            <form:errors path="email"/>
        </label>
        <label>Phone number:
            <form:input path="phoneNumber"/>
            <form:errors path="phoneNumber"/>
        </label>
        <form:checkbox path="usageAcceptance" item="I accept the Terms of Use and Privacy Policy"/>
        <form:errors path="usageAcceptance"/>
        <br/>
        <input type="submit" value="Make a reservation"/>
    </form:form>
</div>
</body>
</html>