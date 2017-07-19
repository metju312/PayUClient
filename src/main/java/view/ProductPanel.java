package view;

import controller.Product;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductPanel extends JPanel{
    private ProductsPanel productsPanel;

    private JTextField nameTextField;
    private JTextField unitPriceTextField;
    private JTextField quantityPriceTextField;

    public ProductPanel(ProductsPanel productsPanel) {
        this.productsPanel = productsPanel;
        setupPanel();
    }

    private void setupPanel() {
        add(new JLabel("Name:"));
        nameTextField = new JTextField(16);
        add(nameTextField);

        add(new JLabel("Unit price:"));
        unitPriceTextField = new JTextField(8);
        add(unitPriceTextField);

        add(new JLabel("Quantity:"));
        quantityPriceTextField = new JTextField(4);
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
        product.setUnitPrice(Integer.valueOf(unitPriceTextField.getText()));
        product.setQuantity(Integer.valueOf(quantityPriceTextField.getText()));
        return product;
    }
}
