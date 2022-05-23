<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2>Online payment page</h2>

<a href="<c:url value="/reservation/payment/succeeded"/>">Reservation paid</a>
<a href="<c:url value="/reservation/payment/failed"/>">Reservation unpaid</a>