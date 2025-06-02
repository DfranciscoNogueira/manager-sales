package br.com.manager.ui.product;


import br.com.manager.dto.ProductDTO;
import br.com.manager.service.ProductService;
import br.com.manager.util.TextManager;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.Objects;

public class RegistrationProductView extends JFrame {

    private static final String PRODUCT_REGISTRATION = TextManager.getLabel("product.registration");
    private static final String DEFAULT_FONT = TextManager.getLabel("default.font");

    private final ProductService productService;
    private final ProductDTO productEdit;

    private JTextField fieldDescription;
    private JTextField fieldPrice;

    public RegistrationProductView(ProductDTO productDTO) {

        this.productService = new ProductService();
        this.productEdit = productDTO;
        setTitle(PRODUCT_REGISTRATION);

        setExtendedState(Frame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        startScreen();

        if (Objects.nonNull(productDTO)) {
            this.fieldDescription.setText(Objects.requireNonNullElse(productDTO.getDescription(), ""));
            this.fieldPrice.setText(Objects.nonNull(productDTO.getPrice()) ? productDTO.getPrice().toString() : "");
        }

    }

    private void startScreen() {

        JPanel panelMain = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JPanel panelInternal = new JPanel();
        panelInternal.setLayout(new GridLayout(4, 1, 10, 10));
        panelInternal.setPreferredSize(new Dimension(350, 200));
        panelInternal.setBorder(BorderFactory.createTitledBorder(PRODUCT_REGISTRATION));

        JLabel labelDescription = new JLabel(TextManager.getLabel("product.label.description"));
        this.fieldDescription = new JTextField();
        this.fieldDescription.setPreferredSize(new Dimension(200, 25));

        JLabel labelPrice = new JLabel(TextManager.getLabel("product.label.price"));
        this.fieldPrice = new JTextField();
        this.fieldPrice.setPreferredSize(new Dimension(200, 25));

        panelInternal.add(labelDescription);
        panelInternal.add(this.fieldDescription);
        panelInternal.add(labelPrice);
        panelInternal.add(this.fieldPrice);

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
        btnSave.addActionListener(e -> saveProduct());

        panelButtons.add(btnBack);
        panelButtons.add(btnSave);

        panelMain.add(panelInternal, gbc);
        gbc.gridy = 1;
        panelMain.add(panelButtons, gbc);

        add(panelMain);
    }

    private void saveProduct() {

        String description = this.fieldDescription.getText().trim();
        String priceStr = this.fieldPrice.getText().trim();

        String titleError = TextManager.getLabel("product.message.error.title");

        if (description.isEmpty() || priceStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, TextManager.getLabel("product.message.field.required"), titleError, JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {

            BigDecimal price = new BigDecimal(priceStr);

            if (Objects.isNull(this.productEdit)) {
                this.productService.save(new ProductDTO(description, price));
            } else {
                this.productService.save(new ProductDTO(this.productEdit.getId(), description, price));
            }

            JOptionPane.showMessageDialog(this, TextManager.getLabel("product.message.success"), TextManager.getLabel("product.message.success.title"), JOptionPane.INFORMATION_MESSAGE);

            backToListing();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, TextManager.getLabel("product.message.field.number.error"), titleError, JOptionPane.ERROR_MESSAGE);
        }

    }

    private void backToListing() {
        dispose();
        SwingUtilities.invokeLater(() -> new ListProductView().setVisible(true));
    }

}