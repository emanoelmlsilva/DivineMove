package com.example.emanoel.divinemoveapplication.model;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DialogMessage {

    private static AlertDialog.Builder builder;

    public DialogMessage(Context myContext,int idStyle){
        builder = new AlertDialog.Builder(myContext,idStyle);
    }

    public static void getMessage(String title,final BackToMenu back){
        builder.setTitle(title);

//                configura cancelamento
        builder.setCancelable(false);

//                Configura icone
        builder.setIcon(android.R.drawable.ic_dialog_info);

//                Configurar opções de butoes sim ou nao
        builder.setPositiveButton("ok",new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                back.popBack();
            }
        });

        builder.create();
        builder.show();
    }

    public interface BackToMenu{
        void popBack();
    }

}
