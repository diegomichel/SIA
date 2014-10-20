/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dmrr.asistenciasx;

import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.capture.event.DPFPDataAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPDataEvent;
import com.digitalpersona.onetouch.capture.event.DPFPErrorAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPErrorEvent;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusEvent;
import com.digitalpersona.onetouch.capture.event.DPFPSensorAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPSensorEvent;
import com.digitalpersona.onetouch.processing.DPFPEnrollment;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;
import com.digitalpersona.onetouch.verification.DPFPVerification;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 *
 * @author diego
 */
public final class MonitorDeHuella extends javax.swing.JFrame implements Runnable {

    DPFPCapture lectorDeHuella = DPFPGlobal.getCaptureFactory().createCapture();
    DPFPEnrollment reclutadorDeHuella = DPFPGlobal.getEnrollmentFactory().createEnrollment();
    DPFPVerification Verificador = DPFPGlobal.getVerificationFactory().createVerification();
    DPFPTemplate template;
    public static String TEMPLATE_PROPERTY = "template"; //TODO: wtf

    JTextArea logTextArea;
    ListaYCapturaDeAsistencias listaYCapturaDeAsistencias;

    public MonitorDeHuella(JTextArea logTextArea) {
        this.logTextArea = logTextArea;
        iniciarListenersDelLector();
        start();
    }
    public MonitorDeHuella(JTextArea logTextArea, ListaYCapturaDeAsistencias listaYCapturaDeAsistencias){
        this.logTextArea = logTextArea;
        this.listaYCapturaDeAsistencias = listaYCapturaDeAsistencias;
        iniciarListenersDelLector();
        start();
    }

    public void iniciarListenersDelLector() {
        lectorDeHuella.addDataListener(new DPFPDataAdapter() {
            @Override
            public void dataAcquired(final DPFPDataEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        logTextArea.append("Huella capturada\n");
                        procesarCaptura(e.getSample());
                        //TODO: We can identify the huella con la base de datos aqui
                        reclutadorDeHuella.clear();
                    }
                });
            }
        });

        lectorDeHuella.addReaderStatusListener(new DPFPReaderStatusAdapter() {
            @Override
            public void readerConnected(final DPFPReaderStatusEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        logTextArea.append("Lector de huella conectado\n");
                    }
                });
            }

            @Override
            public void readerDisconnected(final DPFPReaderStatusEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        logTextArea.append("Lector de huella desconectado\n");
                    }
                });
            }
        });

        lectorDeHuella.addSensorListener(new DPFPSensorAdapter() {
            @Override
            public void fingerTouched(final DPFPSensorEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        logTextArea.append("Dedo tocando el lector\n");
                        //TODO: Tell profesor not to remove finger.
                    }
                });
            }

            @Override
            public void fingerGone(final DPFPSensorEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        logTextArea.append("Dedo removido del lector\n");
                    }
                });
            }
            
        });

        lectorDeHuella.addErrorListener(new DPFPErrorAdapter() {
            public void errorReader(final DPFPErrorEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        logTextArea.append("Error: " + e.getError() + "\n");
                    }
                });
            }
        });
    }

    public void start() {
        lectorDeHuella.startCapture();
        logTextArea.append("Iniciando el lector de huella digital\n");
    }

    public DPFPFeatureSet caracteristicasParaAlta, caracteristicasParaVerificacion;

    public void procesarCaptura(DPFPSample sample) {
        try {
            caracteristicasParaAlta = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction().createFeatureSet(sample, DPFPDataPurpose.DATA_PURPOSE_ENROLLMENT);
            caracteristicasParaVerificacion = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction().createFeatureSet(sample, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);

            if (caracteristicasParaAlta != null) {
                logTextArea.append("Las caracteristicas de la huella han sido creadas\n");
                reclutadorDeHuella.addFeatures(caracteristicasParaAlta);
            }
            
            if(caracteristicasParaVerificacion != null){
                if(this.listaYCapturaDeAsistencias != null)
                {
                    this.listaYCapturaDeAsistencias.checarAsistencia(Verificador,caracteristicasParaVerificacion);
                }
            }

            //logTextArea.append("Huellas necesarias para guardar template: " + reclutadorDeHuella.getFeaturesNeeded() + "\n");

            switch (reclutadorDeHuella.getTemplateStatus()) {
                case TEMPLATE_STATUS_READY:
                    lectorDeHuella.stopCapture();
                    setTemplate(reclutadorDeHuella.getTemplate());
                    //logTextArea.append("La plantilla de la huella ha sido creada, lista para verificar\n");
                    break;
                case TEMPLATE_STATUS_FAILED:
                    reclutadorDeHuella.clear();
                    lectorDeHuella.stopCapture();
                    setTemplate(null);
                    //logTextArea.append("Por favor repita el proceso, la plantilla no pudo ser creada\n");
                    lectorDeHuella.startCapture();
                    break;
            }
        } catch (DPFPImageQualityException ex) {
            Logger.getLogger(MonitorDeHuella.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setTemplate(DPFPTemplate template) {
        firePropertyChange(TEMPLATE_PROPERTY, this.template, template);
        this.template = template;
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
