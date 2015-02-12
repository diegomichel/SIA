package com.unioncomm.sdk.bsp;

import java.awt.Component;
import org.apache.commons.lang.builder.ToStringBuilder;

public class UCBioBSPJNI {

    static boolean s_useNative;

    static {
        try {
            System.loadLibrary("UCBioBSPJNI");
            s_useNative = true;
        } catch (Exception localException) {
            s_useNative = false;
        }
    }

    public static class FIR_SECURITY_LEVEL {

        public static final int LOWEST = 1;
        public static final int LOWER = 2;
        public static final int LOW = 3;
        public static final int BELOW_NORMAL = 4;
        public static final int NORMAL = 5;
        public static final int ABOVE_NORMAL = 6;
        public static final int HIGH = 7;
        public static final int HIGHER = 8;
        public static final int HIGHEST = 9;
    }

    public static class DEVICE_ID {

        public static final short NONE = 0;
        public static final short AUTO = 255;
    }

    public static class DEVICE_NAME {

        public static final short FOH01 = 1;
        public static final short FOM01 = 2;
        public static final short FOH03 = 3;
        public static final short HAM500 = 4;
        public static final short FOH01A = 5;
        public static final short FOM01A = 6;
        public static final short FPR02 = 7;
        public static final short FSH01RF = 8;
        public static final short FOH01RF = 9;
        public static final short FR100 = 10;
        public static final short FPR02LFD = 11;
        public static final short FOH01RFL = 12;
        public static final short FSH01SC = 13;
        public static final short FPR02_V30 = 14;
    }

    public static class FIR_FORMAT {

        public static final int STANDARD = 1;
    }

    public static class FIR_DATA_TYPE {

        public static final int RAW = 0;
        public static final int INTERMEDIATE = 1;
        public static final int PROCESSED = 2;
        public static final int ENCRYPTED = 16;
    }

    public static class FIR_PURPOSE {

        public static final int VERIFY = 1;
        public static final int IDENTIFY = 2;
        public static final int ENROLL = 3;
        public static final int ENROLL_FOR_VERIFICATION_ONLY = 4;
        public static final int ENROLL_FOR_IDENTIFICATION_ONLY = 5;
        public static final int AUDIT = 6;
        public static final int UPDATE = 16;
    }

    public static class FIR_FORM {

        public static final int HANDLE = 2;
        public static final int FULLFIR = 3;
        public static final int TEXTENCODE = 4;
    }

    public class INIT_INFO_0 {

        public int MaxFingersForEnroll;
        public int NecessaryEnrollNum;
        public int SamplesPerFinger;
        public int DefaultTimeout;
        public int SecurityLevelForEnroll;
        public int SecurityLevelForVerify;
        public int SecurityLevelForIdentify;
        public int Reserved1;
        public int Reserved2;

        public INIT_INFO_0() {
            this.MaxFingersForEnroll = 10;
            this.NecessaryEnrollNum = 0;
            this.SamplesPerFinger = 2;
            this.DefaultTimeout = 10000;
            this.SecurityLevelForEnroll = 5;
            this.SecurityLevelForVerify = 5;
            this.SecurityLevelForIdentify = 6;
            this.Reserved1 = 0;
            this.Reserved2 = 0;
        }
    }

    public class DEVICE_INFO {

        public int ImageWidth;
        public int ImageHeight;
        public int Brightness;
        public int Contrast;
        public int Gain;

        public DEVICE_INFO() {
        }
    }

    public class DEVICE_INFO_EX {

        public short DeviceID;
        public short NameID;
        public short Instance;
        public String Name;
        public String Description;
        public String Dll;
        public String Sys;
        public int Brightness;
        public int Contrast;
        public int Gain;
        public int Reserved1;
        public int Reserved2;
        public int Reserved3;
        public int Reserved4;
        public int Reserved5;
        public int Reserved6;
        public int Reserved7;
        public int Reserved8;

        public DEVICE_INFO_EX() {
        }
    }

    public class DEVICE_ENUM_INFO {

        public UCBioBSPJNI.DEVICE_INFO_EX[] DeviceInfo;

        public DEVICE_ENUM_INFO() {
        }
    }

    public class MATCH_OPTION {

        public byte[] NoMatchFinger = new byte[11];
        public int[] Reserved = new int[8];

        public MATCH_OPTION() {
            int j = this.NoMatchFinger.length;
            for (int i = 0; i < j; i++) {
                this.NoMatchFinger[i] = 0;
            }
            j = this.Reserved.length;
            for (int i = 0; i < j; i++) {
                this.Reserved[i] = 0;
            }
        }
    }

    public class FIR_HANDLE {

