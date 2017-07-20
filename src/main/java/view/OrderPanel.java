package view;

import controller.AuthorizeResponse;
import controller.OrderResponse;
import controller.Product;
import controller.RestService;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class OrderPanel extends JPanel {
    private MainWindow mainWindow;

    private JLabel orderIdLabel = new JLabel();
    private JLabel statusLabel = new JLabel();
    private JLabel redirectUriLabel = new JLabel();

    public OrderPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        setLayout(new BorderLayout());
        setupComponents();
        setupListeners();
    }

    private void setupListeners() {
        redirectUriLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        redirectUriLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() > 0) {
                    if (Desktop.isDesktopSupported()) {
                        Desktop desktop = Desktop.getDesktop();
                        try {
                            URI uri = new URI(redirectUriLabel.getText());
                            desktop.browse(uri);
                        } catch (IOException ex) {
                        } catch (URISyntaxException ex) {
                        }
                    }
                }
            }
        });
    }

    private void setupComponents() {
        JPanel panel = new JPanel(new MigLayout());
        JButton orderProductsButton = new JButton("Send Order");
        orderProductsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                orderProducts();
            }
        });
        panel.add(orderProductsButton);
        add(panel, BorderLayout.NORTH);

        panel = new JPanel(new MigLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Order response"));
        panel.add(new JLabel("OrderId:"));
        panel.add(orderIdLabel, "wrap");
        panel.add(new JLabel("Status:"));
        panel.add(statusLabel, "wrap");
        panel.add(new JLabel("RedirectUri:"));
        panel.add(redirectUriLabel);
        redirectUriLabel.setForeground(Color.BLUE);
        add(panel, BorderLayout.CENTER);
    }

    private void orderProducts() {
        List<Product> productList = mainWindow.getProductList();
        if(!validateProducts(productList)){
            return;
        }
        try {
            AuthorizeResponse authorizeResponse = RestService.getAuthorization();
            OrderResponse orderResponse = RestService.orderProducts(authorizeResponse, productList);
            orderIdLabel.setText(orderResponse.getOrderId());
            statusLabel.setText(orderResponse.getStatus().getStatusCode());
            statusLabel.setForeground(Color.GREEN);
            redirectUriLabel.setText(orderResponse.getRedirectUri());
        } catch (IOException e) {
            e.printStackTrace();
            orderIdLabel.setText("");
            statusLabel.setText("FAILED");
            statusLabel.setForeground(Color.RED);
            redirectUriLabel.setText("");
            JOptionPane.showMessageDialog(mainWindow, "Order products failed.");
        }

    }

    private boolean validateProducts(List<Product> productList) {
        for (Product product : productList) {
            //name validation
            if(product.getName().equals("")){
                JOptionPane.showMessageDialog(mainWindow, "All products must have name!");
                return false;
            }

            //unitPrice validation
            if(product.getUnitPrice() == null){
                JOptionPane.showMessageDialog(mainWindow, "All products must have unit price!");
                return false;
            }
            if(product.getUnitPrice() <= 0){
                JOptionPane.showMessageDialog(mainWindow, "UnitPrice must be larger than 0!");
                return false;
            }

            //quantity validation
            if(product.getQuantity() == null){
                JOptionPane.showMessageDialog(mainWindow, "All products must have quantity!");
                return false;
            }
            if(product.getQuantity() <= 0){
                JOptionPane.showMessageDialog(mainWindow, "Quantity must be larger than 0!");
                return false;
            }
        }
        return true;
    }


}
