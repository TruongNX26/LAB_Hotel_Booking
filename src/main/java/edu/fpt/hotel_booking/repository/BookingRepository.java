package edu.fpt.hotel_booking.repository;

import edu.fpt.hotel_booking.entity.Booking;
import edu.fpt.hotel_booking.entity.BookingDetail;
import edu.fpt.hotel_booking.util.JpaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookingRepository {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private static final BookingRepository INSTANCE = new BookingRepository();

    private BookingRepository() {
    }

    public static BookingRepository getInstance() {
        return INSTANCE;
    }

    public void rating(long id, int point) {
        EntityManager em = null;
        try {
            em = JpaUtil.getEntityManager();
            em.getTransaction().begin();
            Booking booking = em.find(Booking.class, id);
            if (booking != null) {
                booking.setRating(point);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public void deactivate(long id) {
        EntityManager em = null;
        try {
            em = JpaUtil.getEntityManager();
            em.getTransaction().begin();
            Booking booking = em.find(Booking.class, id);
            if (booking != null) {
                booking.setStatus(Booking.Status.INACTIVE);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public Booking book(Booking booking, List<BookingDetail> details) {
        EntityManager em = null;
        try {
            em = JpaUtil.getEntityManager();
            em.getTransaction().begin();

            em.persist(booking);
            for (BookingDetail detail : details) {
                em.persist(detail);
                int newQty = detail.getRoom().getQuantity() - detail.getAmount();
                boolean isUpdated = em.createQuery("UPDATE Room SET quantity = ?1 WHERE id = ?2")
                        .setParameter(1, newQty)
                        .setParameter(2, detail.getRoom().getId())
                        .executeUpdate() > 0;
                if (!isUpdated) throw new RuntimeException("Updating room's quantity failed");
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            logger.error("Booking processing failed", e);
            em.getTransaction().rollback();
            booking = null;
        } finally {
            em.close();
        }

        return booking;
    }

    public List<Booking> search(Date bookingDate, String userEmail) {
        EntityManager em = null;
        List<Booking> result = new ArrayList<>();


        try {
            em = JpaUtil.getEntityManager();
            Query query;
            if(bookingDate == null) {
                query = em.createQuery("SELECT b FROM Booking b WHERE b.user.email = ?1 ORDER BY b.bookingDate DESC", Booking.class);
            } else {
                query = em.createQuery("SELECT b FROM Booking b WHERE b.user.email = ?1 AND b.bookingDate = ?2 ORDER BY b.bookingDate DESC", Booking.class);
                query.setParameter(2, bookingDate, TemporalType.DATE);
            }
            query.setParameter(1, userEmail);
            result = query.getResultList();

        } catch (NoResultException e) {
            // do nothing
        } catch (Exception e) {
            logger.error("Searching booking by date failed", e);
        } finally {
            em.close();
        }
        return result;
    }

    public int getConfirmCode(long bookingId) {
        EntityManager em = null;
        int result = -1;
        try {
            em = JpaUtil.getEntityManager();
            Query query = em.createQuery("SELECT b.confirm FROM Booking b WHERE b.id=?1")
                    .setParameter(1, bookingId);

            result = (int) query.getSingleResult();
        } catch (NoResultException e) {
            // do nothing
        } catch (Exception e) {
            logger.error("Getting confirm code failed", e);
        } finally {
            em.close();
        }

        return result;
    }

    public void activate(long bookingId) {
        EntityManager em = null;

        try {
            em = JpaUtil.getEntityManager();
            em.getTransaction().begin();
            Booking booking = em.find(Booking.class, bookingId);
            booking.setStatus(Booking.Status.ACTIVE);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            logger.error("Getting confirm code failed", e);
        } finally {
            em.close();
        }
    }
}
