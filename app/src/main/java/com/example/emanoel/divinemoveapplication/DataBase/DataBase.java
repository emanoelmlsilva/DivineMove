package com.example.emanoel.divinemoveapplication.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class DataBase<Item> extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DivineFinal.DB";

    public DataBase(@Nullable Context mContext){
        super(mContext,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "DROP TABLE"+ getTable();
        db.execSQL(sql);
        onCreate(db);

    }

    protected void createTable(){
        executeSql("CREATE TABLE IF NOT EXISTS "+ getTable() + " ( " + getCreateTableDescriptions() + " ) ");
    }

    protected  void executeSql(String SQL){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(SQL);
        db.close();
    }

    public void addObject(Item object){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = createContentValues(object);
        db.insert(getTable(),null,contentValues);
        db.close();
    }

    public List<Item> allObject(){

        List<Item> listObject = new ArrayList<>();
        String sql = "SELECT * FROM " + getTable();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
                listObject.add(createRegisterObject(cursor));
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();;
        return listObject;
    }

    public void updateObject(Item object){
        String sql = "UPDATE" + getTable() + "SET" + getUpdate(object);
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
        db.close();
    }

    public void delete(int id){
        String sql = "DELETE FROM '" + getTable() + "' WHERE " + "id = '" + id + "'";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
        db.close();
    }

    protected  abstract  ContentValues createContentValues(Item object);

    protected  abstract String getTable();

    protected  abstract String getCreateTableDescriptions();

    protected  abstract Item createRegisterObject(Cursor cursor);

    protected  abstract  String getUpdate(Item object);
}
