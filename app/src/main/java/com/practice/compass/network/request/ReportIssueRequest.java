package com.practice.compass.network.request;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;
import com.practice.compass.network.CommonRetrofit;

import java.io.File;

import retrofit.mime.TypedFile;


public class ReportIssueRequest extends RetrofitSpiceRequest<ReportIssueResponse,CommonRetrofit> {

        private File imageFile;
        private String issueDesc,email,latitude,longitude;

        public  ReportIssueRequest(File imageFile,String issueDesc,String email,String latitude,String longitude){

            super(ReportIssueResponse.class,CommonRetrofit.class);
            this.imageFile = imageFile;
            this.issueDesc = issueDesc;
            this.email = email;
            this.latitude = latitude;
            this.longitude = longitude;
   }

    @Override
    public ReportIssueResponse loadDataFromNetwork() throws Exception {
        return getService().makeReportIssueCall(new TypedFile("image/jpeg",imageFile),issueDesc,email,latitude,longitude);

    }
}
