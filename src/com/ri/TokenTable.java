package com.ri;

import javafx.util.Pair;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * Ventana con la tabla de tokens
 */
public class TokenTable extends JFrame {
    private List<Pair<String, Integer>> tokenTable;

    private JTable table1;
    private JPanel rootPanel;

    public TokenTable(List<Pair<String, Integer>> table) {
        super("Tabla de tokens");
        tokenTable = table;

        initGui();
    }

    private void initGui() {
        setLocationRelativeTo(null);
        setContentPane(rootPanel);
        fillTable();
        pack();
        setVisible(true);
    }

    private void fillTable() {
        String[] headers = { "Token", "Número de documentos" };
        DefaultTableModel dtm = new DefaultTableModel(headers, 0);

        for(Pair<String, Integer> token : tokenTable) {
            dtm.addRow(new Object[] { token.getKey(), token.getValue() });
        }
        table1.setModel(dtm);
    }
}
