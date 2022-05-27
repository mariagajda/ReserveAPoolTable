<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>

</head>
<body>
<%@include file="includes/appHeader.jsp" %>

<h2>Reservation failed</h2>

<c:if test="${reservationsBasket.paymentMethod eq 'transfer'}">
    Your payment failed. If you want to pay again <a href="/reservation/payment/transfer">Click here!</a>
</c:if>
<c:if test="${reservationsBasket.paymentMethod eq 'inPlace'}">
    You resign from your reservation.
</c:if>

<a href="/failed/deleteCookie">Homepage</a>
</body>
</html>

