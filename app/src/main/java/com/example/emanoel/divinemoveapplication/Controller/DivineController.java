package com.example.emanoel.divinemoveapplication.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Nullable;

import com.example.emanoel.divinemoveapplication.DataBase.DataBase;
import com.example.emanoel.divinemoveapplication.model.DivineImageWord;

public class DivineController extends DataBase {

    public static DivineController instance = null;

    private DivineController(@Nullable Context mContext) {
        super(mContext);
        createTable();
    }

    public static DivineController getInstance(Context context){
        if(instance == null){
            instance = new DivineController(context);
        }
        return instance;
    }

    @Override
    protected ContentValues createContentValues(Object object) {

        ContentValues cValues = new ContentValues();

        DivineImageWord dImageWord = (DivineImageWord) object;
        cValues.put("image",dImageWord.getImage());
        cValues.put("word",dImageWord.getWord());
        return cValues;
    }

    @Override
    protected String getTable() {
        return "dImageWordNew";
    }

    @Override
    protected String getCreateTableDescriptions() {
        return "id INTEGER PRIMARY KEY AUTOINCREMENT, "+"image int NOT NULL unique, "+"word varchar(24) NOT NULL unique";
    }

    @Override
    protected Object createRegisterObject(Cursor cursor) {

        int id = cursor.getInt(cursor.getColumnIndex("id"));
        int image = cursor.getInt(cursor.getColumnIndex("image"));
        String word = cursor.getString(cursor.getColumnIndex("word"));

        DivineImageWord divineImageWord = new DivineImageWord(id,image,word);
        return divineImageWord;
    }

    @Override
    protected String getUpdate(Object object) {
        DivineImageWord dImageWord = (DivineImageWord) object;
        return "image = '" + dImageWord.getImage() + "', word = '" + dImageWord.getWord() + "' WHERE id = '"+dImageWord.getId()+"'";
    }
}
