package view;

import controller.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductPanel extends JPanel{
    private ProductsPanel productsPanel;

    private JTextField nameTextField;
    private JFormattedTextField unitPriceTextField;
    private JFormattedTextField quantityPriceTextField;

    public ProductPanel(ProductsPanel productsPanel) {
        this.productsPanel = productsPanel;
        setupPanel();
    }

    private void setupPanel() {
        add(new JLabel("Name:"));
        nameTextField = new JTextField(16);
        add(nameTextField);

        add(new JLabel("Unit price:"));
        unitPriceTextField = new JFormattedTextField(new Double(0d));
        unitPriceTextField.setPreferredSize(new Dimension(80, 24));
        add(unitPriceTextField);

        add(new JLabel("Quantity:"));
        quantityPriceTextField = new JFormattedTextField(new Integer(1));
        quantityPriceTextField.setPreferredSize(new Dimension(60, 24));
        add(quantityPriceTextField);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteThisPanel();
                productsPanel.refresh();
            }
        });
        add(deleteButton);
    }

    private void deleteThisPanel() {
        productsPanel.delete(this);
    }

    public Product getProduct(){
        Product product = new Product();
        product.setName(nameTextField.getText());

        String unitPrice = unitPriceTextField.getText();
        unitPrice = unitPrice.replaceAll(",", "");
        product.setUnitPrice(Integer.valueOf(unitPrice)*100);

        product.setQuantity(Integer.valueOf(quantityPriceTextField.getText()));
        return product;
    }
}