        private int Handle = 0;

        public FIR_HANDLE() {
        }

        public void dispose() {
            if (this.Handle != 0) {
                UCBioBSPJNI.UCBioAPI_NativeFreeFIRHandle(this.Handle);
                this.Handle = 0;
            }
        }

        protected void finalize()
                throws Throwable {
            try {
                dispose();

                super.finalize();
            } finally {
                super.finalize();
            }
        }
    }

    public class FIR_DATE {

        public short Year;
        public byte Month;
        public byte Day;

        public FIR_DATE() {
            this.Year = 0;
            this.Month = 0;
            this.Day = 0;
        }
    }

    public class FIR_OPTIONAL_DATA {

        public int UUIDIndex;
        public byte[] UUIDInfo = new byte[16];
        public int PIN1;
        public int PIN2;
        public int Privilege;
        public int SiteID;
        public UCBioBSPJNI.FIR_DATE IssueDate;
        public UCBioBSPJNI.FIR_DATE ExpireDate;

        public FIR_OPTIONAL_DATA() {
            IssueDate = new UCBioBSPJNI.FIR_DATE();
            ExpireDate = new UCBioBSPJNI.FIR_DATE();
            int j = this.UUIDInfo.length;
            for (int i = 0; i < j; i++) {
                this.UUIDInfo[i] = 0;
            }
        }
    }

    public class FIR_HEADER {

        public short Version;
        public short DataType;
        public short Purpose;
        public short Quality;
        public UCBioBSPJNI.FIR_OPTIONAL_DATA OptionalData = new UCBioBSPJNI.FIR_OPTIONAL_DATA();
        public int Reserved;

        public FIR_HEADER() {
        }
    }

    public class FIR {

        public int Format;
        public UCBioBSPJNI.FIR_HEADER Header = new UCBioBSPJNI.FIR_HEADER();
        public byte[] Data = null;

        public FIR() {
        }
    }

    public class FIR_TEXTENCODE {

        public String TextFIR;

        public FIR_TEXTENCODE() {
        }
    }

    public class INPUT_FIR {

        private int Form;
        private int FIRHandle;
        private UCBioBSPJNI.FIR FullFIR;
        private UCBioBSPJNI.FIR_TEXTENCODE TextFIR;

        public INPUT_FIR() {
        }

        public void SetFIRHandle(UCBioBSPJNI.FIR_HANDLE paramFIR_HANDLE) {
            this.Form = 2;
            this.FIRHandle = paramFIR_HANDLE.Handle;
        }

        public void SetFullFIR(UCBioBSPJNI.FIR paramFIR) {
            this.Form = 3;
            this.FullFIR = paramFIR;
        }

        public void SetTextFIR(UCBioBSPJNI.FIR_TEXTENCODE paramFIR_TEXTENCODE) {
            this.Form = 4;
            this.TextFIR = paramFIR_TEXTENCODE;
        }
    }

    public class FIR_PAYLOAD {

        private String Text = null;
        private byte[] Data = null;

        public FIR_PAYLOAD() {
        }

        public void SetData(byte[] paramArrayOfByte) {
            this.Data = paramArrayOfByte;
            this.Text = null;
        }

        public void SetText(String paramString) {
            this.Data = null;
            this.Text = paramString;
        }

        public byte[] GetData() {
            return this.Data;
        }

        public String GetText() {
            return this.Text;
        }
    }

    public class WINDOW_OPTION {

        public int WindowStyle;
        public Component ParentWnd;
        public Component FingerWnd;
        public String JreBinPath;
        public String CaptionMsg;
        public String CancelMsg;
        public int FPForeColorR;
        public int FPForeColorG;
        public int FPForeColorB;
        public int FPBackColorR;
        public int FPBackColorG;
        public int FPBackColorB;
        public int DisableFingerForEnroll1;
        public int DisableFingerForEnroll2;
        public int DisableFingerForEnroll3;
        public int DisableFingerForEnroll4;
        public int DisableFingerForEnroll5;
        public int DisableFingerForEnroll6;
        public int DisableFingerForEnroll7;
        public int DisableFingerForEnroll8;
        public int DisableFingerForEnroll9;
        public int DisableFingerForEnroll10;

        public WINDOW_OPTION() {
            this.WindowStyle = 0;
            this.ParentWnd = null;
            this.FingerWnd = null;
            this.CaptionMsg = null;
            this.CancelMsg = null;

            this.FPForeColorR = (this.FPForeColorG = this.FPForeColorB = 0);
            this.FPBackColorR = (this.FPBackColorG = this.FPBackColorB = 'ÿ');

            this.DisableFingerForEnroll1 = (this.DisableFingerForEnroll2 = 0);
            this.DisableFingerForEnroll3 = (this.DisableFingerForEnroll4 = this.DisableFingerForEnroll5 = 0);
            this.DisableFingerForEnroll6 = (this.DisableFingerForEnroll7 = this.DisableFingerForEnroll8 = 0);
            this.DisableFingerForEnroll9 = (this.DisableFingerForEnroll10 = 0);
        }
    }

