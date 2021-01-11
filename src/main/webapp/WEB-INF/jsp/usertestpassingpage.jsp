<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Test Passing Page</title>
</head>
<body>
<fmt:setBundle basename="outputs"/>
<c:set var="localeCode" value="${pageContext.response.locale}"/>
<fmt:message key="user.home_title" var="pageTitle"/>
<jsp:include page="userheader.jsp">
    <jsp:param name="title" value="${pageTitle}"/>
</jsp:include><br><br>

<div align="center">
    <h1><fmt:message key="heading.user.test.selecting"/> </h1>
</div>

<form class="w3-center" action="${pageContext.request.contextPath}/user/testPassingPage/select" method="post">
    <label for="id"><fmt:message key="user.choose_tests"/></label><br>
    <input type="number" min="1" id="id" name="id" placeholder="id number"><br><br>
    <input type="submit" value=<fmt:message key="submit.button"/>>
</form>

<div align="center">
    <h1><fmt:message key="heading.user.test.passing"/> </h1>
</div>

<form class="w3-center" action="${pageContext.request.contextPath}/user/testPassingPage/pass" method="post">
    <label for="id1"><fmt:message key="user.pass_tests"/></label><br>
    <input type="number" min="1" id="id1" name="id1" placeholder="id number"><br><br>
    <input type="submit" value=<fmt:message key="submit.button"/>>
</form>

<table class="w3-table w3-bordered w3-striped">
    <thead>
    <tr>
        <th><fmt:message key="question.id"/></th>
        <th><fmt:message key="question.text_en"/></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${questions}" var="question">
        <tr>
            <c:choose>
                <c:when test="${localeCode == 'uk'}">
                    <th><c:out value="${question.id}"/></th>
                    <th><c:out value="${question.enText}"/></th>

                </c:when>
                <c:otherwise>
                    <th><c:out value="${question.id}"/></th>
                    <th><c:out value="${question.uaText}"/></th>
                </c:otherwise>
            </c:choose>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
