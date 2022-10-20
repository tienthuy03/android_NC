package com.example.asmmob201.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.asmmob201.dao.NguoiDungDAO;

public class TaoTaiKhoanService extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Gọi ra Bundle
        Bundle bundle = intent.getExtras();
        String username = bundle.getString("user");
        String password = bundle.getString("pass");
        // Khởi tạo lại dao
        NguoiDungDAO nguoiDungDAO = new NguoiDungDAO(TaoTaiKhoanService.this);
        boolean check = nguoiDungDAO.kiemtraDangKy(username, password);
            Intent intentBr = new Intent();
            Bundle bundleBr = new Bundle();
            bundleBr.putBoolean("KiemTraDangKy", check);
            intentBr.putExtras(bundleBr);
            intentBr.setAction("KiemTraDangKy");
            sendBroadcast(intentBr);

        return super.onStartCommand(intent, flags, startId);
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
