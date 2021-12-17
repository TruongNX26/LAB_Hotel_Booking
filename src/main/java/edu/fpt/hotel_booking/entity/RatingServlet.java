package edu.fpt.hotel_booking.entity;

import edu.fpt.hotel_booking.constant.PathConstant;
import edu.fpt.hotel_booking.repository.BookingRepository;
import edu.fpt.hotel_booking.servlet.PageController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/rating-servlet")
public class RatingServlet extends HttpServlet {

    private static final String PARAM_BOOKING = "booking_id";
    private static final String PARAM_POINT = "point";

    private BookingRepository bookingRepository;

    @Override
    public void init() throws ServletException {
        super.init();
        bookingRepository = BookingRepository.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long bookingId = Long.parseLong(request.getParameter(PARAM_BOOKING));
        int point = Integer.parseInt(request.getParameter(PARAM_POINT));

        if(point >= 1 && point <= 10) {
            bookingRepository.rating(bookingId, point);
        }

        String destination = PathConstant.PREFIX_PAGES + PageController.HISTORY;
        response.sendRedirect(request.getContextPath() + destination);
    }
}
