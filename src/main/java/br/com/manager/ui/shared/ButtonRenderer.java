package br.com.manager.ui.shared;

import br.com.manager.util.TextManager;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ButtonRenderer extends JPanel implements TableCellRenderer {

    public ButtonRenderer() {

        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 2));
        JButton btnEdit = new JButton(TextManager.getLabel("default.btn.edit"));
        btnEdit.setBackground(Color.BLUE);
        btnEdit.setForeground(Color.WHITE);

        JButton btnDelete = new JButton(TextManager.getLabel("default.btn.delete"));
        btnDelete.setBackground(Color.RED);
        btnDelete.setForeground(Color.WHITE);

        add(btnEdit);
        add(btnDelete);

    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return this;
    }

}
