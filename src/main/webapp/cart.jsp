<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="edu.fpt.hotel_booking.servlet.CartServlet" %>
<%@ page import="edu.fpt.hotel_booking.servlet.BookingServlet" %>

<c:set var="title" scope="request" value="Cart"/>
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
            <div class="col-xm-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
                <table class="table table-striped" style="background-color: white;">
                    <thead class="thead-dark">
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Hotel</th>
                        <th scope="col">Type</th>
                        <th scope="col">Amount</th>
                        <th scope="col">Price</th>
                        <th scope="col">Total</th>
                        <th scope="col"></th>
                        <th scope="col"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="item" items="${sessionScope.SESSION_CART.items.values()}" varStatus="status">
                        <c:set var="total" scope="page" value="${item.room.roomType.price * item.amount}"/>
                        <tr>
                            <th scope="row">${status.index}</th>
                            <td>${item.room.hotel.name}</td>
                            <td>${item.room.roomType.name}</td>
                            <td>
                                <form id="${'form-update-'.concat(item.room.id)}" method="POST"
                                      action="${pageContext.request.contextPath}/actions/cart">
                                    <input type="hidden" name="${CartServlet.PARAM_ROOM}" value="${item.room.id}"/>
                                    <input type="number" name="${CartServlet.PARAM_AMOUNT}" value="${item.amount}"/>
                                </form>
                            </td>
                            <td>${item.room.roomType.price}$</td>
                            <td>${total}$</td>
                            <td>
                                <button type="submit" form="${'form-update-'.concat(item.room.id)}"
                                        class="btn btn-primary">Update
                                </button>
                            </td>
                            <td>
                                <form class="form-delete" method="POST"
                                      action="${pageContext.request.contextPath}/actions/cart">
                                    <input type="hidden" name="${CartServlet.PARAM_ROOM}" value="${item.room.id}">
                                    <input type="hidden" name="${CartServlet.PARAM_AMOUNT}" value="${0}">
                                    <button type="submit" class="btn btn-warning">Delete</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <div class="col-xm-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
                <c:if test="${empty sessionScope.SESSION_CART}">
                    <div class="alert alert-primary" role="alert">
                        Cart empty!
                    </div>
                </c:if>
                <c:if test="${not empty sessionScope.SESSION_CART}">
                    <div class="alert alert-primary" role="alert">
                        <b>Total: ${sessionScope.SESSION_CART.calculateTotal()}$</b>
                    </div>
                </c:if>
            </div>

            <c:if test="${sessionScope.SESSION_CART.items.size() > 0}">
                <div class="col-xm-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
                    <form action="${pageContext.request.contextPath}/actions/booking" id="form-booking" method="POST">
                        <input name="${BookingServlet.PARAM_DISCOUNT}" type="number" placeholder="discount code"/><br/>
                        <button type="submit" class="btn btn-success" style="margin-top: 20px;">Confirm</button>
                    </form>
                </div>
            </c:if>

            <div class="col-xm-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
                <c:if test="${not empty requestScope.ERROR}">
                    <div class="alert alert-danger" role="alert">
                            ${requestScope.ERROR}
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</div>

<script>
    let deleteForms = document.getElementsByClassName("form-delete");
    for (let form of deleteForms) {
        form.onsubmit = submitDeleteForm;
    }

    function submitDeleteForm(e) {
        e.preventDefault();
        showDialog(e.target);
    }

    function showDialog(form) {
        Swal.fire({
            title: 'Are you sure?',
            text: "You won't be able to revert this!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                form.submit();
            }
        })
    }
</script>

<script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<jsp:include page="component/script.jsp"/>
</body>
</html>