    private int m_hUCBioBSP = 0;
    private int m_nErrCode;
    private String m_bspVersion = "3.4.0.3";

    public UCBioBSPJNI() {
        if (s_useNative) {
            this.m_nErrCode = UCBioAPI_NativeInit(this, 0);
        }
    }

    public UCBioBSPJNI(int paramInt) {
        if (s_useNative) {
            this.m_nErrCode = UCBioAPI_NativeInit(this, paramInt);
        }
    }

    public int GetErrorCode() {
        return this.m_nErrCode;
    }

    public boolean IsErrorOccured() {
        return this.m_nErrCode != 0;
    }

    public void dispose() {
        if (s_useNative) {
            if (this.m_hUCBioBSP != 0) {
                UCBioAPI_NativeTerminate(this.m_hUCBioBSP);
                this.m_hUCBioBSP = 0;
            }
        }
    }

    protected void finalize()
            throws Throwable {
        try {
            dispose();

            super.finalize();
        } finally {
            super.finalize();
        }
    }

    public int SetSkinResource(String paramString) {
        if (s_useNative) {
            return UCBioAPI_NativeSetSkinResource(paramString);
        }
        return 1;
    }

    public String GetVersion() {
        if (s_useNative) {
            UCBioAPI_NativeGetVersion(this.m_hUCBioBSP, this);
        }
        return this.m_bspVersion;
    }

    public int GetInitInfo(INIT_INFO_0 paramINIT_INFO_0) {
        if (s_useNative) {
            return UCBioAPI_NativeGetInitInfo(this.m_hUCBioBSP, paramINIT_INFO_0);
        }
        return 1;
    }

    public int SetInitInfo(INIT_INFO_0 paramINIT_INFO_0) {
        if (s_useNative) {
            return UCBioAPI_NativeSetInitInfo(this.m_hUCBioBSP, paramINIT_INFO_0);
        }
        return 1;
    }

    public int EnumerateDevice(DEVICE_ENUM_INFO paramDEVICE_ENUM_INFO) {
        if (s_useNative) {
            return UCBioAPI_NativeEnumerateDevice(this.m_hUCBioBSP, paramDEVICE_ENUM_INFO, 0);
        }
        return 1;
    }

    public int EnumerateDevice(DEVICE_ENUM_INFO paramDEVICE_ENUM_INFO, int paramInt) {
        if (s_useNative) {
            return UCBioAPI_NativeEnumerateDevice(this.m_hUCBioBSP, paramDEVICE_ENUM_INFO, paramInt);
        }
        return 1;
    }

    public int OpenDevice() {
        return OpenDevice(0);
    }

    public int OpenDevice(int paramInt) {
        if (s_useNative) {
            return UCBioAPI_NativeOpenDevice(this.m_hUCBioBSP, (short) 255, paramInt);
        }
        return 1;
    }

    public int OpenDevice(short paramShort1, short paramShort2) {
        return OpenDevice(paramShort1, paramShort2, 0);
    }

    public int OpenDevice(short paramShort1, short paramShort2, int paramInt) {
        if (s_useNative) {
            short s = (short) (paramShort1 + paramShort2 * 255);
            return UCBioAPI_NativeOpenDevice(this.m_hUCBioBSP, s, paramInt);
        }
        return 1;
    }

    public int CloseDevice() {
        if (s_useNative) {
            return UCBioAPI_NativeCloseDevice(this.m_hUCBioBSP, (short) 255);
        }
        return 1;
    }

    public int CloseDevice(short paramShort1, short paramShort2) {
        if (s_useNative) {
            short s = (short) (paramShort1 + paramShort2 * 255);
            return UCBioAPI_NativeCloseDevice(this.m_hUCBioBSP, s);
        }
        return 1;
    }

    public int GetDeviceInfo(DEVICE_INFO paramDEVICE_INFO) {
        if (s_useNative) {
            return UCBioAPI_NativeGetDeviceInfo(this.m_hUCBioBSP, (short) 255, paramDEVICE_INFO);
        }
        return 1;
    }

    public int SetDeviceInfo(DEVICE_INFO paramDEVICE_INFO) {
        if (s_useNative) {
            return UCBioAPI_NativeSetDeviceInfo(this.m_hUCBioBSP, (short) 255, paramDEVICE_INFO);
        }
        return 1;
    }

