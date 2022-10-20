package com.example.asmmob201.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asmmob201.database.DbHelper;
import com.example.asmmob201.model.MonHoc;
import com.example.asmmob201.model.ThongTin;

import java.util.ArrayList;

public class DangKyKhoaHocDAO {
    private DbHelper dbHelper;
    public DangKyKhoaHocDAO(Context context){
        dbHelper = new DbHelper(context);

    }
    public ArrayList<MonHoc> getdsMonHoc(int id, boolean isAll){
        ArrayList<MonHoc> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor;
        if (isAll){
           cursor = sqLiteDatabase.rawQuery("SELECT mh.code, mh.name, mh.teacher, dk.id FROM MONHOC mh LEFT JOIN DANGKY dk  ON mh.code = dk.code AND dk.id = ? ", new String[]{String.valueOf(id)});
        }else {
            cursor =sqLiteDatabase.rawQuery("SELECT mh.code, mh.name, mh.teacher, dk.id FROM MONHOC mh INNER JOIN DANGKY dk  ON mh.code = dk.code WHERE dk.id = ? ", new String[]{String.valueOf(id)});
        }
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                list.add(new MonHoc(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), getThongTinMonHoc(cursor.getString(0))));
            }while (cursor.moveToNext());
        }
        return list;
    }
    //đăng ký
    public boolean dangkymonhoc(int id, String code){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("code", code);
        long check = sqLiteDatabase.insert("DANGKY",null, values );
        if (check == -1){
            return false;
        }else {
            return true;
        }
    }
    //Huỷ đăng ký
    public boolean huydkmonhoc(int id, String code){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        long check = sqLiteDatabase.delete("DANGKY","id = ? AND code = ?", new String[]{String.valueOf(id), code});
        if (check == -1){
            return false;
        }else {
            return true;
        }
    }
    public ArrayList<ThongTin> getThongTinMonHoc(String code){
        ArrayList<ThongTin> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT date, address FROM THONGTIN WHERE code = ?", new String[]{String.valueOf(code)});
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            do{
                list.add(new ThongTin(cursor.getString(0), cursor.getString(1)));
            }while (cursor.moveToNext());
        }
        return list;
    }
}
