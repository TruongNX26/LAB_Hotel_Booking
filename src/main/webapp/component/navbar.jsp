<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="edu.fpt.hotel_booking.servlet.SearchServlet" %>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/pages/home">Home</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <c:if test="${sessionScope.SESSION_USER.role.name() != 'ADMIN'}">
                <li class="nav-item active">
                    <a class="nav-link" href="${pageContext.request.contextPath}/pages/cart">Cart <span class="sr-only">(current)</span></a>
                </li>
            </c:if>
            <c:if test="${sessionScope.SESSION_USER.role.name() == 'CUSTOMER'}">
                <li class="nav-item active">
                    <a class="nav-link" href="${pageContext.request.contextPath}/pages/history">History <span
                            class="sr-only">(current)</span></a>
                </li>
            </c:if>
        </ul>

        <span class="badge badge-success nav-component">${sessionScope.SESSION_USER.name}</span>

        <c:if test="${empty sessionScope.SESSION_USER}">
            <a href="${pageContext.request.contextPath}/pages/login" class="nav-component">
                <button type="button" class="btn btn-primary">Login</button>
            </a>
        </c:if>
        <c:if test="${not empty sessionScope.SESSION_USER}">
            <a href="${pageContext.request.contextPath}/actions/logout" class="nav-component">
                <button type="button" class="btn btn-primary">Logout</button>
            </a>
        </c:if>

        <form class="form-inline my-2 my-lg-0" id="form-search">
            <select name="${SearchServlet.PARAM_HOTEL}" form="form-search" class="search-component">
                <option value="-1">Any Hotel</option>
                <c:forEach var="hotel" items="${requestScope.HOTELS}">
                    <option value="${hotel.id}"
                            <c:if test="${param.hotel_id == hotel.id}">
                                selected
                            </c:if>
                    >${hotel.name}</option>
                </c:forEach>
            </select>

            <input name="${SearchServlet.PARAM_AMOUNT}" type="number" value="${param.amount}" placeholder="Amount">
            <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
        </form>
    </div>
</nav>