    public int GetDeviceInfo(short paramShort1, short paramShort2, DEVICE_INFO paramDEVICE_INFO) {
        if (s_useNative) {
            short s = (short) (paramShort1 + paramShort2 * 255);
            return UCBioAPI_NativeGetDeviceInfo(this.m_hUCBioBSP, s, paramDEVICE_INFO);
        }
        return 1;
    }

    public int SetDeviceInfo(short paramShort1, short paramShort2, DEVICE_INFO paramDEVICE_INFO) {
        if (s_useNative) {
            short s = (short) (paramShort1 + paramShort2 * 255);
            return UCBioAPI_NativeSetDeviceInfo(this.m_hUCBioBSP, s, paramDEVICE_INFO);
        }
        return 1;
    }

    public int AdjustDevice() {
        return AdjustDevice(null);
    }

    public int AdjustDevice(WINDOW_OPTION paramWINDOW_OPTION) {
        if (s_useNative) {
            return UCBioAPI_NativeAdjustDevice(this.m_hUCBioBSP, paramWINDOW_OPTION);
        }
        return 1;
    }

    public short GetOpenedDeviceID() {
        if (s_useNative) {
            return UCBioAPI_NativeGetOpenedDeviceID(this.m_hUCBioBSP);
        }
        return 1;
    }

    public int GetFIRFromHandle(FIR_HANDLE paramFIR_HANDLE, FIR paramFIR) {
        if (s_useNative) {
            return UCBioAPI_NativeGetFIRFromHandle(this.m_hUCBioBSP, paramFIR_HANDLE, paramFIR, 1);
        }
        return 1;
    }

    public int GetFIRFromHandle(FIR_HANDLE paramFIR_HANDLE, FIR paramFIR, int paramInt) {
        if (s_useNative) {
            return UCBioAPI_NativeGetFIRFromHandle(this.m_hUCBioBSP, paramFIR_HANDLE, paramFIR, paramInt);
        }
        return 1;
    }

    public int GetTextFIRFromHandle(FIR_HANDLE paramFIR_HANDLE, FIR_TEXTENCODE paramFIR_TEXTENCODE) {
        if (s_useNative) {
            return UCBioAPI_NativeGetTextFIRFromHandle(this.m_hUCBioBSP, paramFIR_HANDLE, paramFIR_TEXTENCODE, 1);
        }
        return 1;
    }

    public int GetTextFIRFromHandle(FIR_HANDLE paramFIR_HANDLE, FIR_TEXTENCODE paramFIR_TEXTENCODE, int paramInt) {
        if (s_useNative) {
            return UCBioAPI_NativeGetTextFIRFromHandle(this.m_hUCBioBSP, paramFIR_HANDLE, paramFIR_TEXTENCODE, paramInt);
        }
        return 1;
    }

    public int Enroll(FIR_HANDLE paramFIR_HANDLE, FIR_PAYLOAD paramFIR_PAYLOAD) {
        return Enroll(null, paramFIR_HANDLE, paramFIR_PAYLOAD, -1, null, null);
    }

    public int Enroll(INPUT_FIR paramINPUT_FIR, FIR_HANDLE paramFIR_HANDLE1, FIR_PAYLOAD paramFIR_PAYLOAD, int paramInt, FIR_HANDLE paramFIR_HANDLE2, WINDOW_OPTION paramWINDOW_OPTION) {
        if (s_useNative) {
            return UCBioAPI_NativeEnroll(this.m_hUCBioBSP, paramINPUT_FIR, paramFIR_HANDLE1, paramFIR_PAYLOAD, paramInt, paramFIR_HANDLE2, paramWINDOW_OPTION);
        }
        return 1;
    }

    public int SetAutoDetect(int paramInt) {
        if (s_useNative) {
            return UCBioAPI_NativeSetAutoDetect(this.m_hUCBioBSP, paramInt);
        }
        return 1;
    }

    public int Capture(FIR_HANDLE paramFIR_HANDLE) {
        return Capture(1, paramFIR_HANDLE, -1, null, null);
    }

    public int Capture(int paramInt1, FIR_HANDLE paramFIR_HANDLE1, int paramInt2, FIR_HANDLE paramFIR_HANDLE2, WINDOW_OPTION paramWINDOW_OPTION) {
        if (s_useNative) {
            return UCBioAPI_NativeCapture(this.m_hUCBioBSP, paramInt1, paramFIR_HANDLE1, paramInt2, paramFIR_HANDLE2, paramWINDOW_OPTION);
        }
        return 1;
    }

