/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dmrr.asistenciasx;

import bareMysqlTables.Profesor;
import com.unioncomm.sdk.bsp.UCBioBSPJNI;
import com.unioncomm.sdk.bsp.UCBioBSPJNI.FIR_HANDLE;
import com.unioncomm.sdk.bsp.UCBioBSPJNI.FastSearch.FP_INFO;
import com.unioncomm.sdk.bsp.UCBioBSPJNI.FastSearch.INIT_INFO;
import com.unioncomm.sdk.bsp.UCBioBSPJNI.INPUT_FIR;
import java.awt.Component;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 *
 * @author diegomichel
 */
public final class VirdiFingerPrintSensor {

    private UCBioBSPJNI bsp;
    UCBioBSPJNI.WINDOW_OPTION wOptions;
    String huella = "";
    UCBioBSPJNI.FastSearch isEngine;
    Integer err;

    public VirdiFingerPrintSensor() {
        inicializaLibreriaDelSensor();
        inicializaSensor();

    }

    private void inicializaSensor() {
        UCBioBSPJNI.DEVICE_ENUM_INFO device_enum_info = bsp.new DEVICE_ENUM_INFO();
        int SetSkinResource = bsp.SetSkinResource("UCBioBSPSkin_Spanish.dll");
        if (SetSkinResource == 1) {
            //All fine
        } else {
            System.out.println("Some error... add skin dlls directory to your PATH system var.");
            JOptionPane.showMessageDialog(null, "Virdi Sensor: Agregue las dll skin de virdi a su variable PATH");
        }

        wOptions = bsp.new WINDOW_OPTION();
        wOptions.WindowStyle = (0x00010000) | (0x00020000);

        int error = bsp.EnumerateDevice(device_enum_info);
        if (error == 1) {
            System.out.println("error al crear lista de dipositivos");
            JOptionPane.showMessageDialog(null, "Virdi Sensor: No Conectado");
        }
        err = bsp.OpenDevice();
        if (err != 0) {
            JOptionPane.showMessageDialog(null, "Asegurese de que el sensor este correctamente instalado y vuelva a intenarlo");
            return;
        }
        bsp.SetAutoDetect(1);
    }

    public void inicializaLibreriaDelSensor() {
        bsp = new UCBioBSPJNI();
        if (bsp.IsErrorOccured()) {
            System.out.println(bsp.GetErrorCode());
            JOptionPane.showMessageDialog(null, "Virdi Sensor: " + bsp.GetErrorCode());
        }
    }

    public String capturaHuella(Integer idProfesor) {
        if (err != 0) {
            return "";
        }
        UCBioBSPJNI.FIR_HANDLE hFIR = bsp.new FIR_HANDLE();
        UCBioBSPJNI.FIR_TEXTENCODE textFir = bsp.new FIR_TEXTENCODE();

        UCBioBSPJNI.FIR_PAYLOAD enrollPayload = bsp.new FIR_PAYLOAD();
        enrollPayload.SetText(idProfesor.toString());

        int ret = bsp.Enroll(null, hFIR, enrollPayload, -1, null, wOptions);
        if (ret == 0) {
            bsp.GetTextFIRFromHandle(hFIR, textFir);
            huella = textFir.TextFIR;
        } else {
            JOptionPane.showMessageDialog(null, "Error al capturar la huella, asegurese de tener conectado el sensor Virdi");
        }
        hFIR.dispose();

        return huella;
    }

    public void close() {
        System.out.println("Liberando recursos virdi");
        bsp.SetAutoDetect(0);
        god = false;
        if (bsp != null) {
            //bsp.CloseDevice();
            bsp = null;
        }
    }

    Boolean god = true;

    public void esperaPorHuella(Component c, final JTextArea t, final ListaYCapturaDeAsistencias w) {
        final UCBioBSPJNI.WINDOW_OPTION winOption = bsp.new WINDOW_OPTION();
        winOption.WindowStyle = 1;
        //winOption.FingerWnd = c;
        //String jreBinPath = System.getProperty("java.home") + "\\bin";
        //winOption.JreBinPath = jreBinPath;

        final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        Runnable waitForFinger = new Runnable() {
            Boolean result = new Boolean(false); //Need to be defined as this or it will crash
            UCBioBSPJNI.FIR_HANDLE hCapturedFIR = bsp.new FIR_HANDLE();

            @Override
            public void run() {
                while (god) {
                    //Si el sensor por alguna razon empieza a fallar aumentar el 3er parametro a algo mas alto, no dejar en 0 por que nunca se libera el sensor
                    //  y falla al tratar de capturar las huellas de los maestros.
                    int ret = bsp.Capture(0x01, hCapturedFIR, 5000, null, winOption);
                    if (ret == 0) {
                        INPUT_FIR inputFIR = bsp.new INPUT_FIR();
                        FP_INFO fpInfo = isEngine.new FP_INFO();

                        inputFIR.SetFIRHandle(hCapturedFIR);
                        ret = isEngine.Identify(inputFIR, 5, fpInfo);
                        switch (ret) {
                            case 0:
                                w.fireAsistencia(fpInfo.ID);
                                break;
                            case 1029:
                                w.fireHuellaNoReconocida();
                                break;
                            case 1023:
                                w.fireHuellaNoReconocida();
                                break;
                            case 10:
                                JOptionPane.showMessageDialog(t, "Conecte su sensor VIRDI y abra de nuevo el programa");
                                w.dispose();
                                System.exit(0);
                                god = false;
                            default:
                                JOptionPane.showMessageDialog(t, "Error def" + ret);
                                break;
                        }
                        hCapturedFIR.dispose();
                    }
                }
            }
        };
        service.execute(waitForFinger);
    }

    public void preparaEngine(List profesorList) {
        isEngine = bsp.new FastSearch();
        UCBioBSPJNI.FastSearch.INIT_INFO isInitInfo = isEngine.new INIT_INFO();
        isEngine.GetInitInfo(isInitInfo);
        System.out.println("FastSearch InitInfo :");
        System.out.println(" UseGroupMatch : " + isInitInfo.UseGroupMatch);
        System.out.println(" MatchMethod : " + isInitInfo.MatchMethod);
        System.out.println("");

        UCBioBSPJNI.FastSearch.SAMPLE_INFO sampleInfo = isEngine.new SAMPLE_INFO();
        UCBioBSPJNI.FIR_TEXTENCODE textFir = bsp.new FIR_TEXTENCODE();
        UCBioBSPJNI.INPUT_FIR inputFIRStored = bsp.new INPUT_FIR();

        for (Iterator it = profesorList.iterator(); it.hasNext();) {
            Profesor profesor = (Profesor) it.next();
            String huellaVirdi = profesor.getHuellavirdi();

            textFir.TextFIR = huellaVirdi;
            inputFIRStored.SetTextFIR(textFir);
            isEngine.AddFIR(inputFIRStored, profesor.getIdprofesor(), sampleInfo);
        }
    }
}
