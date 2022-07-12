package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public void createUsersTable() {
        try {
            Connection connection = Util.getConnection();
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS user(" +
                    "user_id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(64)," +
                    "last_name VARCHAR(64)," +
                    "age TINYINT" +
                    ");"
            );
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try {
            Connection connection = Util.getConnection();
            Statement statement = connection.createStatement();
            statement.execute("DROP TABLE IF EXISTS user");
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        Connection connection = Util.getConnection();

        try(PreparedStatement statement = connection.prepareStatement("INSERT INTO user (name, last_name, age) VALUES  (?, ?, ?);")) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.execute();
            statement.close();
            connection.commit();
            connection.close();
            System.out.println("User with name " + name + " was added.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try {
            Connection connection = Util.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM user WHERE user_id=?;");
            statement.setLong(1, id);
            statement.execute();
            statement.close();
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try {
            Connection connection = Util.getConnection();
            Statement statement = connection.createStatement();

            ResultSet set = statement.executeQuery("SELECT * FROM user");
            while (set.next()) {
                users.add(new User (
                        set.getString("name"),
                        set.getString("last_name"),
                        set.getByte("age"),
                        set.getLong("user_id")
                ));
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try {
            Connection connection = Util.getConnection();
            Statement statement = connection.createStatement();
            statement.execute("TRUNCATE TABLE user");

            statement.close();;
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}