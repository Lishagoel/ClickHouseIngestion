package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ClickHouseClient {

    private static final String URL = "jdbc:clickhouse://localhost:8123";
    private static final String USER = "default";
    private static final String PASSWORD = "";

    // Create a connection to the ClickHouse database
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Insert data into ClickHouse using a dynamic query
    public boolean insertData(int id, int price, String date, String propertyType, String town) {
        String query = "INSERT INTO uk_price_paid (id, price, date, property_type, town) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            // Set the parameters for the query
            statement.setInt(1, id);
            statement.setInt(2, price);
            statement.setString(3, date);
            statement.setString(4, propertyType);
            statement.setString(5, town);

            // Execute the insert statement
            statement.executeUpdate();
            return true; // If the insert was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // If an error occurred
        }
    }

    // Retrieve data from ClickHouse
    public List<String> getData(String town) {
        List<String> data = new ArrayList<>();
        String selectQuery = "SELECT * FROM uk_price_paid WHERE town = ?";
        
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(selectQuery)) {
            
            statement.setString(1, town);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                String record = "ID: " + resultSet.getInt("id") + ", Price: " + resultSet.getInt("price")
                        + ", Date: " + resultSet.getString("date") + ", Property Type: " + resultSet.getString("property_type")
                        + ", Town: " + resultSet.getString("town");
                data.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        System.out.println("Fetched Data from DB: " + data);  // Log the data
        return data;
    }
    

    // For debugging: get all data without filtering by town
    public List<String> getAllData() {
        List<String> data = new ArrayList<>();
        String selectQuery = "SELECT * FROM uk_price_paid";  // No WHERE clause for debugging
        
        System.out.println("Executing query: " + selectQuery);
        
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = statement.executeQuery()) {
            
            while (resultSet.next()) {
                String record = "ID: " + resultSet.getInt("id") + ", Price: " + resultSet.getInt("price")
                        + ", Date: " + resultSet.getString("date") + ", Property Type: " + resultSet.getString("property_type")
                        + ", Town: " + resultSet.getString("town");
                System.out.println("Fetched record: " + record);  // Debug print
                data.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        System.out.println("Total records fetched: " + data.size());
        return data;
    }
}
