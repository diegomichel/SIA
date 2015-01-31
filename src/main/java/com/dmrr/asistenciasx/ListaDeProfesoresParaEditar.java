/*
 * TODO: Asignar materia a otro profesor.
 */
package com.dmrr.asistenciasx;

import bareMysqlTables.Carrera;
import bareMysqlTables.Profesor;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.codehaus.plexus.util.StringUtils;
import org.jdesktop.beansbinding.BindingGroup;

/**
 *
 * @author diego
 */
public class ListaDeProfesoresParaEditar extends javax.swing.JFrame {

    /**
     * Creates new form ListaDeProfesoresParaEditar
     */
    EntityManager em;
    ListaDeHorariosDeProfesor ventanaListaDeHorarios;

    public ListaDeProfesoresParaEditar() {
        try {
            em = javax.persistence.Persistence.createEntityManagerFactory("asistenciasx?zeroDateTimeBehavior=convertToNullPU").createEntityManager();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Al parece MySQL esta apagado, por favor inicie MySQL y luego ejecute este programa.");
        }
        initComponents();
        ventanaListaDeHorarios = new ListaDeHorariosDeProfesor();

        jTable1.getDefaultEditor(String.class).addCellEditorListener(
                new CellEditorListener() {
                    @Override
                    public void editingCanceled(ChangeEvent e) {
                    }

                    @Override
                    public void editingStopped(ChangeEvent e) {
                        entityManager.getTransaction().begin();
                        entityManager.getTransaction().commit();
                        inicializarTabla();
                        if (parent != null) {
                            parent.setEstado("Se guardaron los datos");
                        }
                    }
                });
    }

    Main parent;

    ListaDeProfesoresParaEditar(Main aThis) {
        this();
        parent = aThis;
    }

