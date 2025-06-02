package br.com.manager.ui.client;


import br.com.manager.dto.ClientDTO;
import br.com.manager.service.ClientService;
import br.com.manager.util.TextManager;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.Objects;

public class RegistrationClientView extends JFrame {

    private static final String CLIENT_REGISTRATION = TextManager.getLabel("client.registration");
    private static final String DEFAULT_FONT = TextManager.getLabel("default.font");

    private final ClientService clientService;
    private final ClientDTO clientEdit;

    // campos do formulário
    private JTextField fieldClosing;
    private JTextField fieldLimit;
    private JTextField fieldName;

    public RegistrationClientView(ClientDTO clientDTO) {

        this.clientService = new ClientService();
        this.clientEdit = clientDTO;
        setTitle(CLIENT_REGISTRATION);

        setExtendedState(Frame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        startScreen();

        if (Objects.nonNull(clientDTO)) {
            this.fieldName.setText(Objects.requireNonNullElse(clientDTO.getName(), ""));
            this.fieldLimit.setText(Objects.nonNull(clientDTO.getLimitSales()) ? clientDTO.getLimitSales().toString() : "");
            this.fieldClosing.setText(String.valueOf(clientDTO.getDayClosingInvoice()));
        }

    }

    private void startScreen() {

        JPanel panelMain = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JPanel panelInternal = new JPanel();
        panelInternal.setLayout(new GridLayout(4, 1, 10, 10));
        panelInternal.setPreferredSize(new Dimension(350, 200));
        panelInternal.setBorder(BorderFactory.createTitledBorder(CLIENT_REGISTRATION));

        // Campos
        JLabel labelName = new JLabel(TextManager.getLabel("client.label.name"));
        this.fieldName = new JTextField();
        this.fieldName.setPreferredSize(new Dimension(200, 25));

        JLabel labelLimit = new JLabel(TextManager.getLabel("client.label.limit"));
        this.fieldLimit = new JTextField();
        this.fieldLimit.setPreferredSize(new Dimension(200, 25));

        JLabel labelClosing = new JLabel(TextManager.getLabel("client.label.closing"));
        this.fieldClosing = new JTextField();
        this.fieldClosing.setPreferredSize(new Dimension(200, 25));

        panelInternal.add(labelName);
        panelInternal.add(this.fieldName);
        panelInternal.add(labelLimit);
        panelInternal.add(this.fieldLimit);
        panelInternal.add(labelClosing);
        panelInternal.add(this.fieldClosing);

        // Painel de botões
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
        btnSave.addActionListener(e -> saveClient());

        panelButtons.add(btnBack);
        panelButtons.add(btnSave);

        panelMain.add(panelInternal, gbc);
        gbc.gridy = 1;
        panelMain.add(panelButtons, gbc);

        add(panelMain);
    }

    private void saveClient() {

        String name = this.fieldName.getText().trim();
        String limitStr = this.fieldLimit.getText().trim();
        String closingStr = this.fieldClosing.getText().trim();

        String titleError = TextManager.getLabel("client.message.error.title");

        if (name.isEmpty() || limitStr.isEmpty() || closingStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, TextManager.getLabel("client.message.field.required"), titleError, JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {

            BigDecimal limit = new BigDecimal(limitStr);
            int closing = Integer.parseInt(closingStr);

            if (Objects.isNull(this.clientEdit)) {
                this.clientService.save(new ClientDTO(name, limit, closing));
            } else {
                this.clientService.save(new ClientDTO(this.clientEdit.getId(), name, limit, closing));
            }

            JOptionPane.showMessageDialog(this, TextManager.getLabel("client.message.success"), TextManager.getLabel("client.message.success.title"), JOptionPane.INFORMATION_MESSAGE);

            backToListing();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, TextManager.getLabel("client.message.field.number.error"), titleError, JOptionPane.ERROR_MESSAGE);
        }
    }

    private void backToListing() {
        dispose();
        SwingUtilities.invokeLater(() -> new ListClientView().setVisible(true));
    }

}