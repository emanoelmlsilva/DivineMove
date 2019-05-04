package com.example.emanoel.divinemoveapplication.chronometer;

import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;

public class ChronometerDivine {

    private static Chronometer cronometro;
    private static long timer = 0;
    private static boolean timefinish = false;

    public ChronometerDivine(View viewChronometer) {

        cronometro = (Chronometer) viewChronometer;
//        finishTime();

    }

    /*
    Começa a contagem do cronômetro
     */
    public static void startChronometer() {
        timer = SystemClock.elapsedRealtime();
        cronometro.setBase(SystemClock.elapsedRealtime());
        cronometro.start();
    }

    /*
    Para o cronômetro e retorna o tempo
     */
    public static void stopAndResetChronometer() {
        cronometro.stop();
    }

    public static void finishTime(final TimeOver timeEnd){
        cronometro.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                timer = SystemClock.elapsedRealtime() - chronometer.getBase();
                timefinish  = timer >= 30000 ? true:false;
                if(timefinish){
                    timeEnd.finishOver();
                }
            }
        });
    }

    public static void resetTime(){
        stopAndResetChronometer();
        startChronometer();
    }


    public interface TimeOver{
        void finishOver();
    }

}