    public void inicializarTabla() {
        BindingGroup bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        entityManager = java.beans.Beans.isDesignTime() ? null : javax.persistence.Persistence.createEntityManagerFactory("asistenciasx?zeroDateTimeBehavior=convertToNullPU").createEntityManager();
        profesorQuery = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT p FROM Profesor p");
        profesorList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : profesorQuery.getResultList();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, profesorList, jTable1);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idprofesor}"));
        columnBinding.setColumnName("Idprofesor");
        columnBinding.setColumnClass(Integer.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${nombres}"));
        columnBinding.setColumnName("Nombres");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${apellidos}"));
        columnBinding.setColumnName("Apellidos");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${email}"));
        columnBinding.setColumnName("Email");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${telefono}"));
        columnBinding.setColumnName("Telefono");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${celular}"));
        columnBinding.setColumnName("Celular");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${foto}"));
        columnBinding.setColumnName("Foto");
        columnBinding.setColumnClass(byte[].class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${huella}"));
        columnBinding.setColumnName("Huella");
        columnBinding.setColumnClass(byte[].class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${huellavirdi}"));
        columnBinding.setColumnName("HuellaVirdi");
        columnBinding.setColumnClass(String.class);

        bindingGroup.addBinding(jTableBinding);

        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        bindingGroup.bind();
        filtroDeTabla = new FiltroDeTabla(jTable1, jTextField1);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        entityManager = java.beans.Beans.isDesignTime() ? null : javax.persistence.Persistence.createEntityManagerFactory("asistenciasx?zeroDateTimeBehavior=convertToNullPU").createEntityManager();
        profesorQuery = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT p FROM Profesor p");
        profesorList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : profesorQuery.getResultList();
        jButtonObtenerProfesores = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButtonCapturaHuella = new javax.swing.JButton();
        jButtonCapturaFoto = new javax.swing.JButton();
        jLabelFotoDeProfesor = new javax.swing.JLabel();
        jButtonHorarios = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jButtonCapturaHuella1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButtonObtenerProfesores.setText("Obtener");
        jButtonObtenerProfesores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonObtenerProfesoresActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable1KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTable1KeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jButtonCapturaHuella.setText("Capturar Huella DigitaPersona");
        jButtonCapturaHuella.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCapturaHuellaActionPerformed(evt);
            }
        });

        jButtonCapturaFoto.setText("Capturar Foto");
        jButtonCapturaFoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCapturaFotoActionPerformed(evt);
            }
        });

        jLabelFotoDeProfesor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButtonHorarios.setText("Horarios");
        jButtonHorarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonHorariosActionPerformed(evt);
            }
        });

        jButtonCapturaHuella1.setText("Capturar Huella VIRDI");
        jButtonCapturaHuella1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCapturaHuella1ActionPerformed(evt);
            }
        });

        jButton2.setText("Eliminar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonObtenerProfesores))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 687, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelFotoDeProfesor, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButtonHorarios, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButtonCapturaFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jButtonCapturaHuella1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButtonCapturaHuella, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jButton2)
                                .addGap(43, 43, 43)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonObtenerProfesores)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelFotoDeProfesor, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonCapturaHuella1)
                        .addGap(22, 22, 22)
                        .addComponent(jButtonCapturaHuella)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonCapturaFoto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonHorarios)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 515, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    FiltroDeTabla filtroDeTabla;
    private void jButtonObtenerProfesoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonObtenerProfesoresActionPerformed
        inicializarTabla();
    }//GEN-LAST:event_jButtonObtenerProfesoresActionPerformed

    private void jButtonCapturaFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCapturaFotoActionPerformed
        int x = jTable1.getSelectedRow();
        if (x == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un profesor primero", "Datos incompletos",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        Integer idProfesor = Integer.parseInt(jTable1.getValueAt(x, 0).toString());
        TomarFoto nuevaFoto = new TomarFoto(idProfesor, this);
        nuevaFoto.setVisible(true);
    }//GEN-LAST:event_jButtonCapturaFotoActionPerformed

    private void jButtonCapturaHuellaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCapturaHuellaActionPerformed
        int x = jTable1.getSelectedRow();
        if (x == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un profesor primero", "Datos incompletos",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        Integer idProfesor = Integer.parseInt(jTable1.getValueAt(x, 0).toString());
        CapturaHuella nuevaHuella = new CapturaHuella(idProfesor, this);
        nuevaHuella.setVisible(true);
    }//GEN-LAST:event_jButtonCapturaHuellaActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        try {
            // TODO add your handling code here:
            if (jTable1.getValueAt(jTable1.getSelectedRow(), 6) == null) {
                jLabelFotoDeProfesor.setIcon(new ImageIcon(getClass().getResource("/face-smile_120.png")));
                return;
            }
            final BufferedImage image = ImageIO.read(new ByteArrayInputStream((byte[]) jTable1.getValueAt(jTable1.getSelectedRow(), 6)));
            ImageIcon icon = new ImageIcon(image.getScaledInstance(160, 120, BufferedImage.SCALE_SMOOTH));
            jLabelFotoDeProfesor.setIcon(icon);
        } catch (IOException ex) {
            Logger.getLogger(ListaDeProfesoresParaEditar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButtonHorariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonHorariosActionPerformed
        int row = jTable1.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(rootPane, "Seleccione una fila");
            return;
        }
        Integer profesorId = (Integer) jTable1.getValueAt(row, 0);

        ventanaListaDeHorarios.setIdProfesor(profesorId);
        ventanaListaDeHorarios.cargaHorariosEnJTable();
        ventanaListaDeHorarios.setListaProfesores(this);
        jScrollPane1.setViewportView(ventanaListaDeHorarios.getRootPane());
    }//GEN-LAST:event_jButtonHorariosActionPerformed

    private void jButtonCapturaHuella1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCapturaHuella1ActionPerformed
        int x = jTable1.getSelectedRow();
        if (x == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un profesor primero", "Datos incompletos",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        Integer idProfesor = Integer.parseInt(jTable1.getValueAt(x, 0).toString());

        VirdiFingerPrintSensor sensor = new VirdiFingerPrintSensor();
        String huellaVirdi = sensor.capturaHuella(idProfesor);
        sensor.close();

        Profesor profesor;
        profesor = em.find(Profesor.class, idProfesor);

        if (profesor != null) {
            em.getTransaction().begin();
            profesor.setHuellavirdi(huellaVirdi);
            em.getTransaction().commit();
            inicializarTabla();
        } else {
            JOptionPane.showMessageDialog(rootPane, "No existe el profesor con ese id");
        }
    }//GEN-LAST:event_jButtonCapturaHuella1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int x = jTable1.getSelectedRow();
        if (x == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un profesor primero", "Datos incompletos",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        Integer idProfesor = Integer.parseInt(jTable1.getValueAt(x, 0).toString());
        Profesor profesor;
        profesor = em.find(Profesor.class, idProfesor);
        String datos = ReflectionToStringBuilder.toString(profesor);
        String[] pieces = datos.split(",");
        pieces[0] = "";
        datos = StringUtils.join(pieces, "<br>");
        int response = JOptionPane.showConfirmDialog(null, "<html>Estas seguro de querer borrar a "
                + "<b>" + profesor.getNombres() + " " + profesor.getApellidos() + "</b><br />"
                + datos + "<html>", "Aceptar",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.YES_OPTION) {

            em.getTransaction().begin();
            em.remove(profesor);
            em.getTransaction().commit();
            inicializarTabla();

        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTable1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1KeyTyped

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        // TODO add your handling code here:
        System.out.println("a key have been pressed");
    }//GEN-LAST:event_jTable1KeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ListaDeProfesoresParaEditar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ListaDeProfesoresParaEditar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.persistence.EntityManager entityManager;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButtonCapturaFoto;
    private javax.swing.JButton jButtonCapturaHuella;
    private javax.swing.JButton jButtonCapturaHuella1;
    private javax.swing.JButton jButtonHorarios;
    private javax.swing.JButton jButtonObtenerProfesores;
    private javax.swing.JLabel jLabelFotoDeProfesor;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private java.util.List<bareMysqlTables.Profesor> profesorList;
    private javax.persistence.Query profesorQuery;
    // End of variables declaration//GEN-END:variables

    public void setjScrollBack() {
        this.jScrollPane1.setViewportView(jTable1);
    }
}
