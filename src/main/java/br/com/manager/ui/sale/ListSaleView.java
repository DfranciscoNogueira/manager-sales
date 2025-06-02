package br.com.manager.ui.sale;


import br.com.manager.Home;
import br.com.manager.dto.ClientDTO;
import br.com.manager.dto.SalesDTO;
import br.com.manager.service.ClientService;
import br.com.manager.service.SalesService;
import br.com.manager.ui.shared.ButtonRenderer;
import br.com.manager.util.TextManager;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class ListSaleView extends JFrame {

    private static final String DEFAULT_FONT = TextManager.getLabel("default.font");
    private static final String SALE_LIST = TextManager.getLabel("sale.list");

    private final ClientService clientService;
    private final SalesService salesService;

    private DefaultTableModel modelTable;
    private JDialog loadingDialog;
    private JTable tableSales;

    private JComboBox<ClientDTO> comboClients;
    private JTextField fieldEndDate;
    private JTextField fieldStartDate;

    public ListSaleView() {

        this.salesService = new SalesService();
        this.clientService = new ClientService();

        setTitle(TextManager.getLabel("sale.title"));

        setExtendedState(Frame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        startScreen();
        showLoading();

        new Thread(this::loadSales).start();
    }

    private void startScreen() {

        JPanel painelFiltros = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        this.comboClients = new JComboBox<>(this.clientService.findAll().toArray(new ClientDTO[0]));
        JButton btnFilterClient = new JButton(TextManager.getLabel("filter.client"));
        btnFilterClient.addActionListener(e -> filterByCustomer());

        painelFiltros.add(new JLabel(TextManager.getLabel("sale.client")));
        painelFiltros.add(this.comboClients);
        painelFiltros.add(btnFilterClient);

        this.fieldStartDate = new JTextField(10);
        this.fieldEndDate = new JTextField(10);
        JButton btnFilterPeriod = new JButton(TextManager.getLabel("filter.period"));
        btnFilterPeriod.addActionListener(e -> filterByPeriod());

        painelFiltros.add(new JLabel(TextManager.getLabel("sale.label.dateStart")));
        painelFiltros.add(this.fieldStartDate);
        painelFiltros.add(new JLabel(TextManager.getLabel("sale.label.dateEnd")));
        painelFiltros.add(this.fieldEndDate);
        painelFiltros.add(btnFilterPeriod);

        add(painelFiltros, BorderLayout.NORTH);

        JPanel panelMain = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JPanel panelInternal = new JPanel();
        panelInternal.setLayout(new BorderLayout());
        panelInternal.setPreferredSize(new Dimension(950, 650));
        panelInternal.setBorder(BorderFactory.createTitledBorder(SALE_LIST));

        JLabel title = new JLabel(SALE_LIST, SwingConstants.CENTER);
        title.setFont(new Font(DEFAULT_FONT, Font.BOLD, 22));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        String[] columnNames = {
                TextManager.getLabel("sale.table.id"),
                TextManager.getLabel("sale.table.client"),
                TextManager.getLabel("sale.table.date"),
                TextManager.getLabel("sale.table.total"),
                TextManager.getLabel("sale.table.actions")
        };

        this.modelTable = new DefaultTableModel(columnNames, 0);
        this.tableSales = new JTable(this.modelTable);

        this.tableSales.setRowHeight(40);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < this.tableSales.getColumnCount() - 1; i++) {
            this.tableSales.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        this.tableSales.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
        this.tableSales.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox(), this));

        JScrollPane scrollPane = new JScrollPane(this.tableSales);
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
        btnAdd.addActionListener(e -> openSaleRegistrationScreen());

        panelButtons.add(btnBack);
        panelButtons.add(btnAdd);

        panelInternal.add(title, BorderLayout.NORTH);
        panelInternal.add(scrollPane, BorderLayout.CENTER);
        panelInternal.add(panelButtons, BorderLayout.SOUTH);

        panelMain.add(panelInternal, gbc);
        add(panelMain);
    }

    private void filterByCustomer() {
        ClientDTO client = (ClientDTO) this.comboClients.getSelectedItem();
        if (Objects.nonNull(client)) {
            List<SalesDTO> salesFiltered = this.salesService.findByClient(client.getId());
            updateTable(salesFiltered);
        }
    }

    private void filterByPeriod() {
        try {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            LocalDate dateStart = LocalDate.parse(this.fieldStartDate.getText(), formatter);
            LocalDate endDate = LocalDate.parse(this.fieldEndDate.getText(), formatter);

            List<SalesDTO> salesFiltered = this.salesService.findByPeriod(dateStart, endDate);
            updateTable(salesFiltered);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, TextManager.getLabel("sale.invalid.date"), TextManager.getLabel("product.message.error.title"), JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTable(List<SalesDTO> sales) {
        this.modelTable.setRowCount(0);
        for (SalesDTO sale : sales) {
            this.modelTable.addRow(new Object[]{sale.getId(), sale.getClient().getId(), sale.getDateSale(), sale.getTotal()});
        }
    }

    private void showLoading() {

        loadingDialog = new JDialog(this, TextManager.getLabel("sale.loading"), true);
        loadingDialog.setSize(500, 300);
        loadingDialog.setLocationRelativeTo(this);
        loadingDialog.setUndecorated(true);

        JPanel panelLoading = new JPanel(new BorderLayout());
        panelLoading.setBackground(new Color(173, 216, 230));

        JLabel message = new JLabel(TextManager.getLabel("sale.loading.list"), SwingConstants.CENTER);
        message.setFont(new Font(DEFAULT_FONT, Font.BOLD, 16));
        panelLoading.add(message, BorderLayout.CENTER);

        loadingDialog.setContentPane(panelLoading);

        new Thread(() -> loadingDialog.setVisible(true)).start();
    }

    private void hideLoading() {
        SwingUtilities.invokeLater(() -> loadingDialog.dispose());
    }

    public void loadSales() {

        List<SalesDTO> sales = salesService.findAll();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        SwingUtilities.invokeLater(() -> {
            modelTable.setRowCount(0);
            for (SalesDTO sale : sales) {
                String formattedDate = Objects.isNull(sale.getDateSale()) ? "" : sale.getDateSale().format(formatter);
                modelTable.addRow(new Object[]{
                        sale.getId(), sale.getClient().getName(), formattedDate, sale.getTotal(), TextManager.getLabel("client.table.actions")
                });
            }
            hideLoading();
        });

    }

    private void openSaleRegistrationScreen() {
        dispose();
        SwingUtilities.invokeLater(() -> new RegistrationSaleView(null).setVisible(true));
    }

    private void returnToMenu() {
        dispose();
        SwingUtilities.invokeLater(() -> Home.main(null));
    }

    public JTable getTableSales() {
        return this.tableSales;
    }

    public SalesService getSalesService() {
        return this.salesService;
    }

}