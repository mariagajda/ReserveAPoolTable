<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>

</head>
<body>
<%@include file="includes/appHeader.jsp" %>

<h2>Online payment page</h2>
<p> Amount to be paid: <c:out value="${reservationsBasket.priceSum}"/> PLN</p>

<a href="/reservation/succeeded">Reservation paid</a>
<a href="/reservation/failed">Reservation unpaid</a>
</body>
</html>
