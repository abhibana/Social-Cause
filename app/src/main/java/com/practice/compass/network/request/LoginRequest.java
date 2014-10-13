package com.practice.compass.network.request;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;
import com.practice.compass.network.CommonRetrofit;

public class LoginRequest extends RetrofitSpiceRequest<LogInResponse,CommonRetrofit>{

    private String email,password;

    public LoginRequest(String email,String password){
        super(LogInResponse.class,CommonRetrofit.class);
        this.email = email;
        this.password = password;
    }


    @Override
    public LogInResponse loadDataFromNetwork() throws Exception {
       return getService().makeLogInCall(email, password);
    }
}
