/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dmrr.asistenciasx;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author diegomichel
 */
public class Configuracion {
    private static final Properties properties = new Properties();

    static {
        try {
            properties.load(new FileInputStream("config.txt"));
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void set(String k, Boolean value){
        properties.setProperty(k, value.toString());
        
        try {
            properties.store(new FileOutputStream("config.txt"),"");
        } catch (IOException ex) {
            Logger.getLogger(Configuracion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public Boolean get(String k){
        String propertie = properties.getProperty(k);
        return Boolean.parseBoolean(propertie);
    }
   

}
