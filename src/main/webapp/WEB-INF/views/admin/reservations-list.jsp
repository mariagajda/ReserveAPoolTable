<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize access="hasRole('ADMIN')">
<html>
<head>

</head>
<body>
<%@include file="../includes/appHeader.jsp" %>






</body>
</html>
</sec:authorize>