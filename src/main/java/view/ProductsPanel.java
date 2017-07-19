package view;

import controller.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class ProductsPanel extends JPanel {
    private List<ProductPanel> productPanelList = new ArrayList<ProductPanel>();
    private GridBagConstraints gridBagConstraints;
    private JButton newProductPanelButton;

    public ProductsPanel() {
        setBorder(BorderFactory.createTitledBorder("Order products"));
        setLayout(new GridBagLayout());
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;

        addProductPanel();
        refresh();
    }

    private void addProductPanel() {
        ProductPanel productPanel = new ProductPanel(this);
        productPanelList.add(productPanel);
        add(productPanel,gridBagConstraints);
        gridBagConstraints.gridy++;
    }


    public void refresh() {
        removeAll();
        gridBagConstraints.gridy=0;
        for (ProductPanel productPanel : productPanelList) {
            add(productPanel,gridBagConstraints);
            gridBagConstraints.gridy++;
        }
        newProductPanelButton = new JButton("New Product");
        newProductPanelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addProductPanel();
                refresh();
            }
        });
        add(newProductPanelButton,gridBagConstraints);
        revalidate();
        repaint();
    }

    public void delete(ProductPanel productPanel) {
        productPanelList.remove(productPanel);
    }

    public void getProductList(){
        List<Product> productList = new ArrayList<Product>();
        for (ProductPanel productPanel : productPanelList) {
            productList.add(productPanel.getProduct());
        }
    }
}
