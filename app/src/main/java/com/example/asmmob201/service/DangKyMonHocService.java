package com.example.asmmob201.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.asmmob201.dao.DangKyKhoaHocDAO;
import com.example.asmmob201.model.MonHoc;

import java.util.ArrayList;

public class DangKyMonHocService extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
         int id = bundle.getInt("id", -1);
        String code = bundle.getString("code", "");
        int isRegister = bundle.getInt("isRegister", -1);
        boolean isAll = bundle.getBoolean("isAll");
        boolean check;
        DangKyKhoaHocDAO dangKyKhoaHocDAO = new DangKyKhoaHocDAO(this);
        if (isRegister == id){
            check = dangKyKhoaHocDAO.huydkmonhoc(id, code);
        }else {
            check = dangKyKhoaHocDAO.dangkymonhoc(id, code);
        }

        ArrayList<MonHoc> list = new ArrayList<>();
        if (check){
            list = dangKyKhoaHocDAO.getdsMonHoc(id, isAll );
        }
        Intent intentBR = new Intent();
        Bundle bundleBR = new Bundle();
        bundleBR.putBoolean("check", check);
        bundleBR.putSerializable("list",list);
        intentBR.putExtras(bundleBR);
        intentBR.setAction("DKMonHoc");
        sendBroadcast(intentBR);
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
