package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sessionFactory = Util.getSessionFactory();
    private Transaction transaction;
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String create = "CREATE TABLE IF NOT EXISTS users" +
                "(id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(45) NOT NULL, lastName VARCHAR(45) NOT NULL , age INT(3));";

        try (Session session = sessionFactory.openSession()) {
        transaction = session.beginTransaction();
        Query query = session.createSQLQuery(create).addEntity(User.class);
        query.executeUpdate();
        transaction.commit();
        } catch(Exception e) {
            transaction.rollback();
        }
    }

    @Override
    public void dropUsersTable() {
        String drop = "drop table if exists users";
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Query q = session.createSQLQuery(drop).addEntity(User.class);
            q.executeUpdate();
            transaction.commit();
    } catch(Exception e) {
            transaction.rollback();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
        } catch(Exception e) {
            transaction.rollback();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = session.find(User.class,id);
            if(user != null) {
                session.remove(user);
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            List<User> userList = session.createQuery("from User", User.class).list();
            transaction.commit();
            return userList;
        } catch(Exception e) {
            return null;
        }
    }

    @Override
    public void cleanUsersTable() {
        String trun = "TRUNCATE TABLE users";
        try (Session session = sessionFactory.openSession()) {
         transaction = session.beginTransaction();
         Query q = session.createSQLQuery(trun);
         q.executeUpdate();
         transaction.commit();
    } catch (Exception e) {
        transaction.rollback();
        }
    }
}
