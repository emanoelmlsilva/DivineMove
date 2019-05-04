package com.example.emanoel.divinemoveapplication.model;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import com.example.emanoel.divinemoveapplication.Controller.DivineController;
import com.example.emanoel.divinemoveapplication.DataBase.DataBase;
import com.example.emanoel.divinemoveapplication.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChoseWord {

    private char [] listWord;
    private int tamanho,image,random = 0,lengthKeyboard;
    private String move;
    private DataBase<DivineImageWord> dbDivine;
    private AssetManager assetManagerDivine;
    private InputStream inputStream;
    private String[] recoverDivine;
    private List<Integer> listRandom;

    public ChoseWord(Context mContext){
        dbDivine = DivineController.getInstance(mContext);
        try {
            toRecoverDivine(mContext);
        } catch (IOException e) {
            e.printStackTrace();
        }
        listWord = new char[]{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','w','x','y','z','-',' ','.'};
        listRandom = new ArrayList<>();
        tamanho = 3;
        lengthKeyboard = 24;
    }

    private void toRecoverDivine(Context mContext) throws IOException {

        assetManagerDivine = mContext.getAssets();
        inputStream = null;
        recoverDivine = null;
        DivineImageWord divineImageWord = null;

        try{

            inputStream = assetManagerDivine.open(getDirectory()+getFileName());
            int siize = inputStream.available();
            byte[] buffer = new byte[siize];
            inputStream.read(buffer);
            inputStream.close();
            recoverDivine = new String(buffer).split("\n");
            recoverDivine[0].split(",");
            String[] listDivine = null;
            for(String stringDivine:recoverDivine){
                listDivine = stringDivine.split(",");
                String image = listDivine[0];
                String move = listDivine[1];
                int resId = mContext.getResources().getIdentifier(image,"drawable",mContext.getPackageName());//pega o id do drawable se for 0 não existe um drawable
                if(resId == 0){
                    System.out.println("erro drawable não exites");
                }else if(!searchForDivineIquals(move,resId)){
                    divineImageWord = new DivineImageWord(resId,move);
                    dbDivine.addObject(divineImageWord);
                }
            }


        }catch (IOException erro){
            erro.printStackTrace();
        }

    }

    public String mountLines(){
        String lines = "";
        for(int i = 0;i < move.length();i++){
            lines += "-";
        }
        return  lines;
    }

    public boolean checkWordEquals(int indexNameCheck,String word){
        String nameMove = dbDivine.allObject().get(indexNameCheck).getWord().toLowerCase();
        return nameMove.equals(word.toLowerCase());
    }

    public char[] choseWordKeyboard(){
        char arrayWordSort[] = new char[lengthKeyboard];
        for(int i = 0;i < move.length();i++){
            arrayWordSort[i] = move.charAt(i);
        }

        for(int i = move.length(); i < lengthKeyboard;i++){
            int numberRandom = new Random().nextInt(listWord.length);
            arrayWordSort[i] = listWord[numberRandom];
        }

        return shufflesWord(arrayWordSort);
    }

    private char[] shufflesWord(char[] words){
        char aux;
        int random;
        for(int i = 0;i < words.length;i++){
            random = new Random().nextInt(lengthKeyboard);
            aux = words[random];
            words[random] = words[i];
            words[i] = aux;
        }

        return words;
    }

    public void choseWordImage(){
        random = new Random().nextInt(dbDivine.allObject().size());
        if(listLakeRandom(random)){
            choseWordImage();
        }else{
            image = dbDivine.allObject().get(random).getImage();
            move = dbDivine.allObject().get(random).getWord();
        }
    }

    private boolean listLakeRandom(int random){
        if(!allRandom()){
            for(int randomLake:listRandom){
                if(randomLake == random){
                    return true;
                }
            }
            if(listRandom.size() < dbDivine.allObject().size()){
                listRandom.add(random);
            }
        }

        return false;
    }

    public boolean allRandom(){
        return listRandom.size() == dbDivine.allObject().size() ?true:false;
    }

    public int getImage(){
        return this.image;
    }

    public String getMove(){
        return this.move;
    }

    public int getPositionImageArray(int idImage){
        for(int i = 0;i < dbDivine.allObject().size();i++){
            if(idImage == dbDivine.allObject().get(i).getImage()){
                return i;
            }
        }
        return -1;
    }

    private boolean searchForDivineIquals(String name,int image){
        for(DivineImageWord divineImageWords: dbDivine.allObject()){
            if(divineImageWords.getWord().equals(name) || divineImageWords.getImage() == image){
                return true;
            }
        }
        return false;

    }

    private String getDirectory(){
        return "archive/";
    }

    private String getFileName(){
        return "arquivosImageString";
    }

    public int getSize(){
        return dbDivine.allObject().size();
    }
}
