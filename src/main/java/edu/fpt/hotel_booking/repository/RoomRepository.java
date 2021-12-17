package edu.fpt.hotel_booking.repository;

import edu.fpt.hotel_booking.entity.Room;
import edu.fpt.hotel_booking.util.JpaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.lang.invoke.MethodHandles;
import java.util.List;

public class RoomRepository {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private static final RoomRepository INSTANCE = new RoomRepository();

    private RoomRepository() {
    }

    ;

    public static RoomRepository getInstance() {
        return INSTANCE;
    }

    public Room findById(long roomId) {
        EntityManager em = null;
        Room result = null;

        try {
            em = JpaUtil.getEntityManager();
            result = em.find(Room.class, roomId);
        } catch (NoResultException e) {
            //  do nothing
        } catch (Exception e) {
            logger.error("Finding room by id failed", e);
        } finally {
            em.close();
        }
        return result;
    }

    public List<Room> search(int amount, long hotelId) {
        EntityManager em = null;

        List<Room> result = null;
        try {
            em = JpaUtil.getEntityManager();

            Query query;
            if (hotelId < 0) {
                query = em.createQuery("SELECT r FROM Room r WHERE r.quantity > ?1", Room.class);
            } else {
                query = em.createQuery("SELECT r FROM Room r WHERE r.quantity > ?1 AND r.hotel.id = ?2", Room.class)
                        .setParameter(2, hotelId);
            }
            query.setParameter(1, amount);

            result = query.getResultList();

        } catch (NoResultException e) {
            //  do nothing
        } catch (Exception e) {
            logger.error("Searching rooms failed", e);
        } finally {
            em.close();
        }
        return result;
    }
}
