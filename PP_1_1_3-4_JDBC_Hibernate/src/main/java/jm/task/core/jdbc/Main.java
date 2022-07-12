package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;

public class Main {
    private final static UserService userService = new UserServiceImpl();
    public static void main(String[] args) {
        // реализуйте алгоритм здесь

        Util.getConnection();
        userService.createUsersTable();

        userService.saveUser("Мария", "Кузнецова", (byte) 37);
        userService.saveUser("Александр", "Федоров", (byte) 52);
        userService.saveUser("Иван", "Иванов", (byte) 30);
        userService.saveUser("Зоя", "Смирнова", (byte) 40);

        userService.removeUserById(1L);

        userService.getAllUsers();

        userService.cleanUsersTable();

        userService.dropUsersTable();

        try {
            Util.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}