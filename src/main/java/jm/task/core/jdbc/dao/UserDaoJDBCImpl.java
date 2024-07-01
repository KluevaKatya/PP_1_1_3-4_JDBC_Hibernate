package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users " +
                    "(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(25), last_name VARCHAR(25), age TINYINT)");
        } catch (SQLException e) {
            System.out.println("Ошибка при создании таблицы");
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS users");
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении таблицы");
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("INSERT INTO users (name, last_name, age) VALUES(?,?,?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении User");
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users WHERE id = ?");
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении Users");
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();

        try(Statement statement = Util.getConnection().createStatement()){
        ResultSet resultSet = statement.executeQuery("SELECT * FROM users");

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setAge(resultSet.getByte("age"));

                list.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении Users");
        }
        return list;
    }

    public void cleanUsersTable() {
        try(Connection connection = Util.getConnection()){
            Statement statement = connection.createStatement();
            statement.executeUpdate("TRUNCATE users");
        } catch (SQLException e) {
            System.out.println("Ошибка при очистке таблицы");
        }
    }
}
