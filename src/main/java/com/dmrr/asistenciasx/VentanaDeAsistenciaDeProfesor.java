/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dmrr.asistenciasx;

import bareMysqlTables.Profesor;
import bareMysqlTables.ProfesorAcceso;
import bareMysqlTables.Registro;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import static javax.swing.JTable.AUTO_RESIZE_OFF;
import static javax.swing.JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.config.ResultType;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.swingbinding.JTableBinding;
import org.jdesktop.swingbinding.JTableBinding.ColumnBinding;
import org.jdesktop.swingbinding.SwingBindings;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import sun.swing.table.DefaultTableCellHeaderRenderer;

/**
 *
 * @author diego
 */
public final class VentanaDeAsistenciaDeProfesor extends javax.swing.JFrame {
    /*
     Variables de configuracion de comportamiento
     */

    Integer MinutosAntesDeLaHora = 5;
    Integer MinutosDespuesDeLaHora = 15;
    Boolean permitirDobleRegistro = false;

    /**
     * Creates new form VentanaDeAsistenciaDeProfesor
     *
     * @param profesor
     */
    EntityManager em;
    DefaultTableModel modelTablaHorarios;
    private List<Map> results;
    DateTime dateTime;
    Profesor profesor;

    /*
     Imagenes
     */
    ImageIcon okImage;
    ImageIcon clockImage;
    ImageIcon infoImage;

    public VentanaDeAsistenciaDeProfesor() {
        constructorCalls();
    }

    private void constructorCalls() {
        initComponents();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loadImages();
        jLabelResultadoDeRegistro.setVerticalTextPosition(AbstractButton.BOTTOM);
        jLabelResultadoDeRegistro.setHorizontalTextPosition(AbstractButton.CENTER);
    }

    void loadImages() {
        okImage = new ImageIcon(getClass().getResource("/ok_128.png"));
        clockImage = new ImageIcon(getClass().getResource("/clock_128.png"));
        infoImage = new ImageIcon(getClass().getResource("/info_128.png"));
        jLabelResultadoDeRegistro.setIcon(infoImage);
    }

    void llenaVentanaDeDatos(Profesor profesor) {
        this.profesor = profesor;
        dateTime = new DateTime();
        em = javax.persistence.Persistence.createEntityManagerFactory("asistenciasx?zeroDateTimeBehavior=convertToNullPU").createEntityManager();

        jPanelDatosProfesor.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Bienvenido " + profesor.getNombres() + " " + profesor.getApellidos(), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18), new java.awt.Color(102, 0, 0)));

        jLabelHoraDeEntrada.setText("");
        jLabelNombreDeMateria.setText("");
        jLabelSalon.setText("");
        
        loadFoto(profesor);
        cargaHorariosEnJTable();
        checaHoraDeEntrada();
        registraAccesoDeProfesor(profesor.getIdprofesor());
    }

    public VentanaDeAsistenciaDeProfesor(Profesor profesor) {
        this.constructorCalls();
        this.llenaVentanaDeDatos(profesor);
    }

    private void convertHorariosFromBooleanToHours(Map row, String dia) {
        if (row.get(dia) == null) {
            return;
        }
        if (row.get(dia).equals(true)) {
            row.replace(dia, row.get("horaEntrada") + "-" + row.get("horaSalida"));
        }
        if (row.get(dia).equals(false)) {
            row.replace(dia, "");
        }
    }

