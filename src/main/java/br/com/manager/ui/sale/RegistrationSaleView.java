package br.com.manager.ui.sale;


import br.com.manager.dto.ClientDTO;
import br.com.manager.dto.ProductDTO;
import br.com.manager.dto.SaleItemDTO;
import br.com.manager.dto.SalesDTO;
import br.com.manager.service.ClientService;
import br.com.manager.service.ProductService;
import br.com.manager.service.SalesService;
import br.com.manager.util.Constants;
import br.com.manager.util.TextManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RegistrationSaleView extends JFrame {

    private static final String SALE_REGISTRATION = TextManager.getLabel("sale.registration");
    private static final String DEFAULT_FONT = TextManager.getLabel("default.font");

    private final ProductService productService;
    private final ClientService clientService;
    private final SalesService salesService;

    private JComboBox<ProductDTO> comboProducts;
    private JComboBox<ClientDTO> comboClients;
    private JTextField fieldQuantity;

    private List<SaleItemDTO> itemsSale = new ArrayList<>();
    private SalesDTO saleEdit;

    private DefaultTableModel modelTable;
    private JTable tableItems;

    public RegistrationSaleView(SalesDTO salesDTO) {

        this.salesService = new SalesService();
        this.clientService = new ClientService();
        this.productService = new ProductService();

        this.saleEdit = salesDTO;
        setTitle(SALE_REGISTRATION);

        setExtendedState(Frame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        startScreen();

        if (Objects.nonNull(salesDTO)) {
            fillFieldsWithSale(salesDTO);
        }

    }

    private void startScreen() {

        JPanel panelMain = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JPanel panelInternal = new JPanel();
        panelInternal.setLayout(new GridLayout(4, 2, 10, 10));
        panelInternal.setPreferredSize(new Dimension(500, 250));
        panelInternal.setBorder(BorderFactory.createTitledBorder(SALE_REGISTRATION));

        this.comboClients = new JComboBox<>(this.clientService.findAll().toArray(new ClientDTO[0]));
        this.comboProducts = new JComboBox<>(this.productService.findAll().toArray(new ProductDTO[0]));

        this.fieldQuantity = new JTextField();

        panelInternal.add(new JLabel(TextManager.getLabel("sale.client")));
        panelInternal.add(this.comboClients);
        panelInternal.add(new JLabel(TextManager.getLabel("sale.product")));
        panelInternal.add(this.comboProducts);
        panelInternal.add(new JLabel(TextManager.getLabel("sale.quantity")));
        panelInternal.add(this.fieldQuantity);

        JButton btnAddProduct = new JButton(TextManager.getLabel("sale.add.product"));
        btnAddProduct.setBackground(Color.BLUE);
        btnAddProduct.setForeground(Color.WHITE);
        btnAddProduct.addActionListener(e -> addProduct());

        panelInternal.add(btnAddProduct);

        String[] columnNames = {
                TextManager.getLabel("sale.label.total"),
                TextManager.getLabel("sale.label.product"),
                TextManager.getLabel("sale.label.quantity"),
                TextManager.getLabel("sale.label.delete")
        };

        this.modelTable = new DefaultTableModel(columnNames, 0);
        this.tableItems = new JTable(this.modelTable);
        this.tableItems.setRowHeight(40);

        this.tableItems.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer());
        this.tableItems.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor(new JCheckBox(), this));

        JButton btnRemover = new JButton(TextManager.getLabel("sale.label.delete"));
        btnRemover.setBackground(Color.RED);
        btnRemover.setForeground(Color.WHITE);
        btnRemover.addActionListener(e -> removerItem());

        JScrollPane scrollPane = new JScrollPane(this.tableItems);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnBack = new JButton(TextManager.getLabel("default.btn.back"));
        btnBack.setBackground(Color.YELLOW);
        btnBack.setForeground(Color.BLACK);
        btnBack.setFont(new Font(DEFAULT_FONT, Font.BOLD, 14));
        btnBack.addActionListener(e -> backToListing());

        JButton btnSave = new JButton(TextManager.getLabel("default.btn.save"));
        btnSave.setBackground(Color.GREEN);
        btnSave.setForeground(Color.WHITE);
        btnSave.setFont(new Font(DEFAULT_FONT, Font.BOLD, 14));
        btnSave.addActionListener(e -> saveSale());

        panelButtons.add(btnBack);
        panelButtons.add(btnSave);

        panelMain.add(panelInternal, gbc);
        gbc.gridy = 1;
        panelMain.add(scrollPane, gbc);
        gbc.gridy = 2;
        panelMain.add(panelButtons, gbc);

        add(panelMain);

    }

    private void addProduct() {

        ProductDTO item = (ProductDTO) this.comboProducts.getSelectedItem();
        int amount = Integer.parseInt(this.fieldQuantity.getText());
        BigDecimal total = Objects.isNull(item.getPrice()) ? BigDecimal.ZERO : item.getPrice().multiply(BigDecimal.valueOf(amount));

        this.itemsSale.add(new SaleItemDTO(item, amount));

        this.modelTable.addRow(new Object[]{item.getDescription(), amount, total, TextManager.getLabel("sale.label.delete")});
        JOptionPane.showMessageDialog(this, TextManager.getLabel("sale.label.add"));

    }

    private void removerItem() {
        int selectedRow = this.tableItems.getSelectedRow();
        if (selectedRow >= 0) {
            this.itemsSale.remove(selectedRow);
            this.modelTable.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, TextManager.getLabel("sale.label.select.item"));
        }
    }

    public void removerItem(int row) {
        if (row >= 0) {
            this.itemsSale.remove(row);
            this.modelTable.removeRow(row);
        } else {
            JOptionPane.showMessageDialog(this, TextManager.getLabel("sale.label.select.item"));
        }
    }

    private void saveSale() {
        try {
            ClientDTO client = (ClientDTO) this.comboClients.getSelectedItem();
            this.salesService.registerSale(client.getId(), this.itemsSale);
            JOptionPane.showMessageDialog(this, TextManager.getLabel("sale.message.add"));
            backToListing();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, TextManager.getLabel("sale.message.error.save") + Constants.SPACE_BLANK + e.getMessage(), TextManager.getLabel("sale.message.error.title"), JOptionPane.ERROR_MESSAGE);
        }
    }

    private void backToListing() {
        dispose();
        SwingUtilities.invokeLater(() -> new ListSaleView().setVisible(true));
    }

    private void fillFieldsWithSale(SalesDTO sales) {

        for (int i = 0; i < this.comboClients.getItemCount(); i++) {
            if (this.comboClients.getItemAt(i).getId().equals(sales.getClient().getId())) {
                this.comboClients.setSelectedIndex(i);
                break;
            }
        }

        this.modelTable.setRowCount(0);
        this.itemsSale.clear();

        for (SaleItemDTO item : sales.getItems()) {

            this.itemsSale.add(item);

            JButton btnRemover = new JButton(TextManager.getLabel("sale.label.delete"));
            btnRemover.setBackground(Color.RED);
            btnRemover.setForeground(Color.WHITE);
            btnRemover.addActionListener(e -> removerItem(itemsSale.indexOf(item)));

            modelTable.addRow(new Object[]{
                    item.getProduct().getDescription(),
                    item.getAmount(),
                    item.getTotalValue(),
                    btnRemover
            });

        }
    }

    static class ButtonRenderer extends JPanel implements TableCellRenderer {

        public ButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER));
            JButton btnRemover = new JButton(TextManager.getLabel("sale.label.delete"));
            btnRemover.setBackground(Color.RED);
            btnRemover.setForeground(Color.WHITE);
            add(btnRemover);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }

    }

    static class ButtonEditor extends DefaultCellEditor {

        private final RegistrationSaleView view;
        private final JButton btnRemove;
        private int selectedRow;

        public ButtonEditor(JCheckBox checkBox, RegistrationSaleView view) {
            super(checkBox);
            this.view = view;
            this.btnRemove = new JButton(TextManager.getLabel("sale.label.delete"));
            this.btnRemove.setBackground(Color.RED);
            this.btnRemove.setForeground(Color.WHITE);
            this.btnRemove.addActionListener(e -> removerItem());
        }

        private void removerItem() {
            this.view.removerItem(this.selectedRow);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.selectedRow = row;
            return this.btnRemove;
        }

    }

}