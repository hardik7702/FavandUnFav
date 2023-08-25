package com.slayertech.retrofitexample.databasenn;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.slayertech.retrofitexample.Modelclass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyDatabase extends SQLiteOpenHelper {

    public MyDatabase(@Nullable Context context) {
        super( context, "myconversation.db", null, 1 );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table conversationsave(alid text,id integer primary key, title text,img text,bookmark text)";
        db.execSQL( query );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate( db );
        String query = "drop table if exists conversationsave";
        db.execSQL(query);
    }

    public boolean insertData(String alid,int id, String title,String img,String bookmark) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("alid", alid);
        contentValues.put("id", id);
        contentValues.put("title", title);
        contentValues.put("img", img);
        contentValues.put("bookmark", bookmark);
        database.insert("conversationsave", null, contentValues);
        return false;
    }


    public void Update_converation(String str, String updatevalue) {

        SQLiteDatabase db = getWritableDatabase();
        Log.e("qry", "update conversationsave set bookmark='" + updatevalue + "' where id=" + str);
        String qr = "update conversationsave set bookmark='" + updatevalue + "' where id=" + str;
        db.execSQL(qr);
    }


    public boolean Delete_History_id(String str) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        StringBuilder sb = new StringBuilder();
        sb.append("id=");
        sb.append(str);
        Log.e( "TssAG", "Delete_History_id: "  );
        return writableDatabase.delete("conversationsave", sb.toString(), null) > 0;
    }



    public boolean Delete_Coversation_idall() {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        return writableDatabase.delete( "conversationsave", null, null ) > 0;
    }

    public Cursor getData() {
        SQLiteDatabase database = getReadableDatabase();
        String query = "select * from conversationsave";
        Cursor cursor = database.rawQuery(query, null);
        return cursor;
    }

    public Cursor getBookMarkData() {
        SQLiteDatabase database = getReadableDatabase();
        String query = "select * from conversationsave where bookmark" ;
        Cursor cursor = database.rawQuery(query, null);
        return cursor;
    }

    //----Data---GET--IN--SAVEDCHAT--FRAGMENT-----
//    https://androidtutorialsdemo.blogspot.com/2021/07/favourite-list-in-android-studio-sqlite.html

}
