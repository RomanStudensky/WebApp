package ru.webApp;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DBConnector {
    private static final String URL = "jdbc:postgresql://localhost:5432/EmployeeDepartment";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "12345";
    @Getter
    private static Connection connection;
    @Getter
    private static Statement statement;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            statement = connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
