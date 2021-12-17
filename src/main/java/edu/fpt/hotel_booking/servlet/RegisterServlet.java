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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/register-servlet")
public class RegisterServlet extends HttpServlet {

    public static final String PARAM_EMAIL = "email";
    public static final String PARAM_PHONE = "phone";
    public static final String PARAM_NAME = "name";
    public static final String PARAM_ADDRESS = "address";
    public static final String PARAM_PASSWORD = "password";
    public static final String PARAM_CONFIRM_PASSWORD = "confirm_password";

    private static final String SUCCESS = PathConstant.LOGIN_PAGE;
    private static final String FAILED = PathConstant.REGISTER_PAGE;

    private UserRepository userRepository;

    @Override
    public void init() throws ServletException {
        super.init();
        userRepository = UserRepository.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String destination;

        String email = request.getParameter(PARAM_EMAIL);
        String phone = request.getParameter(PARAM_PHONE);
        String name = request.getParameter(PARAM_NAME);
        String address = request.getParameter(PARAM_ADDRESS);
        String password = request.getParameter(PARAM_PASSWORD);
        String confirmPassword = request.getParameter(PARAM_CONFIRM_PASSWORD);

        User user = User.builder()
                .email(email)
                .password(password)
                .phone(phone)
                .name(name)
                .address(address)
                .role(User.Role.CUSTOMER)
                .active(true)
                .creationDate(new Date())
                .build();

        Map<String, String> errors = validate(user);
        if (!password.equals(confirmPassword)) errors.put(PARAM_CONFIRM_PASSWORD, "Confirm password not match");

        if (errors.isEmpty()) {
            user.setPassword(MyUtil.encode(user.getPassword()));
            boolean isSuccess = userRepository.create(user);
            if (isSuccess) {
                request.setAttribute("STATUS", "CREATED");
                destination = SUCCESS;
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unexpected error");
                return;
            }
        } else {
            request.setAttribute("ERRORS", errors);
            destination = FAILED;
        }

        request.getRequestDispatcher(destination).forward(request, response);
    }

    private Map<String, String> validate(User user) {
        Map<String, String> errors = new HashMap<>();

        if (user.getEmail().isEmpty()) {
            errors.put(PARAM_EMAIL, "Email must not empty");
        } else if (userRepository.checkExistEmail(user.getEmail())) {
            errors.put(PARAM_EMAIL, "Email already existed");
        }

        if (user.getPhone().isEmpty()) {
            errors.put(PARAM_PHONE, "Phone number must not empty");
        }

        if (user.getName().length() < 3 || user.getName().length() > 30) {
            errors.put(PARAM_NAME, "Full name length 3-30");
        }

        if (user.getAddress().length() > 200) {
            errors.put(PARAM_NAME, "Address length 200 max");
        }

        if (user.getPassword().length() < 6 || user.getPassword().length() > 20) {
            errors.put(PARAM_PASSWORD, "Password length 6-20");
        }

        return errors;
    }
}
