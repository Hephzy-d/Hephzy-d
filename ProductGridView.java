package customer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import database.Product;
import database.StorageHelper;

public class ProductGridView extends JFrame {

    private StorageHelper storageHelper = new StorageHelper("./bin/local_db.sqlite");
    private JPanel productPanel;

    public ProductGridView() {
        setTitle("Product Grid View");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set layout for main content pane
        getContentPane().setLayout(new BorderLayout());

        // Create scroll pane with custom settings
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Disable horizontal scroll
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); // Enable vertical scroll

        // Create panel for products with grid layout
        productPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        productPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Add product panel to scroll pane
        scrollPane.setViewportView(productPanel);

        // Add scroll pane to content pane
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Display products
        displayProducts();

        // Set frame size and make visible
        setSize(900, 600); // Adjusted size to accommodate scroll bars
        setVisible(true);
    }

    private void displayProducts() {
        List<Product> productList = storageHelper.getAllProducts();
        for (Product product : productList) {
            JPanel cardPanel = createProductCard(product);
            productPanel.add(cardPanel);
        }
    }

    private JPanel createProductCard(Product product) {
        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Image panel
        JPanel imagePanel = new JPanel();
        try {
            BufferedImage image = ImageIO.read(new File(product.getImagePath()));
            JLabel imageLabel = new JLabel(new ImageIcon(image.getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
            imagePanel.add(imageLabel);
        } catch (IOException e) {
            e.printStackTrace();
            JLabel imageLabel = new JLabel("Image not found");
            imagePanel.add(imageLabel);
        }
        cardPanel.add(imagePanel, BorderLayout.CENTER);

        // Details panel
        JPanel detailsPanel = new JPanel(new GridLayout(3, 1));
        detailsPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        JLabel nameLabel = new JLabel(product.getName());
        detailsPanel.add(nameLabel);

        JLabel availableLabel = new JLabel("Availability: " + product.getAvailable());
        detailsPanel.add(availableLabel);

        JLabel priceLabel = new JLabel("Price: $" + product.getUnitPrice());
        detailsPanel.add(priceLabel);

        cardPanel.add(detailsPanel, BorderLayout.SOUTH);

        return cardPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ProductGridView::new);
    }
}
