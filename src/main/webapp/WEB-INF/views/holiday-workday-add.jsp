<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Holiday-Workday</title>

</head>
<body>
<h1>Add Holiday-Workday</h1>
<%--@elvariable id="holidayWorkday" type="pl.coderslab.reserveapooltable.entity.HolidayWorkdays"--%>
<form:form action="/holiday-workdays/add" modelAttribute="holidayWorkday" method="post">
    <label>Date:
        <form:input path="date"/>
        <form:errors path="date"/>
    </label><br/>

    <input type="submit" value="Add"/>
</form:form>
</body>
</html>