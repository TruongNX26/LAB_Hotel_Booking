package edu.fpt.hotel_booking.servlet;

import edu.fpt.hotel_booking.constant.PathConstant;
import edu.fpt.hotel_booking.entity.Hotel;
import edu.fpt.hotel_booking.repository.HotelRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/pages/*")
public class PageController extends HttpServlet {

    public static final String REGISTER = "/register";
    public static final String LOGIN = "/login";
    public static final String HOME = "/home";
    public static final String CART = "/cart";
    public static final String HISTORY = "/history";

    public static final String ATR_HOTELS = "HOTELS";

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        String page = uri.substring(uri.lastIndexOf('/'));

        loadSharedData(request);
        String destination = null;
        switch (page) {
            case REGISTER:
                destination = PathConstant.REGISTER_PAGE;
                break;
            case LOGIN:
                destination = PathConstant.LOGIN_PAGE;
                break;
            case HOME:
                destination = PathConstant.SEARCH_SERVLET;
                break;
            case CART:
                destination = PathConstant.CART_PAGE;
                break;
            case HISTORY:
                destination = PathConstant.HISTORY_SERVLET;
                break;
        }

        if (destination != null) {
            request.getRequestDispatcher(destination).forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Page not found");
        }
    }

    private void loadSharedData(HttpServletRequest request) {
        List<Hotel> hotels = HotelRepository.getInstance().findAll();
        request.setAttribute(ATR_HOTELS, hotels);
    }
}