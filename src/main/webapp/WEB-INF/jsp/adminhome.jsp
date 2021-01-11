<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
    <body>
        <fmt:setBundle basename="outputs"/>
        <c:set var="localeCode" value="${pageContext.response.locale}"/>
        <fmt:message key="administrator.home_title" var="pageTitle"/>
        <jsp:include page="adminheader.jsp">
            <jsp:param name="title" value="${pageTitle}"/>
        </jsp:include><br><br>
        <div align="center">
            <h1><fmt:message key="heading.admin.block.user"/> </h1>
        </div>
        <form class="w3-center" action="${pageContext.request.contextPath}/admin/blockuser" method="post">
        <label for="id"><fmt:message key="User.block"/></label><br>
        <input type="text" id="id" name="id" placeholder="id number"><br><br>
            <input type="submit" value=<fmt:message key="Block.button"/>>
        </form>
        <div align="center">
            <h1><fmt:message key="heading.admin.unblock.user"/> </h1>
        </div>
        <form class="w3-center" action="${pageContext.request.contextPath}/admin/unblockuser" method="post">
            <label for="id1"><fmt:message key="User.unblock"/></label><br>
            <input type="text" id="id1" name="id1" placeholder="id number"><br><br>
            <input type="submit" value=<fmt:message key="Unblock.button"/>>
        </form>

        <div align="center">
            <h1><fmt:message key="heading.admin.edit.user"/> </h1>
        </div>
        <form class="w3-center" action="${pageContext.request.contextPath}/admin/user/edit" method="post">
            <label for="userId"><fmt:message key="user.id"/></label><br>
            <input type="number" id="userId" name="userId" placeholder="id number"><br><br>
            <label for="enFirstName"><fmt:message key="user.first_name.en"/></label><br>
            <input type="text" id="enFirstName" name="enFirstName"><br><br>
            <label for="uaFirstName"><fmt:message key="user.first_name.ua"/></label><br>
            <input type="text" id="uaFirstName" name="uaFirstName"><br><br>
            <label for="enLastName"><fmt:message key="user.last_name.en"/></label><br>
            <input type="text" id="enLastName" name="enLastName"><br><br>
            <label for="uaLastName"><fmt:message key="user.last_name.ua"/></label><br>
            <input type="text" id="uaLastName" name="uaLastName"><br><br>
            <label for="email"><fmt:message key="user.email"/></label><br>
            <input type="text" id="email" name="email"><br><br>
            <label for="password"><fmt:message key="user.password"/></label><br>
            <input type="password" id="password" name="password" ><br><br>
            <label for="age"><fmt:message key="user.age"/></label><br>
            <input type="number" id="age" name="age" placeholder="age number"><br><br>
            <label for="phone"><fmt:message key="user.phone"/></label><br>
            <input type="text" id="phone" name="phone" placeholder="00380XXXXXXXXX"><br><br>
            <select name="testIds" multiple="multiple">
                    <c:forEach items="${tests}" var="test">
                        <c:choose>
                            <c:when test="${localeCode == 'uk'}">
                                <option value="${test.id}" ><c:out value="${test.uaName}"/></option>
                            </c:when>
                            <c:otherwise>
                                <option value="${test.id}" ><c:out value="${test.enName}"/></option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
            </select>
            <input type="submit" value=<fmt:message key="User.Update"/>>
        </form>
    </body>
</html>