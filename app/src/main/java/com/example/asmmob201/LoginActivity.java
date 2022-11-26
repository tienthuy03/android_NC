package com.example.asmmob201;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asmmob201.service.KiemTraDangNhapService;

public class LoginActivity extends AppCompatActivity {
    IntentFilter intentFilter;
    EditText edtUser, edtPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtUser = findViewById(R.id.edtUser);
        edtPass = findViewById(R.id.edtPass);
        Button btnDangnhap = findViewById(R.id.btnDangnhap);
        TextView txtDangky = findViewById(R.id.txtDangky);
        txtDangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        //Gọi lại intentfilter để add hành dộng
        intentFilter = new IntentFilter();
        intentFilter.addAction("KiemTraDangNhap");
        CheckBox chkGhinho = findViewById(R.id.chkghinho);
        // gọi ra đối tượng getSharedPreferences
        SharedPreferences preferences = getSharedPreferences("data", MODE_PRIVATE);
        // .edit nhận giá trị từ SharedPreferences.Editor
        SharedPreferences.Editor editor = preferences.edit();
        //xét cho checkbox
        boolean isRemember = preferences.getBoolean("ghinho", false);
        // xét điều kiện cho checkbox
        if (isRemember) {
            Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
        btnDangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = edtUser.getText().toString();
                String pass = edtPass.getText().toString();
                if (user.length() == 0) {
                    edtUser.requestFocus();
                    edtUser.setError("Vui lòng nhập tên đăng nhập");
                } else if (pass.length() == 0) {
                    edtPass.requestFocus();
                    edtPass.setError("Vui lòng nhập mật khẩu");
                }
                editor.putBoolean("ghinho", chkGhinho.isChecked());
                editor.commit();
                // Gọi Intent
                Intent intent = new Intent(LoginActivity.this, KiemTraDangNhapService.class);
                //Gọi Bundle == > Gửi data
                Bundle bundle = new Bundle();
                bundle.putString("user", user);
                bundle.putString("pass", pass);
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
    // Khoi tao Broadcast
    public BroadcastReceiver myBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // goi ra hanh dong cho Broadcast
            switch (intent.getAction()) {
                case "KiemTraDangNhap":
                    Bundle bundle = intent.getExtras();
                    boolean check = bundle.getBoolean("check");
                    if (check) {
                        Toast.makeText(context, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    } else {
                        Toast.makeText(context, "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
                    }
                    break;

                default:
                    break;
            }
        }
    };
}