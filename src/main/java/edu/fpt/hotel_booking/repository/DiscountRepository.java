package edu.fpt.hotel_booking.repository;

import edu.fpt.hotel_booking.entity.Discount;
import edu.fpt.hotel_booking.util.JpaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.lang.invoke.MethodHandles;

public class DiscountRepository {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private static DiscountRepository INSTANCE = new DiscountRepository();

    private DiscountRepository() {
    }

    public static DiscountRepository getInstance() {
        return INSTANCE;
    }

    public Discount findById(long code) {
        Discount result = null;
        EntityManager em = null;
        try {
            em = JpaUtil.getEntityManager();
            result = em.find(Discount.class, code);
        } catch (NoResultException e) {
            //  do nothing
        } catch (Exception e) {
            logger.error("Finding discount failed", e);
        } finally {
            em.close();
        }
        return result;
    }
}