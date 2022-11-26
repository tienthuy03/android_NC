package com.example.asmmob201;

import static com.example.asmmob201.service.ServiceAPI.BASE_Service;



import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.asmmob201.ModelJson.data;

import com.example.asmmob201.ModelJson.items;
import com.example.asmmob201.service.ServiceAPI;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewActivity extends AppCompatActivity {
    ListView lstTieuDe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        lstTieuDe = findViewById(R.id.listViewNews);
        demoCallAPI();
    }

    private void demoCallAPI() {
        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_Service)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);

        new CompositeDisposable().add(requestInterface.getData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        );
    }
    private void handleResponse(data dataedu) {
        //API trả về dữ liệu thành công, thực hiện việc lấy data
        Log.d("========", dataedu.getChannel().getGenerator());
        ArrayList<items> list = dataedu.getChannel().getItem();
        ArrayList<HashMap<String, Object>> listtt = new ArrayList<>();
        for (items data : list){
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("title", data.getTitle());
            listtt.add(hs);
        }
        SimpleAdapter adapter = new SimpleAdapter(
                NewActivity.this,
                listtt,
                android.R.layout.simple_list_item_1,
                new String[]{"title" },
                new int[]{android.R.id.text1}
        );
        lstTieuDe.setAdapter(adapter);
        lstTieuDe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(NewActivity.this, WebViewActivity.class);
                intent.putExtra("linkNews",list.get(i).getLink());
                startActivity(intent);
            }
        });
    }
    private void handleError(Throwable error) {
        String a = "";
        //khi gọi API KHÔNG THÀNH CÔNG thì thực hiện xử lý ở đây
    }
}

