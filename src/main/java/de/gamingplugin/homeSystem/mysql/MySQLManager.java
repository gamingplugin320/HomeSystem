package de.gamingplugin.homeSystem.mysql;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySQLManager {

    private Connection connection;

    String url,
            username,
            password;


    public MySQLManager(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public void connect() throws SQLException {
        connection = DriverManager.getConnection(url, username, password);
    }

    public void disconnect() throws SQLException {

        if (this.connection != null) {
            this.connection.close();
        }
    }

    public List<Map<String, Object>> executeQuery(String sql, Object... params) throws SQLException {
        List<Map<String, Object>> results = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            statement.setObject(i + 1, params[i]);
        }

        ResultSet resultSet = statement.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        while (resultSet.next()) {
            Map<String, Object> row = new HashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                row.put(metaData.getColumnLabel(i), resultSet.getObject(i));
            }
            results.add(row);
        }

        resultSet.close();
        statement.close();

        return results;
    }

    public int executeUpdate(String sql, Object... params) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            return statement.executeUpdate();
        }
    }


    public void createTable() throws SQLException {

        final String homes = "CREATE TABLE IF NOT EXISTS homes " +
                "(id INT AUTO_INCREMENT PRIMARY KEY, " +
                "uuid VARCHAR(36) NOT NULL, " +
                "home_name VARCHAR(32) NOT NULL, " +
                "UNIQUE KEY unique_name (uuid, home_name), " +
                "world_name VARCHAR(64) NOT NULL, " +
                "x_position DOUBLE NOT NULL, " +
                "y_position DOUBLE NOT NULL, " +
                "z_position DOUBLE NOT NULL, " +
                "yaw_position FLOAT NOT NULL, " +
                "pitch_position FLOAT NOT NULL)";

        executeUpdate(homes);
    }

}