    public int Process(INPUT_FIR paramINPUT_FIR, FIR_HANDLE paramFIR_HANDLE) {
        if (s_useNative) {
            return UCBioAPI_NativeProcess(this.m_hUCBioBSP, paramINPUT_FIR, paramFIR_HANDLE);
        }
        return 1;
    }

    public int CreateTemplate(INPUT_FIR paramINPUT_FIR1, INPUT_FIR paramINPUT_FIR2, FIR_HANDLE paramFIR_HANDLE, FIR_PAYLOAD paramFIR_PAYLOAD) {
        if (s_useNative) {
            return UCBioAPI_NativeCreateTemplate(this.m_hUCBioBSP, paramINPUT_FIR1, paramINPUT_FIR2, paramFIR_HANDLE, paramFIR_PAYLOAD);
        }
        return 1;
    }

    public int Verify(INPUT_FIR paramINPUT_FIR, Boolean paramBoolean, FIR_PAYLOAD paramFIR_PAYLOAD) {
        return Verify(paramINPUT_FIR, paramBoolean, paramFIR_PAYLOAD, -1, null, null);
    }

    public int Verify(INPUT_FIR paramINPUT_FIR, Boolean paramBoolean, FIR_PAYLOAD paramFIR_PAYLOAD, int paramInt, FIR_HANDLE paramFIR_HANDLE, WINDOW_OPTION paramWINDOW_OPTION) {
        if (s_useNative) {
            return UCBioAPI_NativeVerify(this.m_hUCBioBSP, paramINPUT_FIR, paramBoolean, paramFIR_PAYLOAD, paramInt, paramFIR_HANDLE, paramWINDOW_OPTION);
        }
        return 1;
    }

    public int VerifyMatch(INPUT_FIR paramINPUT_FIR1, INPUT_FIR paramINPUT_FIR2, Boolean paramBoolean, FIR_PAYLOAD paramFIR_PAYLOAD) {
        return VerifyMatch(paramINPUT_FIR1, paramINPUT_FIR2, paramBoolean, paramFIR_PAYLOAD, null);
    }

    public int VerifyMatch(INPUT_FIR paramINPUT_FIR1, INPUT_FIR paramINPUT_FIR2, Boolean paramBoolean, FIR_PAYLOAD paramFIR_PAYLOAD, MATCH_OPTION paramMATCH_OPTION) {
        if (s_useNative) {
            return UCBioAPI_NativeVerifyMatchEx(this.m_hUCBioBSP, paramINPUT_FIR1, paramINPUT_FIR2, paramBoolean, paramFIR_PAYLOAD, paramMATCH_OPTION);
        }
        return 1;
    }

    public int CheckFInger(Boolean paramBoolean) {
        if (s_useNative) {
            return UCBioAPI_NativeCheckFinger(this.m_hUCBioBSP, paramBoolean);
        }
        return 1;
    }

    static native int UCBioAPI_NativeInit(UCBioBSPJNI paramUCBioBSPJNI, int paramInt);

    static native int UCBioAPI_NativeTerminate(int paramInt);

    static native int UCBioAPI_NativeSetSkinResource(String paramString);

    static native void UCBioAPI_NativeGetVersion(int paramInt, UCBioBSPJNI paramUCBioBSPJNI);

    static native int UCBioAPI_NativeGetInitInfo(int paramInt, INIT_INFO_0 paramINIT_INFO_0);

    static native int UCBioAPI_NativeSetInitInfo(int paramInt, INIT_INFO_0 paramINIT_INFO_0);

    static native int UCBioAPI_NativeEnumerateDevice(int paramInt1, DEVICE_ENUM_INFO paramDEVICE_ENUM_INFO, int paramInt2);

    static native int UCBioAPI_NativeOpenDevice(int paramInt1, short paramShort, int paramInt2);

    static native int UCBioAPI_NativeCloseDevice(int paramInt, short paramShort);

    static native int UCBioAPI_NativeGetDeviceInfo(int paramInt, short paramShort, DEVICE_INFO paramDEVICE_INFO);

    static native int UCBioAPI_NativeSetDeviceInfo(int paramInt, short paramShort, DEVICE_INFO paramDEVICE_INFO);

    static native int UCBioAPI_NativeAdjustDevice(int paramInt, WINDOW_OPTION paramWINDOW_OPTION);

    static native short UCBioAPI_NativeGetOpenedDeviceID(int paramInt);

    static native int UCBioAPI_NativeCheckFinger(int paramInt, Boolean paramBoolean);

    static native int UCBioAPI_NativeGetFIRFromHandle(int paramInt1, FIR_HANDLE paramFIR_HANDLE, FIR paramFIR, int paramInt2);

