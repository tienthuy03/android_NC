package com.example.asmmob201;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class KhoaHocActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khoa_hoc);

        Button btnDKKhoaHoc = findViewById(R.id.btnDKKhoaHoc);
        Button btnKhoaHocDK = findViewById(R.id.btnKhoaHocDK);
        ImageView ivThoat = findViewById(R.id.ivThoat);

        ivThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KhoaHocActivity.this, MainActivity.class));
            }
        });
        btnDKKhoaHoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(KhoaHocActivity.this, R.anim.move);
                view.startAnimation(animation);
                Intent intent = new Intent(KhoaHocActivity.this, KhoaHocCuaBanActivity.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean("isAll", true);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        btnKhoaHocDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(KhoaHocActivity.this, R.anim.move);
                view.startAnimation(animation);
                Intent intent = new Intent(KhoaHocActivity.this, KhoaHocCuaBanActivity.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean("isAll", false);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
}