package edu.fpt.hotel_booking.servlet;

import edu.fpt.hotel_booking.cart.Cart;
import edu.fpt.hotel_booking.cart.CartItem;
import edu.fpt.hotel_booking.constant.PathConstant;
import edu.fpt.hotel_booking.entity.Booking;
import edu.fpt.hotel_booking.entity.BookingDetail;
import edu.fpt.hotel_booking.entity.Discount;
import edu.fpt.hotel_booking.entity.User;
import edu.fpt.hotel_booking.repository.BookingRepository;
import edu.fpt.hotel_booking.repository.DiscountRepository;
import edu.fpt.hotel_booking.util.MailUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.time.Duration;
import java.util.*;

@WebServlet("/booking-servlet")
public class BookingServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    public static final String PARAM_DISCOUNT = "discount";
    private DiscountRepository discountRepository;
    private BookingRepository bookingRepository;

    @Override
    public void init() throws ServletException {
        super.init();
        discountRepository = DiscountRepository.getInstance();
        bookingRepository = BookingRepository.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String destination;

        int discountCode = 0;
        try {
            discountCode = Integer.parseInt(request.getParameter(PARAM_DISCOUNT));
        } catch (Exception e) { /* do nothing */ }

        Discount discount = null;
        if (discountCode > 0) {
            discount = discountRepository.findById(discountCode);
            if (discount != null) {
                if (discount.getExpirationDate().before(new Date())) {
                    destination = PathConstant.PREFIX_PAGES + PageController.CART;
                    request.setAttribute("ERROR", "Discount expired!");
                    request.getRequestDispatcher(destination).forward(request, response);
                    return;
                }
            } else {
                destination = PathConstant.PREFIX_PAGES + PageController.CART;
                request.setAttribute("ERROR", "Discount code not found");
                request.getRequestDispatcher(destination).forward(request, response);
                return;
            }
        }

        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute(CartServlet.ATR_CART);
        User user = (User) session.getAttribute(LoginServlet.ATR_SESSION_USER);

        Booking booking = Booking.builder()
                .bookingDate(new Date())
                .user(user)
                .discount(discount == null ? 0 : discount.getPercentage())
                .confirm(createConfirmCode())
                .status(Booking.Status.CONFIRMING)
                .total(cart.calculateTotal())
                .build();

        List<BookingDetail> details = createBookingDetails(cart, booking);

        Booking savedBooking = bookingRepository.book(booking, details);
        boolean isSuccess = savedBooking != null;
        if (isSuccess) {
            String link = "https://localhost:8443" +
                    request.getContextPath() +
                    PathConstant.PREFIX_ACTION +
                    ActionController.CONFIRM +
                    "?booking_id=" + savedBooking.getId() +
                    "&confirm_code=" + savedBooking.getConfirm();

            sendConfirmEmail(user.getEmail(), "Click to confirm booking: " + link);
            request.getSession().removeAttribute(CartServlet.ATR_CART);
            destination = PathConstant.PREFIX_PAGES + PageController.HISTORY;
            response.sendRedirect(request.getContextPath() + destination);
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unexpected booking error");
        }
    }

    private List<BookingDetail> createBookingDetails(Cart cart, Booking booking) {
        List<BookingDetail> result = new ArrayList<>();

        Map<Long, CartItem> items = cart.getItems();
        Collection<CartItem> values = items.values();

        Date current = new Date();
        for (CartItem item : values) {
            result.add(BookingDetail.builder()
                    .checkinDate(current)
                    .checkoutDate(new Date(current.getTime() + Duration.ofDays(1).toMillis()))
                    .price(item.getRoom().getRoomType().getPrice())
                    .amount(item.getAmount())
                    .room(item.getRoom())
                    .booking(booking)
                    .build());
        }

        return result;
    }

    private void sendConfirmEmail(String receiver, String content) {
        new Thread(() -> {
            try {
                MailUtil.sendMail(receiver, content);
            } catch (MessagingException e) {
                logger.error("Sending email failed", e);
            }
        }).start();
    }

    private int createConfirmCode() {
        return (int) (Math.random() * 8999 + 1000);
    }
}
