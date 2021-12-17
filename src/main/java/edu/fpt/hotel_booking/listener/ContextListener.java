package edu.fpt.hotel_booking.listener;

import edu.fpt.hotel_booking.util.JpaUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Web App Started");
        JpaUtil.getEntityManager();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Web App Destroyed");
        JpaUtil.getEmf().close();
    }
}