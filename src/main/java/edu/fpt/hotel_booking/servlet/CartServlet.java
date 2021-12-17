package edu.fpt.hotel_booking.servlet;

import edu.fpt.hotel_booking.cart.Cart;
import edu.fpt.hotel_booking.cart.CartItem;
import edu.fpt.hotel_booking.constant.PathConstant;
import edu.fpt.hotel_booking.entity.Room;
import edu.fpt.hotel_booking.repository.RoomRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;

@WebServlet("/cart-servlet")
public class CartServlet extends HttpServlet {

    public static final String PARAM_ROOM = "room_id";
    public static final String PARAM_AMOUNT = "amount";

    public static final String SUCCESS = PathConstant.PREFIX_PAGES + PageController.CART;
    public static final String FAILED = PathConstant.PREFIX_PAGES + PageController.HOME;
    public static final String ATR_CART = "SESSION_CART";

    private RoomRepository roomRepository;

    @Override
    public void init() throws ServletException {
        super.init();
        roomRepository = RoomRepository.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String destination;

        int roomId = -1;
        try {
            roomId = Integer.parseInt(request.getParameter(PARAM_ROOM));
        } catch (Exception e) { /* do nothing */ }

        if (roomId >= 0) {
            HttpSession session = request.getSession(true);
            Cart cart = (Cart) session.getAttribute(ATR_CART);
            if (cart == null) {
                cart = new Cart();
                session.setAttribute(ATR_CART, cart);
            }

            CartItem item = createCartItem(roomId);
            if (item != null) {
                cart.add(item);
                destination = SUCCESS;
            } else {
                destination = FAILED;
            }
        } else {
            destination = FAILED;
        }
        response.sendRedirect(request.getContextPath() + destination);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int roomId = -1;
        int amount = -1;

        try {
            roomId = Integer.parseInt(request.getParameter(PARAM_ROOM));
        } catch (Exception e) { /* do nothing */ }

        try {
            amount = Integer.parseInt(request.getParameter(PARAM_AMOUNT));
        } catch (Exception e) { /* do nothing */ }

        boolean isRedirect = false;
        HttpSession session = request.getSession();
        Cart cart = session == null ? null : (Cart) session.getAttribute(ATR_CART);
        if (cart != null) {
            if (amount == 0)
                cart.remove(roomId);
            else if (amount > 0) {
                Room room = roomRepository.findById(roomId);
                int available = room.getQuantity();
                if (amount <= available) {
                    cart.updateAmount(roomId, amount);
                    isRedirect = true;
                } else {
                    request.setAttribute("ERROR", "Update failed, max room quantity = " + room.getQuantity());
                }
            }
        }

        String destination = PathConstant.PREFIX_PAGES + PageController.CART;
        if (isRedirect) {
            response.sendRedirect(request.getContextPath() + destination);
        } else {
            request.getRequestDispatcher(destination).forward(request, response);
        }
    }

    private CartItem createCartItem(long roomId) {
        Room room = roomRepository.findById(roomId);
        Date currentDate = new Date();
        return room == null ? null : CartItem.builder()
                .room(room)
                .checkinDate(currentDate)
                .checkoutDate(new Date(currentDate.getTime() + Duration.ofDays(1).toMillis()))
                .amount(1)
                .build();
    }
}