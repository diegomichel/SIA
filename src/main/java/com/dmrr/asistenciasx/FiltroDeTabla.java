/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dmrr.asistenciasx;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author diego
 */
public class FiltroDeTabla {

    RowFilter rf = null;
    TableRowSorter sorter;
    JTextField textField;
    JTable tabla;

    public void refresh() {
        sorter = new TableRowSorter(tabla.getModel());
        tabla.setRowSorter(sorter);

        textField.getDocument().addDocumentListener(
                new DocumentListener() {
                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        rf = RowFilter.regexFilter(textField.getText().toUpperCase(), 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14);
                        sorter.setRowFilter(rf);
                    }

                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        rf = RowFilter.regexFilter(textField.getText().toUpperCase(), 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14);
                        sorter.setRowFilter(rf);
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        rf = RowFilter.regexFilter(textField.getText().toUpperCase(), 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14);
                        sorter.setRowFilter(rf);
                    }
                });
    }

    public FiltroDeTabla(JTable tabla, JTextField text) {
        this.textField = text;
        this.tabla = tabla;
        textField.setText("");
        refresh();
    }
}
