package view;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private static MainWindow instance = null;
    private JSplitPane splitPane;
    private ProductsPanel productsPanel;
    private OrderPanel orderPanel;

    public static MainWindow getInstance() {
        if (instance == null) {
            instance = new MainWindow();
        }
        return instance;
    }

    public MainWindow() {
        super("PayU Client");
        setupFrameValues();
        setupPanels();
        setupVerticalSplitPane();
    }

    private void setupPanels() {
        productsPanel = new ProductsPanel();
        orderPanel = new OrderPanel(this);
    }

    private void setupVerticalSplitPane() {
        JPanel borderLayoutPanel=new JPanel(new BorderLayout());
        borderLayoutPanel.add(productsPanel, BorderLayout.NORTH);
        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, new JScrollPane(borderLayoutPanel), new JScrollPane(orderPanel));
        splitPane.setContinuousLayout(true);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(300);
        getContentPane().add(splitPane);
    }

    private void setupFrameValues() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(640, 500));
        pack();
        setLocationRelativeTo(null);
    }
}
