package com.example.asmmob201;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asmmob201.service.TaoTaiKhoanService;

public class RegisterActivity extends AppCompatActivity {
    IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText edtUser = findViewById(R.id.edtUser);
        EditText edtPass = findViewById(R.id.edtPass);
        EditText edtRepass = findViewById(R.id.edtRepass);
        Button btnDangky = findViewById(R.id.btnDangky);

        //Tạo intentfilter
        intentFilter = new IntentFilter();
        intentFilter.addAction("KiemTraDangKy");

        btnDangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUser.getText().toString();
                String password = edtPass.getText().toString();
                String repass = edtRepass.getText().toString();
                if (username.length() == 0 || password.length() == 0 || repass.length() == 0) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập thông tin", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(repass)){
                    Toast.makeText(RegisterActivity.this, "Mật khẩu sai", Toast.LENGTH_SHORT).show();
                }
                    // Gọi Intent
                    Intent intent = new Intent(RegisterActivity.this, TaoTaiKhoanService.class);
                    //Gọi Bundle == > Gửi data
                    Bundle bundle = new Bundle();
                    bundle.putString("user", username);
                    bundle.putString("pass", password);
                    bundle.putString("repass", repass);
                    intent.putExtras(bundle);
                    //goi starService
                    startService(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(myBroadcast, intentFilter);
    }

    public BroadcastReceiver myBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case "KiemTraDangKy":
                    Bundle bundleDK = intent.getExtras();
                    boolean check = bundleDK.getBoolean("check");
                    if (!check) {
                        Toast.makeText(context, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    } else {
                        Toast.makeText(context, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                    }
            }
        }
    };
}