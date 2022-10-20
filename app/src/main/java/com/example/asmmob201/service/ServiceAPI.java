package com.example.asmmob201.service;




import com.example.asmmob201.ModelJson.data;


import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ServiceAPI {
    String BASE_Service = "https://apis.dinhnt.com/";
    @GET("edu.json")
    Observable<data> getData();

}
