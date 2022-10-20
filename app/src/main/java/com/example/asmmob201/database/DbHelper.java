package com.example.asmmob201.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context){
        super(context, "DANGKYKHOAHOC", null, 2);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String tbNguoiDung = "CREATE TABLE NGUOIDUNG (id integer primary key autoincrement, username text, password text, name text)";
        sqLiteDatabase.execSQL(tbNguoiDung);

        String tbMonHoc = "CREATE TABLE MONHOC (code text primary key, name text, teacher text )";
        sqLiteDatabase.execSQL(tbMonHoc);

        String tbThongTin = "CREATE TABLE THONGTIN (id integer primary key autoincrement, code text, date text, address text)";
        sqLiteDatabase.execSQL(tbThongTin);

        String tbDangKy = "CREATE TABLE DANGKY (id integer, code text)";
        sqLiteDatabase.execSQL(tbDangKy);

        //DŨ LIỆU MẪU
        //data người dùng (user)
        String insNguoiDung = "INSERT INTO NGUOIDUNG VALUES(1,'tridinh','123456','Trí Định'),(2,'minhtri','123abc123','Minh Trí')";
        sqLiteDatabase.execSQL(insNguoiDung);
        //data môn học (course)
        String insMonHoc = "INSERT INTO MONHOC VALUES('MOB201','Android Nâng Cao','Nguyễn Trí Định'),('MOB306','React Native','Trần Anh Hùng'),('MOB2041','Dự Án Mẫu','Nguyễn Trí Định'),('MOB202','Thiết kế giao diện Android','Trần Thị Hường'), ('MOB1023','Lập Trình Java 2','Nguyễn Trí Định') ";
        sqLiteDatabase.execSQL(insMonHoc);
        //data thông tin lịch học từng môn (info)
        String insThongTin ="INSERT INTO THONGTIN VALUES(1, 'MOB201', 'Ca 2 - 19/09/2022', 'T1011'),(2, 'MOB201', 'Ca 2 - 21/09/2022', 'T1011'),(3, 'MOB201', 'Ca 2 - 23/09/2022', 'T1011')," +
                "(4, 'MOB306', 'Ca 5 - 20/09/2022', 'F204'),(5, 'MOB306', 'Ca 5 - 22/09/2022', 'F204'),(6, 'MOB2041', 'Ca 1 - 20/09/2022', 'Online - https://meet.google.com/rku-beuk-wqu'),(7, 'MOB202', 'Ca 2 - 27/09/2022', 'T1012'),(8, 'MOB1032', 'Ca 4 - 28/09/2022', 'T1015')";
        sqLiteDatabase.execSQL(insThongTin);
        //id: id người dùng, code: mã môn học đăng ký
        //data các môn học mà người dùng đã đăng ký
        String insDangKy ="INSERT INTO DANGKY VALUES(1,'MOB201'),(1,'MOB306'),(2,'MOB306'),(2,'MOB1023'),(1,'MOB202')";
        sqLiteDatabase.execSQL(insDangKy);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if(i != i1){
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS NGUOIDUNG");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS MONHOC");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS THONGTIN");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS DANGKY");
            onCreate(sqLiteDatabase);
        }
    }
}
