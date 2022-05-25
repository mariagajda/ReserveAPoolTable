<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
</head>

<body>
<%@include file="includes/appHeader.jsp" %>
<h3>You choose to pay in Club directly before play</h3>
<p>You are obliged to pay ${reservationsBasket.priceSum} PLN</p>

<a href="/reservation/succeeded">Click here to finish your reservation</a>
<a href="/reservation/failed">Click here to resign</a>

</body>
</html>
