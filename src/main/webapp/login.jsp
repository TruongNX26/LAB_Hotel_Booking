<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page import="edu.fpt.hotel_booking.servlet.LoginServlet" %>
<c:set var="title" scope="request" value="Login"/>

<!doctype html>
<html lang="en">
<jsp:include page="component/head.jsp"/>
<body>
<jsp:include page="component/navbar.jsp"/>

<div class="container-main">
    <div class="container container-sub">
        <div class="row justify-content-center">
            <div class="col-lg-6 col-xl-6">
                <form action="${pageContext.request.contextPath}/actions/login" method="POST">
                    <div class="form-group">
                        <label>Email Address</label>
                        <input name="${LoginServlet.PARAM_EMAIL}" value="${param.email}" type="email"
                               class="form-control">
                    </div>
                    <div class="form-group">
                        <label>Password</label>
                        <input name="${LoginServlet.PARAM_PASSWORD}" type="password" class="form-control">
                    </div>
                    <button type="submit" class="btn btn-primary">Login</button>
                    <a href="${pageContext.request.contextPath}/pages/register" style="float: right;">Register</a>
                </form>

                <c:if test="${requestScope.STATUS == 'FAILED'}">
                    <div class="alert alert-danger" role="alert">
                        Login failed, wrong email or password
                    </div>
                </c:if>
                <c:if test="${requestScope.STATUS == 'CREATED'}">
                    <div class="alert alert-primary" role="alert">
                        Account created successfully
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</div>

<jsp:include page="component/script.jsp"/>
</body>
</html>