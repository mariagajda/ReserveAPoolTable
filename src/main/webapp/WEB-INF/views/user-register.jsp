<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration form</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/appCss.css"/>">
</head>
<body>
<%@include file="includes/appHeader.jsp" %>

<h1>Registration form</h1>
<%--@elvariable id="registeredUserDTO" type="pl.coderslab.reserveapooltable.DTO.RegisteredUserDTO"--%>
<form:form action="/user/register" modelAttribute="registeredUserDTO" method="post">
    <form:errors cssClass="error"/><br/>
    <label>Name:
        <form:input path="username"/>
        <form:errors path="username" cssClass="error"/>
    </label><br/>
    <label>Email:
        <form:input path="email"/>
        <form:errors path="email" cssClass="error"/>
    </label><br/>
    <label>Phone number:
        <form:input path="phoneNumber"/>
        <form:errors path="phoneNumber" cssClass="error"/>
    </label><br/>
    <label>Password:
        <form:password path="password"/>
        <form:errors path="password" cssClass="error"/>
    </label><br/>
    <label>Confirm password:
        <form:password path="matchingPassword"/>
        <form:errors path="matchingPassword" cssClass="error"/>
    </label><br/>
    <label>
        <form:checkbox path="usageAcceptance"/>
        <form:errors path="usageAcceptance" cssClass="error"/>
        I accept the Terms of Use and Privacy Policy
    </label><br/>

    <input type="submit" value="Register"/>
</form:form>

</body>
</html>