package edu.fpt.hotel_booking.servlet;

import edu.fpt.hotel_booking.constant.PathConstant;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout-servlet")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session != null) {
            session.invalidate();
        }

        String destination = request.getContextPath() + PathConstant.PREFIX_PAGES + PageController.HOME;
        response.sendRedirect(destination);
    }
}