    static native int UCBioAPI_NativeGetTextFIRFromHandle(int paramInt1, FIR_HANDLE paramFIR_HANDLE, FIR_TEXTENCODE paramFIR_TEXTENCODE, int paramInt2);

    static native int UCBioAPI_NativeFreeFIRHandle(int paramInt);

    static native int UCBioAPI_NativeEnroll(int paramInt1, INPUT_FIR paramINPUT_FIR, FIR_HANDLE paramFIR_HANDLE1, FIR_PAYLOAD paramFIR_PAYLOAD, int paramInt2, FIR_HANDLE paramFIR_HANDLE2, WINDOW_OPTION paramWINDOW_OPTION);

    static native int UCBioAPI_NativeSetAutoDetect(int paramInt1, int paramInt2);

    static native int UCBioAPI_NativeCapture(int paramInt1, int paramInt2, FIR_HANDLE paramFIR_HANDLE1, int paramInt3, FIR_HANDLE paramFIR_HANDLE2, WINDOW_OPTION paramWINDOW_OPTION);

    static native int UCBioAPI_NativeProcess(int paramInt, INPUT_FIR paramINPUT_FIR, FIR_HANDLE paramFIR_HANDLE);

    static native int UCBioAPI_NativeCreateTemplate(int paramInt, INPUT_FIR paramINPUT_FIR1, INPUT_FIR paramINPUT_FIR2, FIR_HANDLE paramFIR_HANDLE, FIR_PAYLOAD paramFIR_PAYLOAD);

    static native int UCBioAPI_NativeVerify(int paramInt1, INPUT_FIR paramINPUT_FIR, Boolean paramBoolean, FIR_PAYLOAD paramFIR_PAYLOAD, int paramInt2, FIR_HANDLE paramFIR_HANDLE, WINDOW_OPTION paramWINDOW_OPTION);

    static native int UCBioAPI_NativeVerifyMatchEx(int paramInt, INPUT_FIR paramINPUT_FIR1, INPUT_FIR paramINPUT_FIR2, Boolean paramBoolean, FIR_PAYLOAD paramFIR_PAYLOAD, MATCH_OPTION paramMATCH_OPTION);

    static native int UCBioAPI_NativeFIRToTemplate(int paramInt1, INPUT_FIR paramINPUT_FIR, UCBioBSPJNI.Export.DATA paramDATA, int paramInt2);

    static native int UCBioAPI_NativeImportDataToFIR(int paramInt1, UCBioBSPJNI.Export.DATA paramDATA, int paramInt2, int paramInt3, FIR_HANDLE paramFIR_HANDLE);

    static native int UCBioAPI_NativeAuditFIRToImage(int paramInt, INPUT_FIR paramINPUT_FIR, UCBioBSPJNI.Export.AUDIT paramAUDIT);

    static native int UCBioAPI_NativeImageToAuditFIR(int paramInt, UCBioBSPJNI.Export.AUDIT paramAUDIT, FIR_HANDLE paramFIR_HANDLE);

    static native int UCBioAPI_NativeImportBioAPIOpaqueToFIRHandle(int paramInt, byte[] paramArrayOfByte, FIR_HANDLE paramFIR_HANDLE);

    static native int UCBioAPI_NativeInitFastSearchEngine(int paramInt);

    static native int UCBioAPI_NativeTerminateFastSearchEngine(int paramInt);

    static native int UCBioAPI_NativeGetFastSearchInitInfo(int paramInt, UCBioBSPJNI.FastSearch.INIT_INFO paramINIT_INFO);

    static native int UCBioAPI_NativeSetFastSearchInitInfo(int paramInt, UCBioBSPJNI.FastSearch.INIT_INFO paramINIT_INFO);

    static native int UCBioAPI_NativeAddFIRToFastSearchDB(int paramInt1, INPUT_FIR paramINPUT_FIR, int paramInt2, UCBioBSPJNI.FastSearch.SAMPLE_INFO paramSAMPLE_INFO);

    static native int UCBioAPI_NativeIdentifyDataFromFastSearchDB(int paramInt1, INPUT_FIR paramINPUT_FIR, int paramInt2, UCBioBSPJNI.FastSearch.FP_INFO paramFP_INFO);

    static native int UCBioAPI_NativeRemoveDataFromFastSearchDB(int paramInt, UCBioBSPJNI.FastSearch.FP_INFO paramFP_INFO);

    static native int UCBioAPI_NativeRemoveUserFromFastSearchDB(int paramInt1, int paramInt2);

    static native int UCBioAPI_NativeClearFastSearchDB(int paramInt);

    static native int UCBioAPI_NativeSaveFastSearchDBToFile(int paramInt, String paramString);

