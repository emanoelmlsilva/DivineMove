package com.example.emanoel.divinemoveapplication.model;

public class DivineImageWord {

    private int id,image;
    private String word;

    public DivineImageWord(int id,int image,String word){
        this.id = id;
        this.image = image;
        this.word = word;
    }

    public DivineImageWord(int image,String word){
        this(0,image,word);
    }

    public int getImage(){
        return this.image;
    }

    public void setImage(int image){
        this.image = image;
    }

    public String getWord(){
        return this.word;
    }

    public void setWord(String word){
        this.word = word;
    }

    public int getId(){
        return this.id;
    }
}
