<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
    <body>
        <fmt:setBundle basename="outputs"/>
        <c:set var="localeCode" value="${pageContext.response.locale}"/>
        <fmt:message key="registration.title" var="pageTitle"/>
        <jsp:include page="header.jsp">
            <jsp:param name="title" value="${pageTitle}"/>
        </jsp:include><br><br>
        <form class="w3-center" action="${pageContext.request.contextPath}/register" method="post">
            <label for="enFirstName"><fmt:message key="registration.en_first_name"/></label><br>
            <input type="text" id="enFirstName" name="enFirstName"><br><br>
            <label for="enLastName"><fmt:message key="registration.en_last_name"/></label><br>
            <input type="text" id="enLastName" name="enLastName"><br><br>
            <label for="uaFirstName"><fmt:message key="registration.ua_first_name"/></label><br>
            <input type="text" id="uaFirstName" name="uaFirstName" lang="uk"><br><br>
            <label for="uaLastName"><fmt:message key="registration.ua_last_name"/></label><br>
            <input type="text" id="uaLastName" name="uaLastName" lang="uk"><br><br>
            <label for="email"><fmt:message key="registration.email"/></label><br>
            <input type="text" id="email" name="email"><br><br>
            <label for="password"><fmt:message key="registration.password"/></label><br>
            <input type="password" id="password" name="password"><br><br>
            <label for="age"><fmt:message key="registration.age"/></label><br>
            <input type="number" min="18" max="100" id="age" name="age"><br><br>
            <label for="phone"><fmt:message key="registration.phone"/></label><br>
            <input type="text" id="phone" name="phone" placeholder="00380XXXXXXXXX"><br><br>
            <input type="submit" value=<fmt:message key="registration.button"/>>
        </form>
        <c:if test="${not empty enErrorMessage}">
            <c:choose>
                <c:when test="${localeCode == 'uk'}">
                    <c:out value="${ukErrorMessage}"/>
                </c:when>
                <c:otherwise>
                    <c:out value="${enErrorMessage}"/>
                </c:otherwise>
            </c:choose>
        </c:if>
    </body>
</html>