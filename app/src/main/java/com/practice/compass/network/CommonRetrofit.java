package com.practice.compass.network;

import com.practice.compass.network.request.LogInResponse;
import com.practice.compass.network.request.ReportIssueResponse;
import com.practice.compass.network.request.SeeIssueRequest.SeeIssueResponse;
import com.practice.compass.network.request.SignUpResponse;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;

public interface CommonRetrofit {

    @FormUrlEncoded
    @POST("/user/authenticate")
    LogInResponse makeLogInCall(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("/user/new")
    SignUpResponse makeSignUpCall(
            @Field("email") String email,
            @Field("password") String name,
            @Field("name") String password,
            @Field("mobileNumber") String mobileNumber);

    @Multipart
    @POST("/issue/new")
    ReportIssueResponse makeReportIssueCall(
            @Part("picture") TypedFile file,
            @Part("postDesc") String issue,
            @Part("email") String email,
            @Part("latitude") String latitude,
            @Part("longitude") String longitude);

    @FormUrlEncoded
    @POST("/issue/nearbyissues")
    SeeIssueResponse.AsList makeSeeIssueCall(
            @Field("latitude") String latitude,
            @Field("longitude") String longitude);
}
