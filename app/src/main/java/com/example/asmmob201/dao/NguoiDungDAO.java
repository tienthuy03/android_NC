package com.example.asmmob201.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.asmmob201.LoginActivity;
import com.example.asmmob201.RegisterActivity;
import com.example.asmmob201.database.DbHelper;

public class NguoiDungDAO {
    DbHelper dbHelper;
    SharedPreferences sharedPreferences;
    public NguoiDungDAO(Context context){
        //khoi tao dbhelper
        dbHelper = new DbHelper(context);
        sharedPreferences = context.getSharedPreferences("THONGTIN", Context.MODE_PRIVATE);
    }

    //ham ktr thong tin dang nhap
    public boolean kiemtraDangNhap(String user, String pass) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM NGUOIDUNG WHERE username = ? AND password = ?", new String[]{user, pass});
        if (cursor.getCount()!=0){
            cursor.moveToFirst();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("id", cursor.getInt(0));
            editor.commit();
            return true;
        }else {
            return false;
        }
    }
    public boolean kiemtraDangKy(String user, String pass) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("user", user);
        contentValues.put("pass", pass);
        sqLiteDatabase.insert("NGUOIDUNG", null, contentValues );
       return true;
    }



}

