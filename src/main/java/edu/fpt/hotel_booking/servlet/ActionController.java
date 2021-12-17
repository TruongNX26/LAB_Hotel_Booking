package edu.fpt.hotel_booking.servlet;

import edu.fpt.hotel_booking.constant.PathConstant;
import edu.fpt.hotel_booking.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/actions/*")
public class ActionController extends HttpServlet {

    public static final String REGISTER = "/register";
    public static final String LOGIN = "/login";
    public static final String LOGOUT = "/logout";
    public static final String CART = "/cart";
    public static final String BOOKING = "/booking";
    public static final String HISTORY = "/history";
    public static final String CONFIRM = "/confirm";
    public static final String DELETE_BOOKING = "/delete-booking";
    public static final String RATING = "/rating";

    private static final String PERM_ANY = "ANY";
    private static final String PERM_CUSTOMER = "CUSTOMER";
//    private static final String PERM_ADMIN = "ADMIN";

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        String action = uri.substring(uri.lastIndexOf('/'));

        String permission = PERM_ANY;
        String destination = null;
        switch (action) {
            case REGISTER:
                destination = PathConstant.REGISTER_SERVLET;
                break;
            case LOGIN:
                destination = PathConstant.LOGIN_SERVLET;
                break;
            case LOGOUT:
                destination = PathConstant.LOGOUT_SERVLET;
                break;
            case CART:
                permission = PERM_CUSTOMER;
                destination = PathConstant.CART_SERVLET;
                break;
            case BOOKING:
                permission = PERM_CUSTOMER;
                destination = PathConstant.BOOKING_SERVLET;
                break;
            case HISTORY:
                permission = PERM_CUSTOMER;
                destination = PathConstant.HISTORY_SERVLET;
                break;
            case CONFIRM:
                destination = PathConstant.CONFIRM_SERVLET;
                break;
            case DELETE_BOOKING:
                permission = PERM_CUSTOMER;
                destination = PathConstant.DELETE_BOOKING_SERVLET;
                break;
            case RATING:
                permission = PERM_CUSTOMER;
                destination = PathConstant.RATING_SERVLET;
                break;
        }

        boolean isRedirect = false;
        HttpSession session = request.getSession();
        User user = session == null ? null : (User) session.getAttribute(LoginServlet.ATR_SESSION_USER);
        if (PERM_CUSTOMER.equals(permission)) {
            if (user == null || !user.getRole().equals(User.Role.CUSTOMER)) {
                destination = PathConstant.PREFIX_PAGES + PageController.LOGIN;
                isRedirect = true;
            }
        }

        if (destination != null) {
            if (isRedirect) {
                response.sendRedirect(request.getContextPath() + destination);
            } else {
                request.getRequestDispatcher(destination).forward(request, response);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Action not found");
        }
    }
}
