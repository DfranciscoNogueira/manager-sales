package br.com.manager.ui.client;

import br.com.manager.dto.ClientDTO;
import br.com.manager.util.TextManager;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class ButtonEditor extends DefaultCellEditor {

    private final ListClientView view;
    private final JPanel panel;

    private int selectedRow;

    public ButtonEditor(JCheckBox checkBox, ListClientView view) {

        super(checkBox);

        this.view = view;

        this.panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 10));

        JButton btnEdit = new JButton(TextManager.getLabel("default.btn.edit"));
        btnEdit.setBackground(Color.BLUE);
        btnEdit.setForeground(Color.WHITE);
        btnEdit.addActionListener(e -> editClient());

        JButton btnDelete = new JButton(TextManager.getLabel("default.btn.delete"));
        btnDelete.setBackground(Color.RED);
        btnDelete.setForeground(Color.WHITE);
        btnDelete.addActionListener(e -> deleteCustomer());

        this.panel.add(btnEdit);
        this.panel.add(btnDelete);
    }

    private void editClient() {
        Long id = (Long) this.view.getTableClient().getValueAt(this.selectedRow, 0);
        Optional<ClientDTO> client = this.view.getClientService().findById(id);
        new RegistrationClientView(client.orElse(null)).setVisible(true);
        this.view.dispose();
    }

    private void deleteCustomer() {
        Long id = (Long) this.view.getTableClient().getValueAt(this.selectedRow, 0);
        this.view.getClientService().delete(id);
        this.view.loadClients();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.selectedRow = row;
        return this.panel;
    }

}
