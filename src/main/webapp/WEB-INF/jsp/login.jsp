<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
    <body>
        <fmt:setBundle basename="outputs"/>
        <c:set var="localeCode" value="${pageContext.response.locale}"/>
        <fmt:message key="login.title" var="pageTitle"/>
        <jsp:include page="header.jsp">
            <jsp:param name="title" value="${pageTitle}"/>
        </jsp:include><br><br>
    <form class="w3-center" action="${pageContext.request.contextPath}/authenticate" method="post">
        <label for="email"><fmt:message key="login.email"/></label><br>
        <input type="text" id="email" name="email" value="<c:if test='${not empty emailLogin}'>${emailLogin}</c:if>"><br><br>
        <label for="password"><fmt:message key="login.password"/></label><br>
        <input type="password" id="password" name="password"><br><br>
        <input type="submit" value="<fmt:message key="login.button"/>">
    </form>
    </body>
</html>