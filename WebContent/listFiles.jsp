<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<c:forEach var="map" items="${requestScope.files}">
    <c:url value="/DownloadServlet" var="downloadUrl">
        <c:param name="fileName" value="${map.key}"/>
    </c:url>
    ${map.value}&emsp;<a href="${downloadUrl}">下载</a><br>
</c:forEach>
</body>
</html>
