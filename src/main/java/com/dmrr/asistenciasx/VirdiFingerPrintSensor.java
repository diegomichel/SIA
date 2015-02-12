/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dmrr.asistenciasx;

import bareMysqlTables.Profesor;
import com.unioncomm.sdk.bsp.UCBioBSPJNI;
import com.unioncomm.sdk.bsp.UCBioBSPJNI.FIR_HANDLE;
import com.unioncomm.sdk.bsp.UCBioBSPJNI.FIR_PURPOSE;
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
    public boolean shutdown = false;

    public VirdiFingerPrintSensor() {
        bsp = new UCBioBSPJNI();
        if (bsp.IsErrorOccured()) {
            System.out.println(bsp.GetErrorCode());
            JOptionPane.showMessageDialog(null, "Virdi Sensor: iserrorocurred" + bsp.GetErrorCode());
            return;
        }
        int SetSkinResource = bsp.SetSkinResource("UCBioBSPSkin_Spanish.dll");
        if (SetSkinResource == 1) {
            //All fine
        } else {
            System.out.println("Some error... add skin dlls directory to your PATH system var.");
            JOptionPane.showMessageDialog(null, "Virdi Sensor: Agregue las dll skin de virdi a su variable PATH");
        }
        wOptions = bsp.new WINDOW_OPTION();
    }

    public boolean setAutoDetect(int param) {
        int errorCodeForAutodetect = bsp.SetAutoDetect(param);
        switch (errorCodeForAutodetect) {
            case VIRDICodes.NONE:
                return true;
            case VIRDICodes.INVALID_HANDLE:
                System.out.println("INVALID_HANDLE");
                return false;
            case VIRDICodes.INVALID_POINTER:
                System.out.println("INVALID_DEVICE_ID");
                return false;
            case VIRDICodes.DEVICE_NOT_OPENED:
                System.out.println("DEVICE_OPEN_FAIL");
                return false;
            case VIRDICodes.FUNCTION_NOT_SUPPORTED:
                System.out.println("FUNCTION_NOT_SUPPORTED");
                return false;
            default:
                System.err.println("Unknown error at SETUATODETECT" + errorCodeForAutodetect);
                return false;
        }
    }

    public boolean openDevice() {
        int errorCode = bsp.OpenDevice();
        switch (errorCode) {
            case VIRDICodes.NONE:
                return true;
            case VIRDICodes.INVALID_HANDLE:
                System.out.println("INVALID_HANDLE");
                return false;
            case VIRDICodes.INVALID_DEVICE_ID:
                System.out.println("INVALID_DEVICE_ID");
                return false;
            case VIRDICodes.DEVICE_OPEN_FAIL:
                System.out.println("DEVICE_OPEN_FAIL");
                return false;
            case VIRDICodes.DEVICE_ALREADY_OPENED:
                System.out.println("DEVICE_ALREADY_OPENED");
                return false;
            default:
                System.err.println("Unknown error at OPENDEVICE" + errorCode);
                return false;
        }
    }

    public boolean init() {
        if (openDevice()) {
            return setAutoDetect(1);
        }
        return false;
    }

    public boolean isSensorConnected() {
        if (!init()) {
            JOptionPane.showMessageDialog(null, "Sensor VIRDI no conectado");
            return false;
        }
        close();
        return true;
    }

    public String capturaHuella(Integer idProfesor) {
        if (!this.init()) {
            return "";
        }
        huella = "";
        UCBioBSPJNI.FIR_HANDLE hFIR = bsp.new FIR_HANDLE();
        UCBioBSPJNI.FIR_TEXTENCODE textFir = bsp.new FIR_TEXTENCODE();

        UCBioBSPJNI.FIR_PAYLOAD enrollPayload = bsp.new FIR_PAYLOAD();
        enrollPayload.SetText(idProfesor.toString());

        wOptions.WindowStyle = (0x00010000) | (0x00020000);
        wOptions.FingerWnd = null;
        wOptions.ParentWnd = null;
        int errorCode = bsp.Enroll(null, hFIR, enrollPayload, -1, null, wOptions);
        switch (errorCode) {
            case VIRDICodes.NONE:
                bsp.GetTextFIRFromHandle(hFIR, textFir);
                huella = textFir.TextFIR;
                break;
            case VIRDICodes.INVALID_HANDLE:
                System.err.println("Invalid handle");
                break;
            case VIRDICodes.INVALID_POINTER:
                System.err.println("INVALID_POINTER");
                break;
            case VIRDICodes.FUNCTION_FAIL:
                System.err.println("FUNCTION_FAIL");
                break;
            case VIRDICodes.DEVICE_NOT_OPENED:
                System.err.println("DEVICE_NOT_OPENED");
                break;
            case VIRDICodes.USER_CANCEL:
                System.err.println("USER_CANCEL");
                break;
            case VIRDICodes.USER_BACK:
                System.err.println("USER_BACK");
                break;
            default:
                System.err.println("Error on enroll" + errorCode);
        }
        hFIR.dispose();
        close();
        return huella;
    }

    public Boolean closeDevice() {
        int errorCode = bsp.CloseDevice();
        switch (errorCode) {
            case VIRDICodes.NONE:
                return true;
            case VIRDICodes.INVALID_HANDLE:
                System.out.println("INVALID_HANDLE");
                return false;
            case VIRDICodes.DEVICE_NOT_OPENED:
                System.out.println("DEVICE_NOT_OPENED");
                return false;
            case VIRDICodes.WRONG_DEVICE_ID:
                System.out.println("WRONG_DEVICE_ID");
                return false;
            default:
                System.err.println("Unknown error at CLOSEDEVICE" + errorCode);
                return false;
        }
    }

    public void close() {
        System.out.println("Liberando recursos virdi");
        setAutoDetect(0);
        if (bsp != null) {
            closeDevice();
        }
    }

    Boolean capturing = true;
    String jreBinPath = System.getProperty("java.home") + "\\bin";

    public void esperaPorHuella(Component c, final JTextArea t, final ListaYCapturaDeAsistencias w) {
        wOptions.WindowStyle = 1;
        wOptions.FingerWnd = c;
        wOptions.JreBinPath = jreBinPath;
        wOptions.ParentWnd = w;

        final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        Runnable waitForFinger = new Runnable() {
            Boolean result = new Boolean(false); //Need to be defined as this or it will crash
            UCBioBSPJNI.FIR_HANDLE hCapturedFIR = bsp.new FIR_HANDLE();

            @Override
            public void run() {
                while (capturing && !shutdown) {
                    //Si el sensor por alguna razon empieza a fallar aumentar el 3er parametro a algo mas alto, no dejar en 0 por que nunca se libera el sensor
                    //  y falla al tratar de capturar las huellas de los maestros.
                    if (!init()) {
                        continue;
                    }
                    System.out.println("Capture call done waiting for no crash print");
                    int errorCodeCapture = bsp.Capture(FIR_PURPOSE.IDENTIFY, hCapturedFIR, 5000, null, wOptions);
                    System.out.println("All safe no crash print");
                    switch (errorCodeCapture) {
                        case VIRDICodes.NONE:
                            INPUT_FIR inputFIR = bsp.new INPUT_FIR();
                            FP_INFO fpInfo = isEngine.new FP_INFO();
                            inputFIR.SetFIRHandle(hCapturedFIR);
                            System.out.println("Engine call done");
                            int errorCodeIndentify = isEngine.Identify(inputFIR, 5, fpInfo);
                            switch (errorCodeIndentify) {
                                case VIRDICodes.NONE:
                                    w.fireAsistencia(fpInfo.ID);
                                    break;
                                case VIRDICodes.INVALID_HANDLE:
                                    System.err.println("INVALID_HANDLE");
                                    break;
                                case VIRDICodes.INVALID_POINTER:
                                    System.err.println("INVALID_POINTER");
                                    break;
                                case VIRDICodes.FASTSEARCH_INIT_FAIL:
                                    System.err.println("FASTSEARCH_INIT_FAIL");
                                    break;
                                case VIRDICodes.INVALID_PARAMETER:
                                    System.out.println("INVALID_PARAMETER");
                                    break;
                                case VIRDICodes.FASTSEARCH_IDENTIFY_STOP:
                                    System.out.println("FASTSEARCH_IDENTIFY_STOP");
                                    break;
                                case VIRDICodes.FASTSEARCH_IDENTIFY_FAIL:
                                    System.out.println("FASTSEARCH_IDENTIFY_FAIL");
                                    w.fireNoEncontrado("");
                                    break;
                                default:
                                    JOptionPane.showMessageDialog(t, "Error def" + errorCodeIndentify);
                            }
                            break;
                        case VIRDICodes.INVALID_HANDLE:
                            System.out.println("INVALID_HANDLE");
                            break;
                        case VIRDICodes.DEVICE_NOT_OPENED:
                            System.out.println("DEVICE_NOT_OPENED");
                            break;
                        case VIRDICodes.USER_CANCEL:
                            System.out.println("USER_CANCEL");
                            break;
                        case VIRDICodes.CAPTURE_TIMEOUT:
                            System.out.println("CAPTURE_TIMEOUT");
                            break;
                        case VIRDICodes.CAPTURE_FAKE_SUSPICIOUS:
                            System.out.println("CAPTURE_FAKE_SUSPICIOUS");
                            break;
                    }
                    System.out.println("Dispose call done");
                    hCapturedFIR.dispose();
                    close();
                    wOptions.FingerWnd.repaint();
                }
                capturing = false;
                System.out.println("Se salio del loop ahora puede salir...");
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
            if (profesor.getHuellavirdi() == null) {
                continue;
            }
            String huellaVirdi = profesor.getHuellavirdi();

            textFir.TextFIR = huellaVirdi;
            inputFIRStored.SetTextFIR(textFir);
            isEngine.AddFIR(inputFIRStored, profesor.getIdprofesor(), sampleInfo);
        }
    }
}