    static native int UCBioAPI_NativeLoadFastSearchDBFromFile(int paramInt, String paramString);

    static native int UCBioAPI_NativeGetDataCountFromFastSearchDB(int paramInt, Integer paramInteger);

    static native int UCBioAPI_NativeCheckDataExistFromFastSearchDB(int paramInt, UCBioBSPJNI.FastSearch.FP_INFO paramFP_INFO, Boolean paramBoolean);

    public static class EXPORT_TEMPLATE_TYPE {

        public static final int SIZE400 = 400;
        public static final int SIZE800 = 800;
        public static final int SIZE320 = 320;
        public static final int SIZE256 = 256;
        public static final int FMR = 1;
    }

    public class Export {

        public Export() {
        }

        public int ExportFIR(UCBioBSPJNI.INPUT_FIR paramINPUT_FIR, DATA paramDATA, int paramInt) {
            if (UCBioBSPJNI.s_useNative) {
                return UCBioBSPJNI.UCBioAPI_NativeFIRToTemplate(UCBioBSPJNI.this.m_hUCBioBSP, paramINPUT_FIR, paramDATA, paramInt);
            }
            return 1;
        }

        public int ImportFIR(DATA paramDATA, UCBioBSPJNI.FIR_HANDLE paramFIR_HANDLE) {
            return ImportFIR(paramDATA, 1, 2, paramFIR_HANDLE);
        }

        public int ImportFIR(DATA paramDATA, int paramInt1, int paramInt2, UCBioBSPJNI.FIR_HANDLE paramFIR_HANDLE) {
            if (UCBioBSPJNI.s_useNative) {
                return UCBioBSPJNI.UCBioAPI_NativeImportDataToFIR(UCBioBSPJNI.this.m_hUCBioBSP, paramDATA, paramInt1, paramInt2, paramFIR_HANDLE);
            }
            return 1;
        }

        public int ExportAudit(UCBioBSPJNI.INPUT_FIR paramINPUT_FIR, AUDIT paramAUDIT) {
            if (UCBioBSPJNI.s_useNative) {
                return UCBioBSPJNI.UCBioAPI_NativeAuditFIRToImage(UCBioBSPJNI.this.m_hUCBioBSP, paramINPUT_FIR, paramAUDIT);
            }
            return 1;
        }

        public int ImportAudit(AUDIT paramAUDIT, UCBioBSPJNI.FIR_HANDLE paramFIR_HANDLE) {
            if (UCBioBSPJNI.s_useNative) {
                return UCBioBSPJNI.UCBioAPI_NativeImageToAuditFIR(UCBioBSPJNI.this.m_hUCBioBSP, paramAUDIT, paramFIR_HANDLE);
            }
            return 1;
        }

        public int ImportBioAPIOpaqueToFIR(byte[] paramArrayOfByte, UCBioBSPJNI.FIR_HANDLE paramFIR_HANDLE) {
            if (UCBioBSPJNI.s_useNative) {
                return UCBioBSPJNI.UCBioAPI_NativeImportBioAPIOpaqueToFIRHandle(UCBioBSPJNI.this.m_hUCBioBSP, paramArrayOfByte, paramFIR_HANDLE);
            }
            return 1;
        }

        public class AUDIT {

            public byte FingerNum;
            public byte SamplesPerFinger;
            public int ImageWidth;
            public int ImageHeight;
            public UCBioBSPJNI.Export.FINGER_BLOCK[] FingerInfo;

            public AUDIT() {
            }
        }

        public class DATA {

            public int TemplateType;
            public byte FingerNum;
            public byte DefaultFingerID;
            public byte SamplesPerFinger;
            public UCBioBSPJNI.Export.FINGER_BLOCK[] FingerInfo;

            public DATA() {
            }
        }

        public class FINGER_BLOCK {

            public byte FingerID;
            public UCBioBSPJNI.Export.TEMPLATE_BLOCK[] TemplateInfo;

            public FINGER_BLOCK() {
            }
        }

        public class TEMPLATE_BLOCK {

            public byte[] Data;

            public TEMPLATE_BLOCK() {
            }
        }
    }

    public class FastSearch {

        public int m_retInit;

        public class INIT_INFO {

            public int UseGroupMatch = 1;
            public int MatchMethod = 0;
            public int Researved1;
            public int Researved2;
            public int Researved3;
            public int Researved4;
            public int Researved5;

            public INIT_INFO() {
            }
        }

        public class SAMPLE_INFO {

            public int ID;
            public byte[] SampleCount = new byte[11];

            public SAMPLE_INFO() {
            }
        }

