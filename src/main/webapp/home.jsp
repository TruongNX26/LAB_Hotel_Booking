<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="title" scope="request" value="Home"/>

<!doctype html>
<html lang="en">
<jsp:include page="component/head.jsp"/>
<body>
<jsp:include page="component/navbar.jsp"/>

<div class="container-main">
    <div class="container container-sub">
        <div class="row">
            <c:forEach var="room" items="${requestScope.ROOMS}">
                <div class="col-md-4 col-lg-4 col-xl-4 col-room">
                    <div class="card" style="width: 18rem;">
                        <div class="card-body">
                            <h5 class="card-title">${room.hotel.name} Hotel</h5>
                        </div>
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item">Type: ${room.roomType.name}</li>
                            <li class="list-group-item">Price: ${room.roomType.price}</li>
                            <li class="list-group-item">Quantity: ${room.quantity}</li>
                        </ul>
                        <div class="card-body">
                            <a href="${pageContext.request.contextPath}/actions/cart?room_id=${room.id}"
                               class="btn btn-primary">Add To Cart</a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>

<jsp:include page="component/script.jsp"/>
</body>
</html>