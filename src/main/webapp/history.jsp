<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="edu.fpt.hotel_booking.servlet.HistoryServlet" %>

<c:set var="title" scope="request" value="Booking History"/>
<c:if test="${sessionScope.SESSION_USER.role.name() != 'CUSTOMER'}">
    <c:redirect url="/pages/login"/>
</c:if>

<!doctype html>
<html lang="en">
<jsp:include page="component/head.jsp"/>
<body>
<jsp:include page="component/navbar.jsp"/>

<div class="container-main">
    <div class="container container-sub">
        <div class="row">
            <form action="${pageContext.request.contextPath}/actions/history" method="GET">
                <div class="col">
                    <label>Booking Date:&nbsp;</label>
                    <input type="date" name="${HistoryServlet.PARAM_DATE}" value="${param.booking_date}">
                </div>
                <div class="col">
                    <button class="btn btn-primary" type="submit">Search</button>
                </div>
            </form>
        </div>
        <div class="row">
            <div class="col">
                <table class="table table-striped" style="background-color: white; margin-top: 30px;">
                    <thead class="thead-dark">
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Date</th>
                        <th scope="col">Total</th>
                        <th scope="col">Discount</th>
                        <th scope="col">Status</th>
                        <th scope="col">Rating</th>
                        <th scope="col">Delete</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="item" items="${requestScope.BOOKINGS}" varStatus="status">
                        <tr>
                            <th scope="row">${status.index}</th>
                            <td>${item.bookingDate}</td>
                            <td>${item.total}</td>
                            <td>${item.discount}%</td>
                            <td>${item.status}</td>
                            <td>
                                <c:if test="${item.status == 'CHECKED_OUT'}">
                                    <c:if test="${item.rating > 0}">
                                        ${item.rating}
                                    </c:if>
                                    <c:if test="${item.rating <= 0}">
                                        <form action="${pageContext.request.contextPath}/actions/rating" method="POST" id="form-rating-${item.id}">
                                            <input name="booking_id" type="hidden" value="${item.id}">
                                            <input name="point" type="number" min="1" max="10" value="5">
                                            <button type="submit" form="form-rating-${item.id}" class="btn btn-primary">Rate</button>
                                        </form>
                                    </c:if>
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${item.status == 'CONFIRMING' || item.status == 'ACTIVE'}">
                                    <a href="${pageContext.request.contextPath}/actions/delete-booking?booking_id=${item.id}"
                                       class="btn btn-warning">Delete</a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<jsp:include page="component/script.jsp"/>
</body>
</html>