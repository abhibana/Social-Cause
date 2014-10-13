package com.practice.compass.network.request.SeeIssueRequest;


import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;
import com.practice.compass.network.CommonRetrofit;

public class SeeIssueRequest extends RetrofitSpiceRequest<SeeIssueResponse.AsList,CommonRetrofit> {

    private String latitude,longitude;

    public SeeIssueRequest(String latitude,String longitude){

        super(SeeIssueResponse.AsList.class,CommonRetrofit.class);
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public SeeIssueResponse.AsList loadDataFromNetwork() throws Exception {
        return getService().makeSeeIssueCall(latitude,longitude);
    }
}
