package edu.fpt.hotel_booking.servlet;

import edu.fpt.hotel_booking.constant.PathConstant;
import edu.fpt.hotel_booking.entity.Room;
import edu.fpt.hotel_booking.repository.RoomRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/search-servlet")
public class SearchServlet extends HttpServlet {

    public static final String PARAM_HOTEL = "hotel_id";
    public static final String PARAM_AMOUNT = "amount";

    private RoomRepository roomRepository;

    @Override
    public void init() throws ServletException {
        super.init();
        roomRepository = RoomRepository.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int hotel_id = -1;
        int amount = 0;

        try {
            hotel_id = Integer.parseInt(request.getParameter(PARAM_HOTEL));
        } catch (Exception e) { /* do nothing */ }

        try {
            amount = Integer.parseInt(request.getParameter(PARAM_AMOUNT));
        } catch (Exception e) { /* do nothing */ }

        List<Room> rooms = roomRepository.search(amount, hotel_id);
        request.setAttribute("ROOMS", rooms);

        String destination = PathConstant.HOME_PAGE;
        request.getRequestDispatcher(destination).forward(request, response);
    }
}
