<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<body>
<fmt:setBundle basename="outputs"/>
<c:set var="localeCode" value="${pageContext.response.locale}"/>
<fmt:message key="user.home_title" var="pageTitle"/>
<jsp:include page="userheader.jsp">
    <jsp:param name="title" value="${pageTitle}"/>
</jsp:include><br><br>

<div class="w3-row-padding w3-margin-top">
    <fmt:message key="test.search_by_subject"/>
    <form action="${pageContext.request.contextPath}/user/test/searchBySubject" method="get">
        <select name="searchedSubject">
            <c:forEach items="${subjects}" var="subject">
                <c:choose>
                    <c:when test="${localeCode == 'uk'}">
                        <option value="${subject.id}" selected><c:out value="${subject.uaName}"/></option>
                    </c:when>
                    <c:otherwise>
                        <option value="${subject.id}" selected><c:out value="${subject.enName}"/></option>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </select>
        <br><input type="submit" value="<fmt:message key="submit.button"/>"/>
    </form>

    <table class="w3-table w3-bordered w3-striped">
        <thead>
        <tr>
            <th><fmt:message key="test.en_name"/></th>
            <th><fmt:message key="test.subject_name"/></th>
            <th><fmt:message key="test.difficulty"/></th>
            <th><fmt:message key="test.question_amount"/></th>

        </tr>
        </thead>
        <tbody>
        <c:forEach items="${testWithSubjectDtos}" var="testWithSubjectDtos">
        <tr>
            <c:choose>
                <c:when test="${localeCode == 'uk'}">
                    <td><c:out value="${testWithSubjectDtos.uaNameTest}"/></td>
                    <td><c:out value="${testWithSubjectDtos.uaNameSubject}"/></td>
                    <td><c:out value="${testWithSubjectDtos.difficulty}"/></td>
                    <td><c:out value="${testWithSubjectDtos.questionAmount}"/></td>
                </c:when>
                <c:otherwise>
                    <td><c:out value="${testWithSubjectDtos.enNameTest}"/></td>
                    <td><c:out value="${testWithSubjectDtos.enNameSubject}"/></td>
                    <td><c:out value="${testWithSubjectDtos.difficulty}"/></td>
                    <td><c:out value="${testWithSubjectDtos.questionAmount}"/></td>
                </c:otherwise>
            </c:choose>
        </tr>
        </c:forEach>
        <tbody>
    </table>

    <fmt:message key="test.sort_by"/>
    <form action="${pageContext.request.contextPath}/user/test/all" method="get">
        <select name="sortBy">
            <c:choose>
                <c:when test="${localeCode == 'uk'}">
                    <option value="uaName" selected><c:out value="назва"/></option>
                    <option value="difficulty" selected><c:out value="Складність"/></option>
                    <option value="questionAmount" selected><c:out value="Кількість Питань"/></option>
                </c:when>
                <c:otherwise>
                    <option value="enName" selected><c:out value="name"/></option>
                    <option value="difficulty" selected><c:out value="Difficulty"/></option>
                    <option value="questionAmount" selected><c:out value="Question Amount"/></option>
                </c:otherwise>
            </c:choose>
        </select>
        <br><input type="submit" value="<fmt:message key="submit.button"/>"/>
    </form>

    <table class="w3-table w3-bordered w3-striped">
        <thead>
        <tr>
            <th><fmt:message key="test.en_name"/></th>
            <th><fmt:message key="test.subject_name"/></th>
            <th><fmt:message key="test.difficulty"/></th>
            <th><fmt:message key="test.question_amount"/></th>

        </tr>
        </thead>
        <tbody>
        <c:forEach items="${testsWithSubjectDto.testWithSubjectDtos}" var="testWithSubjectDtos">
        <tr>
            <c:choose>
                <c:when test="${localeCode == 'uk'}">
                    <td><c:out value="${testWithSubjectDtos.uaNameTest}"/></td>
                    <td><c:out value="${testWithSubjectDtos.uaNameSubject}"/></td>
                    <td><c:out value="${testWithSubjectDtos.difficulty}"/></td>
                    <td><c:out value="${testWithSubjectDtos.questionAmount}"/></td>
                </c:when>
                <c:otherwise>
                    <td><c:out value="${testWithSubjectDtos.enNameTest}"/></td>
                    <td><c:out value="${testWithSubjectDtos.enNameSubject}"/></td>
                    <td><c:out value="${testWithSubjectDtos.difficulty}"/></td>
                    <td><c:out value="${testWithSubjectDtos.questionAmount}"/></td>
                </c:otherwise>
            </c:choose>
        </tr>
        </c:forEach>
        <tbody>
    </table>

    <div class="w3-center w3-padding-32">
        <div class="w3-bar">
            <c:forEach begin="1" end="${testsWithSubjectDto.totalPages}" varStatus="loop">
                <a href="${pageContext.request.contextPath}/user/test/all?pageNo=${loop.index - 1}" class="w3-bar-item w3-button w3-theme">${loop.index}</a>
            </c:forEach>
        </div>
    </div>


</div>
</body>
</html>