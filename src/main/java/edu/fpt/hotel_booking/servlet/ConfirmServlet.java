package edu.fpt.hotel_booking.servlet;

import edu.fpt.hotel_booking.constant.PathConstant;
import edu.fpt.hotel_booking.repository.BookingRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/confirm-servlet")
public class ConfirmServlet extends HttpServlet {

    public static final String PARAM_BOOKING = "booking_id";
    public static final String PARAM_CONFIRM = "confirm_code";
    private BookingRepository bookingRepository;

    @Override
    public void init() throws ServletException {
        super.init();
        bookingRepository = BookingRepository.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int bookingId = 0;
        int confirmCode = 0;

        try {
            bookingId = Integer.parseInt(request.getParameter(PARAM_BOOKING));
        } catch (Exception e) { /* do nothing */ }

        try {
            confirmCode = Integer.parseInt(request.getParameter(PARAM_CONFIRM));
        } catch (Exception e) { /* do nothing */ }

        if (bookingId >= 0 && confirmCode >= 0) {
            if (confirmCode == bookingRepository.getConfirmCode(bookingId)) {
                bookingRepository.activate(bookingId);
            }
        }

        String destination = PathConstant.PREFIX_PAGES + PageController.HISTORY;
        response.sendRedirect(request.getContextPath() + destination);
    }
}
