package edu.fpt.hotel_booking.repository;

import edu.fpt.hotel_booking.entity.Hotel;
import edu.fpt.hotel_booking.util.JpaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.lang.invoke.MethodHandles;
import java.util.List;

public class HotelRepository {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private static final HotelRepository INSTANCE = new HotelRepository();

    private HotelRepository() {
    }

    ;

    public static HotelRepository getInstance() {
        return INSTANCE;
    }

    public List<Hotel> findAll() {
        EntityManager em = null;
        List<Hotel> result = null;

        try {
            em = JpaUtil.getEntityManager();
            result = em.createQuery("SELECT h FROM Hotel h", Hotel.class).getResultList();
        } catch (NoResultException e) {
            //  do nothing
        } catch (Exception e) {
            logger.error("Fetching hotels failed");
        } finally {
            em.close();
        }

        return result;
    }
}
