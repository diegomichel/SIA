/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dmrr.asistenciasx;

import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JLabel;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author diego
 */
public class Clock {
    private JLabel Jlabel;
    private String dateFormat;
    public Clock(JLabel label,String format) {
        Jlabel = label;
        dateFormat = format;
        final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        Runnable updateClock = new Runnable() {
            long thisTimer = 0;
            Calendar calendar = Calendar.getInstance();
            
            DateTime jodaTime;
            DateTimeFormatter formatter = DateTimeFormat.forPattern(dateFormat);

            @Override
            public void run() {
                jodaTime = new DateTime();
                Jlabel.setText(jodaTime.toString(formatter));
            }
        };
        service.scheduleWithFixedDelay(updateClock, 1, 1, TimeUnit.SECONDS);
    }
    
}
