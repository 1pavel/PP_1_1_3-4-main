package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;


public class UserDaoJDBCImpl implements UserDao {
   private Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String create = "CREATE TABLE IF NOT EXISTS users " +
                "( id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(45) NOT NULL, lastName VARCHAR(45) NOT NULL , age INT(3)); ";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(create);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
           throw new RuntimeException(e);
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement  pstatement = connection.prepareStatement
                ("INSERT INTO users (name, lastName, age) VALUES ( ?, ?, ?)") ){
            pstatement.setString(1, name);
            pstatement.setString(2,lastName);
            pstatement.setByte(3,age);
            pstatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }

    public void removeUserById(long id) {
        try ( PreparedStatement pstatement = connection.prepareStatement("DELETE FROM users WHERE id = ?" )) {
            pstatement.setLong(1,id);
            pstatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public List<User> getAllUsers() {
        ArrayList<User> list = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery("SELECT * from users");
            while (result.next()) {
                User user = new User();
                user.setId(result.getLong(1));
                user.setName(result.getString(2));
                user.setLastName(result.getString(3));
                user.setAge(result.getByte(4));
                list.add(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE users");
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
