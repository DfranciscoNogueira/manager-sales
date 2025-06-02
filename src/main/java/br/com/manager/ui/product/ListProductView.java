package br.com.manager.ui.product;


import br.com.manager.Home;
import br.com.manager.dto.ProductDTO;
import br.com.manager.service.ProductService;
import br.com.manager.ui.shared.ButtonRenderer;
import br.com.manager.util.TextManager;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ListProductView extends JFrame {

    private static final String DEFAULT_FONT = TextManager.getLabel("default.font");
    private static final String PRODUCT_LIST = TextManager.getLabel("product.list");

    private final ProductService productService;

    private DefaultTableModel modelTable;
    private JDialog loadingDialog;
    private JTable tableProduct;

    public ListProductView() {

        this.productService = new ProductService();

        setTitle(TextManager.getLabel("product.title"));

        setExtendedState(Frame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        startScreen();
        showLoading();

        new Thread(this::loadProducts).start();
    }

    private void startScreen() {

        JPanel panelMain = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JPanel panelInternal = new JPanel();
        panelInternal.setLayout(new BorderLayout());
        panelInternal.setPreferredSize(new Dimension(950, 650));
        panelInternal.setBorder(BorderFactory.createTitledBorder(PRODUCT_LIST));

        JLabel title = new JLabel(PRODUCT_LIST, SwingConstants.CENTER);
        title.setFont(new Font(DEFAULT_FONT, Font.BOLD, 22));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        String[] columnNames = {
                TextManager.getLabel("product.table.id"),
                TextManager.getLabel("product.table.description"),
                TextManager.getLabel("product.table.price"),
                TextManager.getLabel("product.table.actions")
        };

        this.modelTable = new DefaultTableModel(columnNames, 0);
        this.tableProduct = new JTable(this.modelTable);

        this.tableProduct.setRowHeight(40);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < this.tableProduct.getColumnCount() - 1; i++) {
            this.tableProduct.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        this.tableProduct.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer());
        this.tableProduct.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor(new JCheckBox(), this));

        JScrollPane scrollPane = new JScrollPane(this.tableProduct);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton btnBack = new JButton(TextManager.getLabel("default.btn.back"));
        btnBack.setBackground(Color.YELLOW);
        btnBack.setForeground(Color.BLACK);
        btnBack.setFont(new Font(DEFAULT_FONT, Font.BOLD, 14));
        btnBack.addActionListener(e -> returnToMenu());

        JButton btnAdd = new JButton(TextManager.getLabel("default.btn.add"));
        btnAdd.setBackground(Color.GREEN);
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFont(new Font(DEFAULT_FONT, Font.BOLD, 14));
        btnAdd.addActionListener(e -> openProductRegistrationScreen());

        panelButtons.add(btnBack);
        panelButtons.add(btnAdd);

        panelInternal.add(title, BorderLayout.NORTH);
        panelInternal.add(scrollPane, BorderLayout.CENTER);
        panelInternal.add(panelButtons, BorderLayout.SOUTH);

        panelMain.add(panelInternal, gbc);
        add(panelMain);
    }

    private void showLoading() {

        this.loadingDialog = new JDialog(this, TextManager.getLabel("product.loading"), true);
        this.loadingDialog.setSize(500, 300);
        this.loadingDialog.setLocationRelativeTo(this);
        this.loadingDialog.setUndecorated(true);

        JPanel panelLoading = new JPanel(new BorderLayout());
        panelLoading.setBackground(new Color(173, 216, 230));

        JLabel message = new JLabel(TextManager.getLabel("product.loading.list"), SwingConstants.CENTER);
        message.setFont(new Font(DEFAULT_FONT, Font.BOLD, 16));
        panelLoading.add(message, BorderLayout.CENTER);

        this.loadingDialog.setContentPane(panelLoading);

        new Thread(() -> this.loadingDialog.setVisible(true)).start();
    }

    private void hideLoading() {
        SwingUtilities.invokeLater(() -> this.loadingDialog.dispose());
    }

    public void loadProducts() {

        List<ProductDTO> products = this.productService.findAll();

        SwingUtilities.invokeLater(() -> {
            this.modelTable.setRowCount(0);
            for (ProductDTO product : products) {
                this.modelTable.addRow(new Object[]{
                        product.getId(), product.getDescription(), product.getPrice(), TextManager.getLabel("product.table.actions")
                });
            }
            hideLoading();
        });

    }

    private void openProductRegistrationScreen() {
        dispose();
        SwingUtilities.invokeLater(() -> new RegistrationProductView(null).setVisible(true));
    }

    private void returnToMenu() {
        dispose();
        SwingUtilities.invokeLater(() -> Home.main(null));
    }

    public JTable getTableProduct() {
        return this.tableProduct;
    }

    public ProductService getProductService() {
        return this.productService;
    }

}