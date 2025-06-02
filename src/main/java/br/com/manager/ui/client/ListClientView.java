package br.com.manager.ui.client;


import br.com.manager.Home;
import br.com.manager.dto.ClientDTO;
import br.com.manager.service.ClientService;
import br.com.manager.ui.shared.ButtonRenderer;
import br.com.manager.util.TextManager;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ListClientView extends JFrame {

    private static final String DEFAULT_FONT = TextManager.getLabel("default.font");
    private static final String CLIENT_LIST = TextManager.getLabel("client.list");

    private final ClientService clientService;

    private DefaultTableModel modelTable;
    private JDialog loadingDialog;
    private JTable tableClient;

    public ListClientView() {

        this.clientService = new ClientService();

        setTitle(TextManager.getLabel("client.title"));

        setExtendedState(Frame.MAXIMIZED_BOTH); // Maximiza a tela
        setUndecorated(true); // Remove a borda da janela
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        startScreen(); // Inicializa os componentes da interface

        showLoading(); // Exibe a tela de carregamento enquanto busca os dados

        new Thread(this::loadClients).start(); // Executa a busca em uma thread separada
    }

    private void startScreen() {

        JPanel panelMain = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JPanel panelInternal = new JPanel();
        panelInternal.setLayout(new BorderLayout());
        panelInternal.setPreferredSize(new Dimension(950, 650));
        panelInternal.setBorder(BorderFactory.createTitledBorder(CLIENT_LIST));

        JLabel title = new JLabel(CLIENT_LIST, SwingConstants.CENTER);
        title.setFont(new Font(DEFAULT_FONT, Font.BOLD, 22));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        String[] columnNames = {
                TextManager.getLabel("client.table.id"),
                TextManager.getLabel("client.table.name"),
                TextManager.getLabel("client.table.limit"),
                TextManager.getLabel("client.table.closing"),
                TextManager.getLabel("client.table.actions")
        };

        this.modelTable = new DefaultTableModel(columnNames, 0);
        this.tableClient = new JTable(this.modelTable);

        this.tableClient.setRowHeight(40);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < this.tableClient.getColumnCount() - 1; i++) {
            this.tableClient.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Adicionando renderizador e editor de célula com botões
        this.tableClient.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
        this.tableClient.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox(), this));

        JScrollPane scrollPane = new JScrollPane(this.tableClient);
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
        btnAdd.addActionListener(e -> openCustomerRegistrationScreen());

        panelButtons.add(btnBack);
        panelButtons.add(btnAdd);

        panelInternal.add(title, BorderLayout.NORTH);
        panelInternal.add(scrollPane, BorderLayout.CENTER);
        panelInternal.add(panelButtons, BorderLayout.SOUTH);

        panelMain.add(panelInternal, gbc);
        add(panelMain);
    }

    private void showLoading() {

        this.loadingDialog = new JDialog(this, TextManager.getLabel("client.loading"), true);
        this.loadingDialog.setSize(500, 300);
        this.loadingDialog.setLocationRelativeTo(this);
        this.loadingDialog.setUndecorated(true);

        JPanel panelLoading = new JPanel(new BorderLayout());
        panelLoading.setBackground(new Color(173, 216, 230));

        JLabel message = new JLabel(TextManager.getLabel("client.loading.list"), SwingConstants.CENTER);
        message.setFont(new Font(DEFAULT_FONT, Font.BOLD, 16));
        panelLoading.add(message, BorderLayout.CENTER);

        this.loadingDialog.setContentPane(panelLoading);

        new Thread(() -> this.loadingDialog.setVisible(true)).start();
    }

    private void hideLoading() {
        SwingUtilities.invokeLater(() -> this.loadingDialog.dispose());
    }

    public void loadClients() {

        List<ClientDTO> clients = this.clientService.findAll();

        SwingUtilities.invokeLater(() -> {
            this.modelTable.setRowCount(0);
            for (ClientDTO client : clients) {
                this.modelTable.addRow(new Object[]{
                        client.getId(), client.getName(), client.getLimitSales(), client.getDayClosingInvoice(), TextManager.getLabel("client.table.actions")
                });
            }
            hideLoading();
        });

    }

    private void openCustomerRegistrationScreen() {
        dispose();
        SwingUtilities.invokeLater(() -> new RegistrationClientView(null).setVisible(true));
    }

    private void returnToMenu() {
        dispose();
        SwingUtilities.invokeLater(() -> Home.main(null));
    }

    public JTable getTableClient() {
        return this.tableClient;
    }

    public ClientService getClientService() {
        return this.clientService;
    }

}