package com.practice.compass.network.request;


import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;
import com.practice.compass.network.CommonRetrofit;

public class SignUpRequest extends RetrofitSpiceRequest<SignUpResponse,CommonRetrofit>{

    private String name,email,password,mobileNumber;

    public SignUpRequest(String email,String password,String name,String mobileNumber){
        super(SignUpResponse.class,CommonRetrofit.class);
        this.email = email;
        this.password = password;
        this.name = name;
        this.mobileNumber = mobileNumber;
    }


    @Override
    public SignUpResponse loadDataFromNetwork() throws Exception {
        return getService().makeSignUpCall(email,password,name,mobileNumber);
    }
}
