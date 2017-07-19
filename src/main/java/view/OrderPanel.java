package view;

import controller.AuthorizeResponse;
import controller.OrderResponse;
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
        add(panel, BorderLayout.CENTER);
    }

    private void orderProducts() {
        try {
            AuthorizeResponse authorizeResponse = RestService.getAuthorization();
            OrderResponse orderResponse = RestService.orderProducts(authorizeResponse, mainWindow.getProductList());
            orderIdLabel.setText(orderResponse.getOrderId());
            statusLabel.setText(orderResponse.getStatus().getStatusCode());
            redirectUriLabel.setText(orderResponse.getRedirectUri());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
