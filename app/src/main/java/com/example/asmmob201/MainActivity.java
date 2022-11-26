package com.example.asmmob201;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    private AlphaAnimation buttonClick = new AlphaAnimation(4F, 0.5F);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout linearLayout = findViewById(R.id.linear);
        Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.move);
        linearLayout.startAnimation(animation);
        LinearLayout linearLayout1 = findViewById(R.id.linear1);
        Animation animation1 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.move);
        linearLayout1.startAnimation(animation1);


        Button btnKhoaHoc = findViewById(R.id.btnkhoahoc);
        Button btnMap = findViewById(R.id.btnmap);
        Button btnNew  = findViewById(R.id.btnew);
        Button btnXaHoi = findViewById(R.id.btnxahoi);
        ImageView ivThoat = findViewById(R.id.ivThoat);

        btnKhoaHoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, KhoaHocActivity.class));
            }
        });
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MapsActivity.class));
            }
        });
        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, NewActivity.class));
            }
        });
        btnXaHoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, XaHoiActivity.class));
            }
        });
        ivThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( MainActivity.this, LoginActivity.class));
            }
        });
    }

}