    private void cargaHorariosEnJTable() {
        Query queryHorariosPorProfesor = em.createNativeQuery("Select * from horariosporprofesor WHERE idprofesor=" + profesor.getIdprofesor() + " order by " + MyUtils.getTodayString3Char() + " desc, horaEntrada asc");
        queryHorariosPorProfesor.setHint(QueryHints.RESULT_TYPE, ResultType.Map);
        System.out.println(queryHorariosPorProfesor);
        results = queryHorariosPorProfesor.getResultList();

        for (Map row : results) {
            convertHourFromH55toH00Ejemplo855to900(row);
            convertHorariosFromBooleanToHours(row, "lun");
            convertHorariosFromBooleanToHours(row, "mar");
            convertHorariosFromBooleanToHours(row, "mie");
            convertHorariosFromBooleanToHours(row, "jue");
            convertHorariosFromBooleanToHours(row, "vie");
            convertHorariosFromBooleanToHours(row, "sab");
        }

        JTableBinding jTableBinding;
        jTableBinding = SwingBindings.createJTableBinding(AutoBinding.UpdateStrategy.READ, results, jTable3);

        ColumnBinding columnBinding;

        columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${idcurso}"));
        columnBinding.setColumnName("NRC");
        columnBinding.setColumnClass(String.class);

        columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${nombre}"));
        columnBinding.setColumnName("Materia");
        columnBinding.setColumnClass(String.class);

        columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${lun}"));
        columnBinding.setColumnName("Lunes");
        columnBinding.setColumnClass(String.class);

        columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${mar}"));
        columnBinding.setColumnName("Martes");
        columnBinding.setColumnClass(String.class);

        columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${mie}"));
        columnBinding.setColumnName("Miercoles");
        columnBinding.setColumnClass(String.class);

        columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${jue}"));
        columnBinding.setColumnName("Jueves");
        columnBinding.setColumnClass(String.class);

        columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${vie}"));
        columnBinding.setColumnName("Viernes");
        columnBinding.setColumnClass(String.class);

        columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${sab}"));
        columnBinding.setColumnName("Sabado");
        columnBinding.setColumnClass(String.class);

        columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${edif} ${aula}"));
        columnBinding.setColumnName("Salon");
        columnBinding.setColumnClass(String.class);

        jTableBinding.bind();

        jTable3.setAutoResizeMode(AUTO_RESIZE_OFF);
        for (int i = 0; i < 7; i++) {
            if (i == 0) {
                jTable3.getColumnModel().getColumn(i).setPreferredWidth(60);
            }
            if (i == 1) {
                jTable3.getColumnModel().getColumn(i).setPreferredWidth(250);
            }
        }
        jTable3.setAutoResizeMode(AUTO_RESIZE_SUBSEQUENT_COLUMNS);

        DefaultTableCellRenderer centerRendererForCells = new DefaultTableCellRenderer();
        DefaultTableCellRenderer centerRendererForHeaders = new DefaultTableCellHeaderRenderer();
        centerRendererForCells.setHorizontalAlignment(JLabel.CENTER);
        centerRendererForHeaders.setHorizontalAlignment(JLabel.CENTER);

        DefaultTableCellRenderer cellRendererForToday = new DefaultTableCellRenderer();
        cellRendererForToday.setBorder(BorderFactory.createLineBorder(Color.yellow, 5));
        cellRendererForToday.setForeground(Color.red);
        cellRendererForToday.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 2; i < 8; i++) {
            jTable3.getColumnModel().getColumn(i).setCellRenderer(centerRendererForCells);
            jTable3.getColumnModel().getColumn(i).setHeaderRenderer(centerRendererForHeaders);
            if (i + 2 == MyUtils.getDayOfWeek() + 2) {
                jTable3.getColumnModel().getColumn(i).setCellRenderer(cellRendererForToday);
                jTable3.getColumnModel().getColumn(i).setHeaderRenderer(cellRendererForToday);
            }
        }
    }

    private void loadFoto(Profesor profesor) {
        try {
            if (profesor.getFoto() == null) {
                ImageIcon icon = new ImageIcon(this.getClass().getResource("/face-smile_120.png"));
                jLabelFotoDeProfesor.setIcon(icon);
                return;
            }
            final BufferedImage image = ImageIO.read(new ByteArrayInputStream(profesor.getFoto()));
            ImageIcon icon = new ImageIcon(image.getScaledInstance(160, 120, BufferedImage.SCALE_SMOOTH));
            jLabelFotoDeProfesor.setIcon(icon);
        } catch (IOException ex) {
            Logger.getLogger(VentanaDeAsistenciaDeProfesor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelDatosProfesor = new javax.swing.JPanel();
        jLabelResultadoDeRegistro = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabelFotoDeProfesor = new javax.swing.JLabel();
        jLabelNombreDeMateria = new javax.swing.JLabel();
        jLabelSalon = new javax.swing.JLabel();
        jLabelHoraDeEntrada = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanelDatosProfesor.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Bienvenido Walter Wynter", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18), new java.awt.Color(102, 0, 0))); // NOI18N

        jLabelResultadoDeRegistro.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabelResultadoDeRegistro.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelResultadoDeRegistro.setText("Tiene que registrar su asistencia 15 minutos antes o despues de la hora");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Materia:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Hora:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Salon:");

        jLabelNombreDeMateria.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelNombreDeMateria.setText("          ");

        jLabelSalon.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelSalon.setText("          ");

        jLabelHoraDeEntrada.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelHoraDeEntrada.setText("          ");

        javax.swing.GroupLayout jPanelDatosProfesorLayout = new javax.swing.GroupLayout(jPanelDatosProfesor);
        jPanelDatosProfesor.setLayout(jPanelDatosProfesorLayout);
        jPanelDatosProfesorLayout.setHorizontalGroup(
            jPanelDatosProfesorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDatosProfesorLayout.createSequentialGroup()
                .addGroup(jPanelDatosProfesorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelResultadoDeRegistro, javax.swing.GroupLayout.DEFAULT_SIZE, 1118, Short.MAX_VALUE)
                    .addGroup(jPanelDatosProfesorLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanelDatosProfesorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelDatosProfesorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelNombreDeMateria)
                            .addComponent(jLabelSalon)
                            .addComponent(jLabelHoraDeEntrada))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelFotoDeProfesor, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanelDatosProfesorLayout.setVerticalGroup(
            jPanelDatosProfesorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDatosProfesorLayout.createSequentialGroup()
                .addGroup(jPanelDatosProfesorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDatosProfesorLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanelDatosProfesorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabelNombreDeMateria))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelDatosProfesorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabelHoraDeEntrada))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelDatosProfesorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabelSalon)))
                    .addComponent(jLabelFotoDeProfesor, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabelResultadoDeRegistro, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTable3.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable3.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTable3.setRowHeight(22);
        jScrollPane3.setViewportView(jTable3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelDatosProfesor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelDatosProfesor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaDeAsistenciaDeProfesor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaDeAsistenciaDeProfesor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaDeAsistenciaDeProfesor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaDeAsistenciaDeProfesor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //TODO Code for testing porpouses.
                EntityManager em;
                em = javax.persistence.Persistence.createEntityManagerFactory("asistenciasx?zeroDateTimeBehavior=convertToNullPU").createEntityManager();
                Profesor profesor = em.find(Profesor.class, 2732238);
                new VentanaDeAsistenciaDeProfesor(profesor).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabelFotoDeProfesor;
    private javax.swing.JLabel jLabelHoraDeEntrada;
    private javax.swing.JLabel jLabelNombreDeMateria;
    private javax.swing.JLabel jLabelResultadoDeRegistro;
    private javax.swing.JLabel jLabelSalon;
    private javax.swing.JPanel jPanelDatosProfesor;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable3;
    // End of variables declaration//GEN-END:variables

    private void convertHourFromH55toH00Ejemplo855to900(Map row) {
        if (row.get("horaSalida") != null) {
            String salida = row.get("horaSalida").toString();
            if (salida.charAt(salida.length() - 1) == '5') {
                String hora = "";
                if (salida.length() == 3) {
                    hora = Character.getNumericValue(salida.charAt(0)) + "";
                }
                if (salida.length() == 4) {
                    hora = Character.getNumericValue(salida.charAt(0)) + "" + Character.getNumericValue(salida.charAt(1));
                }
                hora = (Integer.parseInt(hora) + 1) + "";
                row.replace("horaSalida", hora + "00");
            }
        }
    }

    private void checaHoraDeEntrada() {
        jLabelResultadoDeRegistro.setOpaque(true);

        if (dateTime.getMinuteOfHour() <= MinutosDespuesDeLaHora || dateTime.getMinuteOfHour() >= 60 - MinutosAntesDeLaHora) {
            if (yaSeRegistroAsistencia()) {
                jLabelResultadoDeRegistro.setIcon(okImage);
                jLabelResultadoDeRegistro.setText("Ya ha registrado su asistencia!");
                return;
            }
            checaMateriasQueCoincidadConLaHoraDeEntrada();
        } else {
            jLabelResultadoDeRegistro.setIcon(clockImage);
            jLabelResultadoDeRegistro.setText("Solo puede registrarse 15 minutos antes o despues de la hora!");
        }
    }

    private void checaMateriasQueCoincidadConLaHoraDeEntrada() {
        Integer hora = dateTime.getHourOfDay();
        if (dateTime.getMinuteOfHour() >= 45) {
            hora++;
        }
        String horaString = hora + "00";

        boolean checaHoraConsecutiva = false;
        for (Map row : results) {
            switch (MyUtils.getDayOfWeek()) {
                case 2:
                    if (row.get("lun") == null) {
                        continue;
                    }
                    break;
                case 3:
                    if (row.get("mar") == null) {
                        continue;
                    }
                    break;
                case 4:
                    if (row.get("mie") == null) {
                        continue;
                    }
                    break;
                case 5:
                    if (row.get("jue") == null) {
                        continue;
                    }
                    break;
                case 6:
                    if (row.get("vie") == null) {
                        continue;
                    }
                    break;
                case 7:
                    if (null == row.get("sab")) {
                        continue;
                    }
                    break;
            }
            String horaEntrada = "" + row.get("horaEntrada");
            if (checaHoraConsecutiva && permitirDobleRegistro) {
                System.out.println("Checando hora consecutiva");
                System.out.println("horaEntrada" + horaEntrada + " horaString:" + horaString);
                horaString = (hora + 1) + "00";
                if (horaEntrada.equals(horaString)) {
                    generaAsistencia(row);
                } else {
                    horaString = (hora + 2) + "00";
                    if (horaEntrada.equals(horaString)) {
                        generaAsistencia(row);
                    }
                }
                break;
            } else if (horaEntrada.equals(horaString)) {
                jLabelNombreDeMateria.setText(row.get("nombre") + "");
                jLabelHoraDeEntrada.setText(row.get("horaEntrada") + "-" + row.get("horaSalida"));
                jLabelSalon.setText(row.get("edif") + " " + row.get("aula"));
                jLabelResultadoDeRegistro.setIcon(okImage);
                jLabelResultadoDeRegistro.setText("Se ha registrado su asistencia!");

                generaAsistencia(row);
                checaHoraConsecutiva = true;
            }
        }
        if (!jLabelResultadoDeRegistro.getText().equals("Se ha registrado su asistencia!")) {
            jLabelResultadoDeRegistro.setIcon(infoImage);
            jLabelResultadoDeRegistro.setText("No tiene materia a esta hora!");
        }
    }

    private void registraAccesoDeProfesor(Integer idProfesor) {
        ProfesorAcceso profesorAcceso = new ProfesorAcceso();
        profesorAcceso.setIdprofesor(idProfesor);
        profesorAcceso.setFechayhora(dateTime.toDate());

        em.getTransaction().begin();
        em.persist(profesorAcceso);
        em.getTransaction().commit();
    }

    private void generaAsistencia(Map row) {

        Registro registro = new Registro();
        registro.setFechayhora(dateTime.toDate());
        registro.setIdhorario(Integer.parseInt(row.get("idhorario") + ""));
        em.getTransaction().begin();
        em.persist(registro);
        em.getTransaction().commit();
    }

    private boolean yaSeRegistroAsistencia() {
        dateTime = new DateTime();
        Integer minuto = Integer.parseInt(dateTime.toString(DateTimeFormat.forPattern("MM")));
        String fechayhora;
        if(minuto >=45){
            dateTime.plusHours(1);
            fechayhora = dateTime.toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:00:00"));
            dateTime.minusHours(1);
        }else{
            fechayhora = dateTime.toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:00:00"));
        }
        
        //String fechayhora = dateTime.toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:00:00"));
        Query yaSeRegistro = em.createNativeQuery("SELECT * FROM registrodeasistencias WHERE idprofesor = " + profesor.getIdprofesor() + " AND fechayhora BETWEEN '" + fechayhora + "' - INTERVAL 15 MINUTE AND '" + fechayhora + "' + INTERVAL 15 MINUTE");
        System.out.println(yaSeRegistro.toString());
        try {
            Object x = yaSeRegistro.getSingleResult();
        } catch (javax.persistence.NoResultException e) {
            return false;
        } catch (javax.persistence.NonUniqueResultException ex) {
            return true;
        }
        return true;
    }
}
