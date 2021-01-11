<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
    <fmt:setBundle basename="outputs"/>
    <body>
    <h1><fmt:message key="validation.error.message"/></h1>
    <div><fmt:message key="validation.field.empty.message"/></div>
    <div><fmt:message key="validation.id.message"/></div>
    <div><fmt:message key="validation.email.message"/></div>
    <div><fmt:message key="validation.password.message"/></div>
    <div><fmt:message key="validation.phone.message"/></div>
    <div><fmt:message key="validation.number.message"/></div>
    <div><fmt:message key="validation.age.message"/></div>
    <div><fmt:message key="validation.English.String.message"/></div>
    <div><fmt:message key="validation.Ukrainian.String.message"/></div>
    <div><fmt:message key="validation.date.message"/></div>
    <div><fmt:message key="validation.Boolean.message"/></div>
        </body>
</html>
