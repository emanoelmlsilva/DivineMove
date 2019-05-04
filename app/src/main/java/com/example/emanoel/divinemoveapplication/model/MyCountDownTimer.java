package com.example.emanoel.divinemoveapplication.model;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MyCountDownTimer extends CountDownTimer {

    private Context mContext;
    private TextView myTextView;
    private long timeInFuture;
    private long interval;

    public MyCountDownTimer(Context mContext,TextView textView,long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.myTextView = textView;
        this.mContext = mContext;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        timeInFuture = millisUntilFinished;
        myTextView.setText(getCorretcTimer(true,millisUntilFinished)+":"+getCorretcTimer(false,millisUntilFinished));
    }

    @Override
    public void onFinish() {
        timeInFuture -= 1000;
        myTextView.setText(getCorretcTimer(true,timeInFuture)+":"+getCorretcTimer(false,timeInFuture));
        Toast.makeText(mContext,"FINISH!",Toast.LENGTH_SHORT).show();
    }

    private String getCorretcTimer(boolean isMinute, long millisUntilFinished){
        String aux;
        int constCalendar = isMinute ? Calendar.MINUTE : Calendar.SECOND;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millisUntilFinished);

        aux = calendar.get(constCalendar) < 10 ? "0"+calendar.get(constCalendar) : ""+calendar.get(constCalendar);

        return aux;
    }

}
