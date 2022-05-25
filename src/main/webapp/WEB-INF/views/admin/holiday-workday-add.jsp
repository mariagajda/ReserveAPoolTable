<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<sec:authorize access="hasRole('ADMIN')">
<html>
<head>
    <title>Add Holiday-Workday</title>

</head>
<body>
<%@include file="../includes/appHeader.jsp" %>
<h1>Add Holiday-Workday</h1>
    <%--@elvariable id="holidayWorkday" type="pl.coderslab.reserveapooltable.entity.HolidayWorkday"--%>
<form:form action="/holiday-workdays/add" modelAttribute="holidayWorkday" method="post">
    <label>Date:
        <fmt:formatDate value="${holidayWorkday.date}" var="dateString" pattern="yyyy-MM-dd" />
        <form:input path="date" value="${dateString}"/>
        <form:errors path="date"/>
    </label><br/>

    <input type="submit" value="Add"/>
</form:form>
</body>
</html>
</sec:authorize>