package edu.fpt.hotel_booking.servlet;

import edu.fpt.hotel_booking.constant.PathConstant;
import edu.fpt.hotel_booking.entity.Booking;
import edu.fpt.hotel_booking.entity.User;
import edu.fpt.hotel_booking.repository.BookingRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/history-servlet")
public class HistoryServlet extends HttpServlet {

    public static final String PARAM_HOSTEL = "hostel_name";
    public static final String PARAM_DATE = "booking_date";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private BookingRepository bookingRepository;

    @Override
    public void init() throws ServletException {
        super.init();
        bookingRepository = BookingRepository.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Date bookingDate = null;
        try {
            bookingDate = dateFormat.parse(request.getParameter(PARAM_DATE));
        } catch (Exception e) { /* do nothing */ }

        User user = (User) request.getSession().getAttribute(LoginServlet.ATR_SESSION_USER);
        List<Booking> bookings = bookingRepository.search(bookingDate, user.getEmail());
        request.setAttribute("BOOKINGS", bookings);

        String destination = PathConstant.HISTORY_PAGE;
        request.getRequestDispatcher(destination).forward(request, response);
    }

}
