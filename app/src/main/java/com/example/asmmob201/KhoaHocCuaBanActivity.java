package com.example.asmmob201;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asmmob201.adapter.DKKhoaHocAdapter;
import com.example.asmmob201.model.MonHoc;
import com.example.asmmob201.service.KHDangKySercive;

import java.util.ArrayList;

public class KhoaHocCuaBanActivity extends AppCompatActivity {
    RecyclerView Recycledkmn;
    int id;
    IntentFilter intentFilter;
    boolean isAll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khoa_hoc_cua_ban);

        Recycledkmn = findViewById(R.id.Recycle);
        TextView txtContent = findViewById(R.id.txtContent);
        ImageView ivhinh = findViewById(R.id.ivhinh);
        intentFilter = new IntentFilter();
        intentFilter.addAction("DSMonHoc");
        intentFilter.addAction("DKMonHoc");

        //lấy id của ngdung đăng nhập
        SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN", MODE_PRIVATE);
        id = sharedPreferences.getInt("id", -1);

        // lấy giá trị isall
        Intent intentIsAll = getIntent();
        Bundle bundleIsAll = intentIsAll.getExtras();
         isAll = bundleIsAll.getBoolean("isAll");
        if (isAll){
            txtContent.setText("Đăng ký khoá học");
        }else {
            txtContent.setText("Khoá học của bạn");
        }

        Intent intent = new Intent(this, KHDangKySercive.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putBoolean("isAll", isAll);
        intent.putExtras(bundle);
        startService(intent);

    }
    private void loadData(ArrayList<MonHoc> list){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        Recycledkmn.setLayoutManager(layoutManager);
        DKKhoaHocAdapter adapter = new DKKhoaHocAdapter(this, list, id, isAll);
        Recycledkmn.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(myBroadcast, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myBroadcast);
    }

    private BroadcastReceiver myBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()){
                case "DSMonHoc":
                case "DKMonHoc":
                    Bundle bundle = intent.getExtras();
                    boolean check = intent.getBooleanExtra("check", true);

                    if (check){
                        ArrayList<MonHoc> list = (ArrayList<MonHoc>) bundle.getSerializable("list");
                        loadData(list);
                    }else {
                        Toast.makeText(context, "Thất bại", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };
}