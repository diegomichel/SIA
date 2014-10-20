/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dmrr.asistenciasx;

import java.util.Calendar;

/**
 *
 * @author diego
 */
public class MyUtils {

    public MyUtils() {
    }

    public static Integer dayToInt(String day) {
        if(day.equals("dom"))
            return 1;
        if(day.equals("lun"))
            return 2;
        if(day.equals("mar"))
            return 3;
        if(day.equals("mie"))
            return 4;
        if(day.equals("jue"))
            return 5;
        if(day.equals("vie"))
            return 6;
        if(day.equals("sab"))
            return 7;
        
        return null;
    }

    public static String intToDay(Integer day) {
        switch (day) {
            case 1:
                return "dom";
            case 2:
                return "lun";
            case 3:
                return "mar";
            case 4:
                return "mie";
            case 5:
                return "jue";
            case 6:
                return "vie";
            case 7:
                return "sab";
        }
        return null;
    }
    public static Integer getDayOfWeek(){
         Calendar calendario = Calendar.getInstance();
         int day = calendario.get(Calendar.DAY_OF_WEEK);
         if(day == 1) day = 2;
         
         return day;
    }

    public static String getTodayString3Char() {
        Calendar calendario = Calendar.getInstance();
        int day = calendario.get(Calendar.DAY_OF_WEEK);
        
        //HACK: We dont work domingos, it will throw an SQL error anyways
        if(day == 1) day = 2;
        
        return intToDay(day);
    }
}
