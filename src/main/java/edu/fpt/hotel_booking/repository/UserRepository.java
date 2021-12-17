package edu.fpt.hotel_booking.repository;

import edu.fpt.hotel_booking.entity.User;
import edu.fpt.hotel_booking.util.JpaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.lang.invoke.MethodHandles;

public class UserRepository {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private static final UserRepository INSTANCE = new UserRepository();

    private UserRepository() {
    }

    ;

    public static UserRepository getInstance() {
        return INSTANCE;
    }

    public boolean create(User user) {
        boolean isSuccess = false;
        EntityManager em = null;
        try {
            em = JpaUtil.getEntityManager();
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();

            isSuccess = true;
        } catch (Exception e) {
            logger.error("Creating user failed", e);
        } finally {
            em.close();
        }
        return isSuccess;
    }

    public User findByEmailPassword(String email, String password) {
        User user = null;
        EntityManager em = null;
        try {
            em = JpaUtil.getEntityManager();

            user = em.createQuery("SELECT NEW User(phone, name, creationDate, address, role) FROM User WHERE active=true AND email=?1 AND password=?2", User.class)
                    .setParameter(1, email)
                    .setParameter(2, password)
                    .getSingleResult();

            user.setEmail(email);
            user.setActive(true);
        } catch (NoResultException e) {
            //  do nothing
        } catch (Exception e) {
            logger.error("Find user by email and password failed");
        } finally {
            em.close();
        }
        return user;
    }

    public boolean checkExistEmail(String email) {
        boolean isExist = false;
        EntityManager em = null;
        try {
            em = JpaUtil.getEntityManager();
            Query query = em.createQuery("SELECT email FROM User WHERE email = ?1");
            query.setParameter(1, email);
            query.getSingleResult();
            isExist = true;
        } catch (NoResultException e) {
            //  do nothing
        } catch (Exception e) {
            logger.error("Checking exist email failed", e);
        } finally {
            em.close();
        }
        return isExist;
    }

}
