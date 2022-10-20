package com.example.asmmob201.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asmmob201.MainActivity;
import com.example.asmmob201.R;
import com.example.asmmob201.model.MonHoc;
import com.example.asmmob201.model.ThongTin;
import com.example.asmmob201.service.DangKyMonHocService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class DKKhoaHocAdapter extends RecyclerView.Adapter<DKKhoaHocAdapter.ViewHolder> {
    private Context context;
    private ArrayList<MonHoc> list;
    private int id;
    private boolean isAll;
    public DKKhoaHocAdapter(Context context, ArrayList<MonHoc> list, int id, boolean isAll) {
        this.context = context;
        this.list = list;
        this.id = id;
        this.isAll = isAll;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.itemkhoahoc, parent, false);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.listview);
        view.startAnimation(animation);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.txtCode.setText(String.valueOf(list.get(position).getCode()));
            holder.txtName.setText(list.get(position).getName());
            holder.txtTeacher.setText(list.get(position).getTeacher());
            holder.ivhinh.setImageResource(R.drawable.course);

            if (list.get(position).getIsRegister() == id){
                holder.btnTrangThai.setText("Huỷ đăng ký môn học");
                holder.btnTrangThai.setBackgroundColor(Color.parseColor("#FF1E00"));
                holder.btnTrangThai.setTextColor(Color.WHITE);
            }else {
                holder.btnTrangThai.setText("Đăng ký môn học");
                holder.btnTrangThai.setBackgroundColor(Color.parseColor("#59CE8F"));
                holder.btnTrangThai.setTextColor(Color.WHITE);
            }
            holder.btnTrangThai.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DangKyMonHocService.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", id);
                    bundle.putString("code",list.get(holder.getAdapterPosition()).getCode());
                    bundle.putInt("isRegister", list.get(holder.getAdapterPosition()).getIsRegister());
                    bundle.putBoolean("isAll", isAll);
                    intent.putExtras(bundle);
                    context.startService(intent);
                }
            });
            holder.ivChitiet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ShowThongTin(list.get(holder.getAdapterPosition()).getListTt());
                }
            });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtCode, txtName, txtTeacher;
        Button btnTrangThai;
        ImageView ivhinh, ivChitiet;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCode = itemView.findViewById(R.id.txtCode);
            txtName = itemView.findViewById(R.id.txtName);
            txtTeacher = itemView.findViewById(R.id.txtTeacher);
            btnTrangThai = itemView.findViewById(R.id.btnTrangThai);
            ivhinh = itemView.findViewById(R.id.ivhinh);
            ivChitiet = itemView.findViewById(R.id.ivChitiet);
        }
    }
    private void ShowThongTin(ArrayList<ThongTin> list){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_thongtin, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
        ListView lvThongTin = view.findViewById(R.id.lvThongTin);

        ArrayList<HashMap<String, Object>> listtt = new ArrayList<>();
        for (ThongTin thongTin : list){
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("date", "Ngày học: "  + thongTin.getDate());
            hs.put("address","Địa điểm: "  + thongTin.getAddress());
            listtt.add(hs);
        }
        SimpleAdapter adapter = new SimpleAdapter(
                context,
                listtt,
                android.R.layout.simple_list_item_2,
                new String[]{"date", "address"},
                new int[]{android.R.id.text1, android.R.id.text2}
        );
        lvThongTin.setAdapter(adapter);
    }
}
