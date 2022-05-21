<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Holiday-workdays</title>

</head>
<body>
<h3>Holiday - workdays</h3>
<a href="/holiday-workdays/add">Add new holiday-workday</a>
<table>
    <thead>
    <tr>
        <th>Id</th>
        <th>Date</th>
        <th></th>
    </tr>
    </thead>
    <tbody>

    <c:forEach items="${holidayWorkdaysList}" var="holidayWorkday">
        <tr>
            <td>${holidayWorkday.id}</td>
            <td>${holidayWorkday.date}</td>
            <td>
                <a href="/holiday-workdays/delete/${holidayWorkday.id}">Delete</a>
            </td>
        </tr>
    </c:forEach>


    </tbody>
</table>

</body>
</html>