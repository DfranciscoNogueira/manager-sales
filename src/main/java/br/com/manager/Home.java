package br.com.manager;

import br.com.manager.repository.GenericRepository;
import br.com.manager.ui.shared.Menu;
import br.com.manager.util.Constants;
import br.com.manager.util.TextManager;
import br.com.manager.util.Thema;

import javax.swing.*;
import java.awt.*;


public class Home {

    private static final String TITLE = TextManager.getLabel("title");

    public static void main(String[] args) {

        GenericRepository<Object, Long> repository = new GenericRepository<Object, Long>(Object.class) {};
        repository.isDatabaseOnline();

        // Criando a janela
        JFrame frame = new JFrame(TITLE);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH); // Maximiza a tela
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Criando painel menu
        JPanel menuPanel = new Menu(frame);

        // Criando painel central
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Thema.SECONDARY_COLOR);

        JLabel labelWelcome = new JLabel(TITLE, SwingConstants.CENTER);
        labelWelcome.setFont(new Font("Arial", Font.BOLD, 24));
        labelWelcome.setForeground(Thema.MENU_COLOR);

        // Adicionando imagem responsiva
        ImageIcon icon = new ImageIcon(Constants.PATH_IMAGES + "home.png");
        JLabel imgLabel = new JLabel();
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imgLabel.setIcon(new ImageIcon(icon.getImage().getScaledInstance(600, 400, Image.SCALE_SMOOTH)));

        mainPanel.add(labelWelcome, BorderLayout.NORTH);
        mainPanel.add(imgLabel, BorderLayout.CENTER);

        // Adicionando os painéis à janela
        frame.add(menuPanel, BorderLayout.WEST);
        frame.add(mainPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

}