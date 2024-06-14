package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StorageHelper {

    private Connection conn;

    public StorageHelper(String dbFilePath) {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:" + dbFilePath);
            System.out.println("Connected to SQLite database: " + dbFilePath);
            createTableIfNotExists();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTableIfNotExists() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS products (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "available TEXT, " +
                "unit_price REAL, " +
                "image_path TEXT)";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("Table 'products' created or already exists.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertProduct(Product product) {
        try (PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO products(name, available, unit_price, image_path) VALUES (?, ?, ?, ?)")) {
            pstmt.setString(1, product.getName());
            pstmt.setString(2, product.getAvailable());
            pstmt.setString(3, product.getUnitPrice());
            pstmt.setString(4, product.getImagePath());
            pstmt.executeUpdate();
            System.out.println("Product inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM products")) {
            while (rs.next()) {
                String name = rs.getString("name");
                String available = rs.getString("available");
                String unitPrice = rs.getString("unit_price");
                String imagePath = rs.getString("image_path");
                Product product = new Product(name, available, unitPrice, imagePath);
                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    public void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
                System.out.println("Disconnected from SQLite database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        StorageHelper helper = new StorageHelper("./bin/local_db.sqlite");

        // Example usage: Insert product
        Product product = new Product("Product A", "Available", "19.99", "/path/to/image.jpg");
        helper.insertProduct(product);

        // Example usage: Retrieve all products
        List<Product> productList = helper.getAllProducts();
        for (Product p : productList) {
            System.out.println(
                    p.getName() + ", " + p.getAvailable() + ", $" + p.getUnitPrice() + ", " + p.getImagePath());
        }

        // Close the connection when done
        helper.closeConnection();
    }
}
