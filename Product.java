package database;

public class Product {
    private int id;
    private String name;
    private String available;
    private String unitPrice;
    private String imagePath;

    // Constructors, getters, and setters
    public Product(String name, String available, String unitPrice, String imagePath) {
        this.name = name;
        this.available = available;
        this.unitPrice = unitPrice;
        this.imagePath = imagePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
