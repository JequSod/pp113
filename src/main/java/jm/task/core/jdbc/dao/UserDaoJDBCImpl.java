package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.execute("CREATE TABLE if not exists users (id bigint AUTO_INCREMENT PRIMARY KEY,name varchar (60),lastName varchar (60), age int);");
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.execute("DROP TABLE if exists users");
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate(
                    "insert  into  users(name, lastName, age) " +
                            "values ('" + name + "', '" + lastName + "', " + age + ")");
            System.out.println("User с именем — " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.execute("DELETE from users WHERE id IN(" + id + ")");
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Statement statement = Util.getConnection().createStatement()) {
            ResultSet results = statement.executeQuery("SELECT*FROM users");
            while (results.next()) {
                User user = new User();
                user.setId(results.getLong("id"));
                user.setName(results.getString("name"));
                user.setLastName(results.getString("lastName"));
                user.setAge(results.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.getStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.execute("TRUNCATE users");
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }
}
