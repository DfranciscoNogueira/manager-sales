package br.com.manager.ui.sale;

import br.com.manager.dto.SalesDTO;
import br.com.manager.util.TextManager;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class ButtonEditor extends DefaultCellEditor {

    private final ListSaleView view;
    private final JPanel panel;

    private int selectedRow;

    public ButtonEditor(JCheckBox checkBox, ListSaleView view) {

        super(checkBox);

        this.view = view;

        this.panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 10));

        JButton btnEdit = new JButton(TextManager.getLabel("default.btn.edit"));
        btnEdit.setBackground(Color.BLUE);
        btnEdit.setForeground(Color.WHITE);
        btnEdit.addActionListener(e -> editSale());

        JButton btnDelete = new JButton(TextManager.getLabel("default.btn.delete"));
        btnDelete.setBackground(Color.RED);
        btnDelete.setForeground(Color.WHITE);
        btnDelete.addActionListener(e -> deleteSale());

        this.panel.add(btnEdit);
        this.panel.add(btnDelete);
    }

    private void editSale() {
        Long id = (Long) this.view.getTableSales().getValueAt(this.selectedRow, 0);
        Optional<SalesDTO> sale = this.view.getSalesService().findById(id);
        new RegistrationSaleView(sale.orElse(null)).setVisible(true);
        this.view.dispose();
    }

    private void deleteSale() {
        Long id = (Long) this.view.getTableSales().getValueAt(this.selectedRow, 0);
        this.view.getSalesService().delete(id);
        this.view.loadSales();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.selectedRow = row;
        return this.panel;
    }

}
