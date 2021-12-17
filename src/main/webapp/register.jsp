<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page import="edu.fpt.hotel_booking.servlet.RegisterServlet" %>
<c:set var="title" scope="request" value="Register"/>
<c:set var="errors" scope="page" value="${requestScope.ERRORS}"/>

<!doctype html>
<html lang="en">
<jsp:include page="component/head.jsp"/>
<body>
<jsp:include page="component/navbar.jsp"/>

<div class="container-main">
    <div class="container container-sub">
        <div class="row justify-content-center">
            <div class="col-lg-6 col-xl-6">
                <form action="${pageContext.request.contextPath}/actions/register" method="POST">
                    <div class="form-group">
                        <label>Email Address</label>
                        <input name="${RegisterServlet.PARAM_EMAIL}" value="${param.email}" type="email"
                               class="form-control">
                        <small class="alert-error">${errors[RegisterServlet.PARAM_EMAIL]}</small>
                    </div>
                    <div class="form-group">
                        <label>Password</label>
                        <input name="${RegisterServlet.PARAM_PASSWORD}" type="password" class="form-control">
                        <small class="alert-error">${errors[RegisterServlet.PARAM_PASSWORD]}</small>
                    </div>
                    <div class="form-group">
                        <label>Confirm Password</label>
                        <input name="${RegisterServlet.PARAM_CONFIRM_PASSWORD}" type="password" class="form-control">
                        <small class="alert-error">${errors[RegisterServlet.PARAM_CONFIRM_PASSWORD]}</small>
                    </div>
                    <div class="form-group">
                        <label>Full Name</label>
                        <input name="${RegisterServlet.PARAM_NAME}" value="${param.name}" type="text"
                               class="form-control">
                        <small class="alert-error">${errors[RegisterServlet.PARAM_NAME]}</small>
                    </div>
                    <div class="form-group">
                        <label>Phone</label>
                        <input name="${RegisterServlet.PARAM_PHONE}" value="${param.phone}" type="number"
                               class="form-control">
                        <small class="alert-error">${errors[RegisterServlet.PARAM_PHONE]}</small>
                    </div>
                    <div class="form-group">
                        <label>Address</label>
                        <input name="${RegisterServlet.PARAM_ADDRESS}" value="${param.address}" type="text"
                               class="form-control">
                        <small class="alert-error">${errors[RegisterServlet.PARAM_ADDRESS]}</small>
                    </div>
                    <button type="submit" class="btn btn-primary">Register</button>
                </form>
            </div>
        </div>
    </div>
</div>

<jsp:include page="component/script.jsp"/>
</body>
</html>