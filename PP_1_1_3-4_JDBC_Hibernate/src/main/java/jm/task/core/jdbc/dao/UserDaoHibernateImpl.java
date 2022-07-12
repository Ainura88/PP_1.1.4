package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;


public class UserDaoHibernateImpl implements UserDao {
    Transaction transaction;
    Session session;


    @Override
    public void createUsersTable() {
        session.createSQLQuery("CREATE TABLE IF NOT EXISTS user " +
                    "(user_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(64) NOT NULL, last_Name VARCHAR(64) NOT NULL, " +
                    "age TINYINT NOT NULL)").executeUpdate();
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction.commit();
            if (transaction != null) {
                transaction.rollback();
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.createSQLQuery("DROP TABLE IF EXISTS user").executeUpdate();
            transaction.commit();
            if (transaction != null) {
                transaction.rollback();
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.save(new User(name, lastName, age));
            transaction.commit();
            if (transaction != null) {
                transaction.rollback();
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        System.out.println("User с именем – " + name + " добавлен в базу данных");
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.delete(session.get(User.class, id));
            transaction.commit();
            if (transaction != null) {
                transaction.rollback();
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = (List<User>)  Util.getSessionFactory().openSession().createQuery("From User").list();
        return userList; // нет транзакций, согласно ACID
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.createNativeQuery("TRUNCATE TABLE user").executeUpdate();
            transaction.commit();
            if (transaction != null) {
                transaction.rollback();
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
}
