package com.example.emanoel.divinemoveapplication.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emanoel.divinemoveapplication.R;
import com.example.emanoel.divinemoveapplication.chronometer.ChronometerDivine;
import com.example.emanoel.divinemoveapplication.model.ChoseWord;
import com.example.emanoel.divinemoveapplication.model.DialogMessage;

public class PlayActivity extends AppCompatActivity implements View.OnClickListener {

    private ChoseWord userWord;
    private Button[] listButton;
    private int[] listIdbutton = new int[]{R.id.btnAction1,R.id.btnAction2,R.id.btnAction3,R.id.btnAction4,R.id.btnAction5,R.id.btnAction6,R.id.btnAction7,R.id.btnAction8,R.id.btnAction9,R.id.btnAction10,R.id.btnAction11,R.id.btnAction12,R.id.btnAction13,R.id.btnAction14,R.id.btnAction15,R.id.btnAction16,R.id.btnAction17,R.id.btnAction18,R.id.btnAction19,R.id.btnAction20,R.id.btnAction21,R.id.btnAction22,R.id.btnAction23,R.id.btnAction24};
    private ImageButton btn_backSpace;
    private TextView showTraceWord,textCorrect;
    private ImageView imageMove,heart1,heart2,heart3;
    private int positionLastWord,positionSelections,contLoseLife,totalCorrect,actualCorrect;
    private int[] buttonsSelecion;
    private static final int SPLASH_TIME_OUT = 1000;
    private char[] words;
    private boolean alreadyChecked;
    private ChronometerDivine chronometer;
    private DialogMessage alertDialog;
    private Intent backMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
    }

    public void onResume(){
        super.onResume();
        init();
        starTime();
    }

    public void onPause(){
        super.onPause();
        chronometer.stopAndResetChronometer();
    }

    private void init(){
        contLoseLife = 3;
        backMain = new Intent(PlayActivity.this,MainActivity.class);
        actionButtons();
        allAction();
    }

    private void actionButtons(){
        alertDialog = new DialogMessage(this,R.style.DialogStyle);
        listButton = new Button[24];
        chronometer = new ChronometerDivine(findViewById(R.id.chronometer));
        userWord = new ChoseWord(this);
        imageMove = findViewById(R.id.imageView);
        textCorrect = findViewById(R.id.textViewCorrect);
        showTraceWord = findViewById(R.id.textNameFind);
        btn_backSpace = findViewById(R.id.btn_back_space);
        buttonsSelecion =  new int[24];
        totalCorrect = userWord.getSize();
        actualCorrect = 0;
        heart1 = findViewById(R.id.heart1);
        heart2 = findViewById(R.id.heart2);
        heart3 = findViewById(R.id.heart3);
        initButtons();
        setTextAll();
    }

    private void initButtons(){
        for(int i = 0;i < listButton.length;i++){
            listButton[i] = findViewById(listIdbutton[i]);
        }
    }

    private void setButtons(){
        for(int i = 0;i < listButton.length;i++){
            listButton[i].setText(String.valueOf(words[i]));
        }
    }

    private void allAction(){
        for(int i = 0;i < listButton.length;i++){
            setOnclick(listButton[i]);
        }

        btn_backSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alreadyChecked = false;
                backSpaceClickButton();
            }
        });

    }

    private void setOnclick(Button buttonAction){
        buttonAction.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        Button buttonAll = (Button)view;
        takesWordClickButton(buttonAll.getText().toString().toCharArray()[0],buttonAll);
        checkNameEqualsImage();
    }

    private void setTextAll(){
        alreadyChecked = false;
        positionLastWord = 0;
        positionSelections = 0;
        userWord.choseWordImage();
        showTraceWord.setText(userWord.mountLines());
        textCorrect.setText(actualCorrect+"/"+totalCorrect);
        words = userWord.choseWordKeyboard();
        imageMove.setImageResource(userWord.getImage());
        setButtons();
    }

    private void takesWordClickButton(char word,Button button){
        char[] newWordLine = showTraceWord.getText().toString().toCharArray();
        for(int i = 0;i < userWord.getMove().length();i++){
            if(newWordLine[i] == '-'){
                newWordLine[i] = word;
                showTraceWord.setText(String.valueOf(newWordLine).toUpperCase());
                positionLastWord++;
                buttonVilibilite(button);
                break;
            }
        }
    }

    private void backSpaceClickButton(){
        char[] newWordLine = showTraceWord.getText().toString().toCharArray();
        if(positionLastWord > 0) {
            newWordLine[--positionLastWord] = '-';
            buttonVilibilite(takeesButtonLastSelection());
        }
        if(positionLastWord == 0 && newWordLine[0] != '-'){
            newWordLine[0] = '-';
            positionLastWord = 0;
        }
        showTraceWord.setText(String.valueOf(newWordLine));
    }

    private void buttonVilibilite(Button visiviliteButton){
        if(visiviliteButton.getVisibility() == View.INVISIBLE){
            visiviliteButton.setVisibility(View.VISIBLE);
        }else{
            visiviliteButton.setVisibility(View.INVISIBLE);
            addButtonsSelection(visiviliteButton.getId());
        }
    }

    private void addButtonsSelection(int idButton){
        buttonsSelecion[positionSelections++] = idButton;
    }

    private Button takeesButtonLastSelection(){
        return (Button) findViewById(buttonsSelecion[--positionSelections]);
    }

    private void checkNameEqualsImage(){
        if(positionLastWord == userWord.getMove().length() && !alreadyChecked){
            if(userWord.checkWordEquals(userWord.getPositionImageArray(userWord.getImage()),showTraceWord.getText().toString())){
                Toast.makeText(getApplicationContext(),"resposta certa",Toast.LENGTH_SHORT).show();
                actualCorrect++;
                nextDivine();
            }else{
                Toast.makeText(getApplicationContext(),"resposta errada",Toast.LENGTH_SHORT).show();
                alreadyChecked = true;
            }
        }
    }

    private void nextDivine(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i("textando","next");
                resetTime();
                if(contLoseLife > 0) {
                    finishAllRandom();
                    loadingNextImage();
                }

            }
        },SPLASH_TIME_OUT);
    }

    private void loadingNextImage(){
        setTextAll();
        resetButton();

    }

    private void resetButton(){
        for(int i = 0;i < listButton.length;i++){
            listButton[i].setVisibility(View.VISIBLE);
        }
    }

    private void starTime(){

        chronometer.startChronometer();
        chronometer.finishTime(new ChronometerDivine.TimeOver() {
            @Override
            public void finishOver() {
                nextDivine();
                loseLife();
            }
        });

    }

    private void resetTime(){
        finishPlay();
        if(contLoseLife > 0) {
            chronometer.resetTime();
        }else{
            chronometer.stopAndResetChronometer();
        }

    }

    private void loseLife(){

        switch (contLoseLife--){
            case 3:
                heart1.setImageResource(R.drawable.heartbroken);
                break;
            case 2:
                heart2.setImageResource(R.drawable.heartbroken);
                break;
            case 1:
                heart3.setImageResource(R.drawable.heartbroken);
                break;
        }

    }

//    private  void recoversLife(){
//        contLoseLife = 3;
//        heart1.setImageResource(R.drawable.heart);
//        heart2.setImageResource(R.drawable.heart);
//        heart3.setImageResource(R.drawable.heart);
//    }

    private void finishPlay(){
        if(contLoseLife == 0){
            chronometer.stopAndResetChronometer();
            alertDialog.getMessage("Jogo finalizado",new DialogMessage.BackToMenu() {
                @Override
                public void popBack() {
                    startActivity(backMain);
                }
            });
        }
    }

    private void finishAllRandom(){
        if(userWord.allRandom()){
            alertDialog.getMessage("Todas as imagens foram utilizadas", new DialogMessage.BackToMenu() {
                @Override
                public void popBack() {
                    startActivity(backMain);
                }
            });
        }
    }


}

