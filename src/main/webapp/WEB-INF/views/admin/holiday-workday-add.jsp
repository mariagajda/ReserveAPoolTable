<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
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
    <form action="/holiday-workdays/add" method="post">
        <label>Date:
            <input type="date" required name="date" min="${minDate}"/>

        </label><br/>

        <input type="submit" value="Add"/>
    </form>
    </body>
    </html>
</sec:authorize>