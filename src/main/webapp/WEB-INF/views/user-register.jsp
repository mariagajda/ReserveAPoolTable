<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration form</title>

</head>
<body>
<h1>Registration form</h1>
<%--@elvariable id="registeredUser" type="pl.coderslab.reserveapooltable.entity.RegisteredUser"--%>
<form:form action="/user/register" modelAttribute="registeredUser" method="post">
    <label>Name:
        <form:input path="name"/>
        <form:errors path="name"/>
    </label><br/>
    <label>Email:
        <form:input path="email"/>
        <form:errors path="email"/>
    </label><br/>
    <label>Phone number:
        <form:input path="phoneNumber"/>
        <form:errors path="phoneNumber"/>
    </label><br/>
    <label>Password:
        <form:input path="password"></form:input>
        <form:errors path="password"></form:errors>
    </label>

    <label>
        <form:checkbox path="usageAcceptance"/>
        <form:errors path="usageAcceptance"/>
        I accept the Terms of Use and Privacy Policy
    </label><br/>
    <input type="submit" value="Register"/>
</form:form>

</body>
</html>