        public FastSearch() {
            if (UCBioBSPJNI.s_useNative) {
                this.m_retInit = UCBioBSPJNI.UCBioAPI_NativeInitFastSearchEngine(UCBioBSPJNI.this.m_hUCBioBSP);
            } else {
                this.m_retInit = 1;
            }
        }

        public void dispose() {
            if (UCBioBSPJNI.s_useNative) {
                UCBioBSPJNI.UCBioAPI_NativeTerminateFastSearchEngine(UCBioBSPJNI.this.m_hUCBioBSP);
            } else {
                this.m_retInit = 1;
            }
        }

        protected void finalize()
                throws Throwable {
            dispose();
        }

        public int GetInitInfo(INIT_INFO paramINIT_INFO) {
            if (UCBioBSPJNI.s_useNative) {
                return UCBioBSPJNI.UCBioAPI_NativeGetFastSearchInitInfo(UCBioBSPJNI.this.m_hUCBioBSP, paramINIT_INFO);
            }
            return 1;
        }

        public int SetInitInfo(INIT_INFO paramINIT_INFO) {
            if (UCBioBSPJNI.s_useNative) {
                return UCBioBSPJNI.UCBioAPI_NativeSetFastSearchInitInfo(UCBioBSPJNI.this.m_hUCBioBSP, paramINIT_INFO);
            }
            return 1;
        }

        public int AddFIR(UCBioBSPJNI.INPUT_FIR paramINPUT_FIR, int paramInt, SAMPLE_INFO paramSAMPLE_INFO) {
            if (UCBioBSPJNI.s_useNative) {
                return UCBioBSPJNI.UCBioAPI_NativeAddFIRToFastSearchDB(UCBioBSPJNI.this.m_hUCBioBSP, paramINPUT_FIR, paramInt, paramSAMPLE_INFO);
            }
            return 1;
        }

        public int Identify(UCBioBSPJNI.INPUT_FIR paramINPUT_FIR, int paramInt, FP_INFO paramFP_INFO) {
            if (UCBioBSPJNI.s_useNative) {
                return UCBioBSPJNI.UCBioAPI_NativeIdentifyDataFromFastSearchDB(UCBioBSPJNI.this.m_hUCBioBSP, paramINPUT_FIR, paramInt, paramFP_INFO);
            }
            return 1;
        }

        public int RemoveData(FP_INFO paramFP_INFO) {
            if (UCBioBSPJNI.s_useNative) {
                return UCBioBSPJNI.UCBioAPI_NativeRemoveDataFromFastSearchDB(UCBioBSPJNI.this.m_hUCBioBSP, paramFP_INFO);
            }
            return 1;
        }

        public int RemoveUser(int paramInt) {
            if (UCBioBSPJNI.s_useNative) {
                return UCBioBSPJNI.UCBioAPI_NativeRemoveUserFromFastSearchDB(UCBioBSPJNI.this.m_hUCBioBSP, paramInt);
            }
            return 1;
        }

        public int ClearDB() {
            if (UCBioBSPJNI.s_useNative) {
                return UCBioBSPJNI.UCBioAPI_NativeClearFastSearchDB(UCBioBSPJNI.this.m_hUCBioBSP);
            }
            return 1;
        }

        public int SaveDB(String paramString) {
            if (UCBioBSPJNI.s_useNative) {
                return UCBioBSPJNI.UCBioAPI_NativeSaveFastSearchDBToFile(UCBioBSPJNI.this.m_hUCBioBSP, paramString);
            }
            return 1;
        }

        public int LoadDB(String paramString) {
            if (UCBioBSPJNI.s_useNative) {
                return UCBioBSPJNI.UCBioAPI_NativeLoadFastSearchDBFromFile(UCBioBSPJNI.this.m_hUCBioBSP, paramString);
            }
            return 1;
        }

        public int GetDBCount(Integer paramInteger) {
            if (UCBioBSPJNI.s_useNative) {
                return UCBioBSPJNI.UCBioAPI_NativeGetDataCountFromFastSearchDB(UCBioBSPJNI.this.m_hUCBioBSP, paramInteger);
            }
            return 1;
        }

        public int CheckDataExist(FP_INFO paramFP_INFO, Boolean paramBoolean) {
            if (UCBioBSPJNI.s_useNative) {
                return UCBioBSPJNI.UCBioAPI_NativeCheckDataExistFromFastSearchDB(UCBioBSPJNI.this.m_hUCBioBSP, paramFP_INFO, paramBoolean);
            }
            return 1;
        }

        public class FP_INFO {

            public int ID;
            public byte FingerID;
            public byte SampleNumber;

            public FP_INFO() {
            }
        }
    }
}
