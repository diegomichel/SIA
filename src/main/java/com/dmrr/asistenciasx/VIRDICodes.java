/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dmrr.asistenciasx;

/**
 *
 * @author diegomichel
 */
public class VIRDICodes {

    public static final int NONE = 0;
    public static final int INVALID_HANDLE = 0x0001;
    public static final int INVALID_POINTER = 0x0002;
    public static final int INVALID_TYPE = 0x0003;
    public static final int INVALID_PARAMETER = 0x0015;
    public static final int FUNCTION_FAIL = 0x0004;
    public static final int STRUCTTYPE_NOT_MATCHED = 0x0005;
    public static final int ALREADY_PROCESSED = 0x0006;
    public static final int EXTRACTION_OPEN_FAIL = 0x0007;
    public static final int VERIFICATION_OPEN_FAIL = 0x0008;
    public static final int DATA_PROCESS_FAIL = 0x0009;
    public static final int MUST_BE_PROCESSED_DATA = 0x000a;
    public static final int INTERNAL_CHECKSUM_FAIL = 0x000b;
    public static final int ENCRYPTED_DATA_ERROR = 0x000c;

    public static final int USER_CANCEL = 0x0301;
    public static final int USER_BACK = 0x0302;
    public static final int CAPTURE_TIMEOUT = 0x0303;
    public static final int CAPTURE_FAKE_SUSPICIOUS = 0x0304;

    public static final int DEVICE_ALREADY_OPENED = 0x0204;
    public static final int DEVICE_NOT_OPENED = 0x0205;
    public static final int DEVICE_OPEN_FAIL  = 0x0201;
    public static final int INVALID_DEVICE_ID = 0x0202;

    public static final int FUNCTION_NOT_SUPPORTED = 0x0016;
    public static final int WRONG_DEVICE_ID = 0x0203;
    
    //Fast Search
    public static final int FASTSEARCH_INIT_FAIL  = 0x0401;
    public static final int FASTSEARCH_SAVE_DB  = 0x0402;
    public static final int FASTSEARCH_LOAD_DB  = 0x0403;
    public static final int FASTSEARCH_UNKNOWN_VER  = 0x0404;
    public static final int FASTSEARCH_IDENTIFY_FAIL  = 0x0405;
    public static final int FASTSEARCH_DUPLICATED_ID  = 0x0406;
    public static final int FASTSEARCH_IDENTIFY_STOP  = 0x0407;
    public static final int FASTSEARCH_NOUSER_EXIST  = 0x0408;
    public static final int FASTSEARCHIDENTIFY_FAIL  = 0x0405;
    
}
