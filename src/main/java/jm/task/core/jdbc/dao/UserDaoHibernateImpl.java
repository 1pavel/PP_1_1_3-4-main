package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private Session session = Util.getSessionFactory().openSession();
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String create = "CREATE TABLE IF NOT EXISTS users" +
                "(id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(45) NOT NULL, lastName VARCHAR(45) NOT NULL , age INT(3));";
        session.beginTransaction();
        Query query = session.createSQLQuery(create).addEntity(User.class);
        query.executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public void dropUsersTable() {
        String drop = "drop table if exists users";
        session.beginTransaction();
        Query q = session.createSQLQuery(drop).addEntity(User.class);
        q.executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        session.beginTransaction();
        session.save(new User(name, lastName, age));
        session.getTransaction().commit();
    }

    @Override
    public void removeUserById(long id) {
        session.beginTransaction();
        User user = session.find(User.class,id);
        if(user != null) {
        session.remove(user);
        }
        session.getTransaction().commit();
    }

    @Override
    public List<User> getAllUsers() {
        return session.createQuery("from User", User.class).list();
    }

    @Override
    public void cleanUsersTable() {
        String trun = "TRUNCATE TABLE users";
        session.beginTransaction();
        Query q = session.createSQLQuery(trun);
        q.executeUpdate();
        session.getTransaction().commit();
    }
}
