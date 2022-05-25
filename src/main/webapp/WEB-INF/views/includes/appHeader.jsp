<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<header>
    <sec:authorize access="isAuthenticated()">
        <p>Logged in as: <sec:authentication property="principal.username"/></p>
        <a href="/logout">Logout</a>
    </sec:authorize>
</header>