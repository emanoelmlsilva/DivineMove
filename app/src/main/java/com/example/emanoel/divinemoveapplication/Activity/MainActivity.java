package com.example.emanoel.divinemoveapplication.Activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.emanoel.divinemoveapplication.R;
import com.example.emanoel.divinemoveapplication.model.ChoseWord;
import com.example.emanoel.divinemoveapplication.model.DialogMessage;
import com.example.emanoel.divinemoveapplication.model.MyCountDownTimer;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private Button jogar;
    private ChoseWord chose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chose = new ChoseWord(this);
        jogar = findViewById(R.id.btn_jogar);
        jogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent play = new Intent(MainActivity.this,PlayActivity.class);
                startActivity(play);
            }
        });
    }

}
