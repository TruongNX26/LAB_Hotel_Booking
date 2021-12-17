package edu.fpt.hotel_booking.servlet;

import edu.fpt.hotel_booking.constant.PathConstant;
import edu.fpt.hotel_booking.entity.User;
import edu.fpt.hotel_booking.repository.UserRepository;
import edu.fpt.hotel_booking.util.MyUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login-servlet")
public class LoginServlet extends HttpServlet {

    public static final String PARAM_EMAIL = "email";
    public static final String PARAM_PASSWORD = "password";
    public static final String ATR_SESSION_USER = "SESSION_USER";

    private static final String SUCCESS = PathConstant.PREFIX_PAGES + PageController.HOME;
    private static final String FAILED = PathConstant.LOGIN_PAGE;

    private UserRepository userRepository;

    @Override
    public void init() throws ServletException {
        super.init();
        userRepository = UserRepository.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String destination;

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        boolean isRedirect = false;
        User user = userRepository.findByEmailPassword(email, MyUtil.encode(password));
        if (user != null) {
            isRedirect = true;
            request.getSession(true).setAttribute(ATR_SESSION_USER, user);
            destination = SUCCESS;
        } else {
            request.setAttribute("STATUS", "FAILED");
            destination = FAILED;
        }

        if (isRedirect) {
            response.sendRedirect(request.getContextPath() + destination);
        } else {
            request.getRequestDispatcher(destination).forward(request, response);
        }
    }
}
