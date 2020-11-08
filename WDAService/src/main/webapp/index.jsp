<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<html>
<body>
<h2></h2>
<script type="text/javascript">
	window.location.href = "${ctx}/user/toUrl.do?page=/login"
</script>
</body>
</html>
