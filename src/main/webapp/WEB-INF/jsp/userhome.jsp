<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
    <body>
        <fmt:setBundle basename="outputs"/>
        <c:set var="localeCode" value="${pageContext.response.locale}"/>
        <fmt:message key="user.home_title" var="pageTitle"/>
        <jsp:include page="userheader.jsp">
            <jsp:param name="title" value="${pageTitle}"/>
        </jsp:include><br><br>

        <div align="center">
            <h1><fmt:message key="heading.user.info"/> </h1>
        </div>

        <table class="w3-table w3-bordered w3-striped">
            <thead>
            <c:choose>
                <c:when test="${localeCode == 'uk'}">
                    <th><fmt:message key="user.id"/></th>
                    <th><fmt:message key="user.first_name.ua"/></th>
                    <th><fmt:message key="user.last_name.ua"/></th>
                    <th><fmt:message key="user.email"/></th>
                    <th><fmt:message key="user.age"/></th>
                    <th><fmt:message key="user.phone"/></th>

                </c:when>
                <c:otherwise>
                    <th><fmt:message key="user.id"/></th>
                    <th><fmt:message key="user.first_name.en"/></th>
                    <th><fmt:message key="user.last_name.en"/></th>
                    <th><fmt:message key="user.email"/></th>
                    <th><fmt:message key="user.age"/></th>
                    <th><fmt:message key="user.phone"/></th>
                </c:otherwise>
            </c:choose>
            </thead>
            <tbody>
                <tr>
                    <c:choose>
                        <c:when test="${localeCode == 'uk'}">
                            <th><c:out value="${userInfoDto.userId}"/></th>
                            <th><c:out value="${userInfoDto.uaFirstName}"/></th>
                            <th><c:out value="${userInfoDto.uaLastName}"/></th>
                            <th><c:out value="${userInfoDto.email}"/></th>
                            <th><c:out value="${userInfoDto.age}"/></th>
                            <th><c:out value="${userInfoDto.phone}"/></th>

                        </c:when>
                        <c:otherwise>
                            <th><c:out value="${userInfoDto.userId}"/></th>
                            <th><c:out value="${userInfoDto.enFirstName}"/></th>
                            <th><c:out value="${userInfoDto.enLastName}"/></th>
                            <th><c:out value="${userInfoDto.email}"/></th>
                            <th><c:out value="${userInfoDto.age}"/></th>
                            <th><c:out value="${userInfoDto.phone}"/></th>
                        </c:otherwise>
                    </c:choose>
                </tr>
            </tbody>
        </table>

        <div align="center">
            <h1><fmt:message key="heading.tests.with.marks"/> </h1>
        </div>

        <table class="w3-table w3-bordered w3-striped">
            <thead>
            <tr>
                <c:choose>
                <c:when test="${localeCode == 'uk'}">
                <th><fmt:message key="testsWithResultsDto.testId"/></th>
                <th><fmt:message key="testsWithResultsDto.testUaName"/></th>
                <th><fmt:message key="testsWithResultsDto.subject"/></th>
                <th><fmt:message key="testsWithResultsDto.mark"/></th>
                </c:when>
                    <c:otherwise>
                        <th><fmt:message key="testsWithResultsDto.testId"/></th>
                        <th><fmt:message key="testsWithResultsDto.testEnName"/></th>
                        <th><fmt:message key="testsWithResultsDto.subject"/></th>
                        <th><fmt:message key="testsWithResultsDto.mark"/></th>
                    </c:otherwise>
                </c:choose>
            </tr>
            </thead>
            <tbody>

            <c:forEach items="${testsWithResultsDtos}" var="testsWithResultsDto">
            <tr>
                <c:choose>
                    <c:when test="${localeCode == 'uk'}">
                        <th><c:out value="${testsWithResultsDto.testId}"/></th>
                        <th><c:out value="${testsWithResultsDto.testUaName}"/></th>
                        <th><c:out value="${testsWithResultsDto.subjectUaName}"/></th>
                        <th><c:out value="${testsWithResultsDto.mark}"/></th>
                    </c:when>
                    <c:otherwise>
                        <th><c:out value="${testsWithResultsDto.testId}"/></th>
                        <th><c:out value="${testsWithResultsDto.testEnName}"/></th>
                        <th><c:out value="${testsWithResultsDto.subjectEnName}"/></th>
                        <th><c:out value="${testsWithResultsDto.mark}"/></th>
                    </c:otherwise>
                </c:choose>
            </tr>
            </c:forEach>
            </tbody>
        </table>


    </body>
</html>