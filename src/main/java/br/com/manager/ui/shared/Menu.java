package br.com.manager.ui.shared;

import br.com.manager.ui.client.ListClientView;
import br.com.manager.ui.product.ListProductView;
import br.com.manager.ui.sale.ListSaleView;
import br.com.manager.util.Constants;
import br.com.manager.util.TextManager;
import br.com.manager.util.Thema;

import javax.swing.*;
import java.awt.*;

public class Menu extends JPanel {

    private JFrame frameParent;

    public Menu(JFrame frameParent) {

        this.frameParent = frameParent;

        // Configurações do painel
        setPreferredSize(new Dimension(150, frameParent.getHeight()));
        setLayout(new GridLayout(4, 1, 5, 5));
        setBackground(Thema.MENU_COLOR);

        // Adicionando botões ao menu
        add(createButton(TextManager.getLabel("customers"), Constants.PATH_ICONS + "menu_customers.png", this::openPageClients));
        add(createButton(TextManager.getLabel("products"), Constants.PATH_ICONS + "menu_products.png", this::openPageProducts));
        add(createButton(TextManager.getLabel("sales"), Constants.PATH_ICONS + "menu_sales.png", this::openPageSales));

    }

    private JButton createButton(String text, String iconPath, Runnable action) {
        ImageIcon icon = new ImageIcon(iconPath);
        Image img = icon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH); // Define o novo tamanho
        JButton btn = new JButton(text, new ImageIcon(img));
        btn.addActionListener(e -> action.run());
        btn.setBackground(Thema.PRIMARY_COLOR);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(Thema.FONT);
        return btn;
    }

    private void openPageClients() {
        frameParent.dispose(); // Fecha o menu
        new ListClientView().setVisible(true); // Abre a tela de clientes
    }

    private void openPageProducts() {
        frameParent.dispose();
        new ListProductView().setVisible(true);
    }

    private void openPageSales() {
        frameParent.dispose();
        new ListSaleView().setVisible(true);
    }

}
