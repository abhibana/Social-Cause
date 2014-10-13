package com.practice.compass.network;

import com.google.gson.GsonBuilder;
import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class CommonSpiceService extends RetrofitGsonSpiceService{

    private final static String BASE_URL = "http://54.191.15.170:9000";

    public void onCreate(){
        super.onCreate();
        addRetrofitInterface(CommonRetrofit.class);
    }

    @Override
    protected RestAdapter.Builder createRestAdapterBuilder() {
        return new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setConverter(new GsonConverter(new GsonBuilder()
                        .excludeFieldsWithoutExposeAnnotation().create()))
                .setLogLevel(RestAdapter.LogLevel.FULL);

    }

    @Override
    protected String getServerUrl() {
        return BASE_URL;
    }

    public int getThreadCount(){
        return 15;
    }
}
