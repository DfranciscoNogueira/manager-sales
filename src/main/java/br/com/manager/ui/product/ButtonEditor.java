package br.com.manager.ui.product;

import br.com.manager.dto.ProductDTO;
import br.com.manager.util.TextManager;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class ButtonEditor extends DefaultCellEditor {

    private final ListProductView view;
    private final JPanel panel;

    private int selectedRow;

    public ButtonEditor(JCheckBox checkBox, ListProductView view) {

        super(checkBox);

        this.view = view;

        this.panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 10));

        JButton btnEdit = new JButton(TextManager.getLabel("default.btn.edit"));
        btnEdit.setBackground(Color.BLUE);
        btnEdit.setForeground(Color.WHITE);
        btnEdit.addActionListener(e -> editProduct());

        JButton btnDelete = new JButton(TextManager.getLabel("default.btn.delete"));
        btnDelete.setBackground(Color.RED);
        btnDelete.setForeground(Color.WHITE);
        btnDelete.addActionListener(e -> deleteProduct());

        this.panel.add(btnEdit);
        this.panel.add(btnDelete);
    }

    private void editProduct() {
        Long id = (Long) this.view.getTableProduct().getValueAt(this.selectedRow, 0);
        Optional<ProductDTO> product = this.view.getProductService().findById(id);
        new RegistrationProductView(product.orElse(null)).setVisible(true);
        this.view.dispose();
    }

    private void deleteProduct() {
        Long id = (Long) view.getTableProduct().getValueAt(this.selectedRow, 0);
        this.view.getProductService().delete(id);
        this.view.loadProducts();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.selectedRow = row;
        return this.panel;
    }

}
