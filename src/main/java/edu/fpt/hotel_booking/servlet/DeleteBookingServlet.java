package edu.fpt.hotel_booking.servlet;

import edu.fpt.hotel_booking.constant.PathConstant;
import edu.fpt.hotel_booking.repository.BookingRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/delete-booking-servlet")
public class DeleteBookingServlet extends HttpServlet {

    private static final String PARAM_BOOKING = "booking_id";
    private BookingRepository bookingRepository;

    @Override
    public void init() throws ServletException {
        super.init();
        bookingRepository = BookingRepository.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long bookingId = Long.parseLong(request.getParameter(PARAM_BOOKING));
        bookingRepository.deactivate(bookingId);
        String destination = PathConstant.PREFIX_PAGES + PageController.HISTORY;
        response.sendRedirect(request.getContextPath() + destination);
    }
}