package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LocalDatabase {

    private Connection conn;

    public LocalDatabase() {
        // Connect to SQLite database (creates if not exists)
        connect();
    }

    public void connect() {
        try {
            // SQLite connection string
            String url = "jdbc:sqlite:./bin/local_db.sqlite"; // Relative path to bin folder

            // Create a connection to the database (creates if not exists)
            conn = DriverManager.getConnection(url);

            System.out.println("Connected to SQLite database.");

        } catch (SQLException e) {
            System.err.println("SQLite Connection Error: " + e.getMessage());
        }
    }

    public void disconnect() {
        try {
            if (conn != null) {
                conn.close();
                System.out.println("Disconnected from SQLite database.");
            }
        } catch (SQLException e) {
            System.err.println("Error disconnecting from SQLite database: " + e.getMessage());
        }
    }

    public void createTable(String tableName, String columns) {
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (" + columns + ")";

        try (Statement stmt = conn.createStatement()) {
            // Create table
            stmt.execute(sql);
            System.out.println("Table '" + tableName + "' created successfully.");

        } catch (SQLException e) {
            System.err.println("Error creating table: " + e.getMessage());
        }
    }

    public void insert(String tableName, String columns, String values) {
        String sql = "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + values + ")";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Execute insert statement
            pstmt.executeUpdate();
            System.out.println("Data inserted into '" + tableName + "' successfully.");

        } catch (SQLException e) {
            System.err.println("Error inserting data: " + e.getMessage());
        }
    }

    public void update(String tableName, String setValues, String condition) {
        String sql = "UPDATE " + tableName + " SET " + setValues + " WHERE " + condition;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Execute update statement
            pstmt.executeUpdate();
            System.out.println("Data updated in '" + tableName + "' successfully.");

        } catch (SQLException e) {
            System.err.println("Error updating data: " + e.getMessage());
        }
    }

    public void delete(String tableName, String condition) {
        String sql = "DELETE FROM " + tableName + " WHERE " + condition;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Execute delete statement
            pstmt.executeUpdate();
            System.out.println("Data deleted from '" + tableName + "' successfully.");

        } catch (SQLException e) {
            System.err.println("Error deleting data: " + e.getMessage());
        }
    }

    public ResultSet select(String tableName, String columns, String condition) {
        String sql = "SELECT " + columns + " FROM " + tableName + " WHERE " + condition;

        try {
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(sql);

        } catch (SQLException e) {
            System.err.println("Error selecting data: " + e.getMessage());
        }

        return null;
    }

    public static void main(String[] args) {
        LocalDatabase db = new LocalDatabase();

        // Example usage:
        db.createTable("users", "id INTEGER PRIMARY KEY, name TEXT, email TEXT");
        db.insert("users", "name, email", "'Alice', 'alice@example.com'");
        db.update("users", "email = 'alice_updated@example.com'", "name = 'Alice'");
        // db.delete("users", "name = 'Alice'");
        ResultSet resultSet = db.select("users", "*", "1=1");

        // Example displaying result set (replace with actual usage)
        try {
            while (resultSet.next()) {
                System.out.println("User: " + resultSet.getString("name") + ", Email: " + resultSet.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        db.disconnect();
    }
}
