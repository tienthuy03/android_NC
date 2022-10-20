package com.example.asmmob201;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ManhinhchaoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manhinhchao2);
        ImageView ivlogo = findViewById(R.id.ivlogo);
        Glide.with(ManhinhchaoActivity.this).load(R.drawable.splash).into(ivlogo);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(ManhinhchaoActivity.this, Manhinhc2Activity.class));
            }
        },3000);
    }
}