package edu.fpt.hotel_booking.filter;

import edu.fpt.hotel_booking.constant.PathConstant;
import edu.fpt.hotel_booking.servlet.PageController;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter("/*")
public class MainFilter implements Filter {

    private final List<String> allowTypes = Arrays.asList(".css", ".js", ".jpg");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpRequest.setCharacterEncoding("UTF-8");

        String uri = httpRequest.getRequestURI();
        String path = uri.substring(httpRequest.getContextPath().length());

        if(path.matches("/")) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + PathConstant.PREFIX_PAGES + PageController.HOME);
            return;
        }

        if (path.startsWith(PathConstant.PREFIX_ACTION) || path.startsWith(PathConstant.PREFIX_PAGES) || checkResourcePath(path)) {
            chain.doFilter(request, response);
        } else {
            httpResponse.sendError(HttpServletResponse.SC_NOT_FOUND, "Path or Resource not found");
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    private boolean checkResourcePath(String path) {
        path = path.toLowerCase();
        for (String type : allowTypes) {
            if (path.endsWith(type.toLowerCase())) return true;
        }
        return false;
    }
}
