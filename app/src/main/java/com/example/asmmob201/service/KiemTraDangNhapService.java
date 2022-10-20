package com.example.asmmob201.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.asmmob201.dao.NguoiDungDAO;

public class KiemTraDangNhapService extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //Goi lai Bundle
        Bundle bundle = intent.getExtras();
        String user = bundle.getString("user");
        String pass = bundle.getString("pass");
        // Khoi tao DAO
        NguoiDungDAO nguoiDungDAO = new NguoiDungDAO(KiemTraDangNhapService.this);
        boolean check = nguoiDungDAO.kiemtraDangNhap(user, pass);
        //khoi tao Intent cho Broadcast
        Intent intentBR = new Intent();
        Bundle bundleBR = new Bundle();
        bundleBR.putBoolean("check", check);
        intentBR.putExtras( bundleBR);
        intentBR.setAction("KiemTraDangNhap");
        sendBroadcast(intentBR);
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
