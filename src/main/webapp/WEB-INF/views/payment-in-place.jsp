<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
</head>

<body>
<%@include file="includes/appHeader.jsp" %>

<p>You choose to pay in Club directly before play</p>
<p>You are obliged to pay ${reservationsBasket.priceSum} PLN</p>

<h3><a href="/reservation/succeeded">Click here to finish your reservation</a></h3>
<a href="/reservation/failed">Click here to resign</a>

</body>
</html>
