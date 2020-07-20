package com.dimowner.audiorecorder;

import com.dimowner.audiorecorder.app.model.FileUploadRequest;
import com.dimowner.audiorecorder.app.model.HttpResponse;
import com.dimowner.audiorecorder.app.model.LoginResponse;
import com.dimowner.audiorecorder.app.model.User;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Api {



    //this is our multipart request
    //we have two parameters on is name and other one is description

    /*@Multipart
    @POST("files")
    @Headers({"Keep-Alive: timeout=600"})
    Call<HttpResponse> uploadFile(@Header("Authorization") String token, @Part MultipartBody.Part file, @Part("file") RequestBody name, @Part("patient_id") String patient_id);
*/

    @POST("files")
    @Headers({"Keep-Alive: timeout=600"})
    Call<HttpResponse> uploadFile(@Header("Authorization") String token, @Body FileUploadRequest fileUploadRequest);

    @Headers({"Content-Type:application/json","Accept:*/*"})
    @POST("api/v1/auth")
    Call<LoginResponse> login(@Body User user);
}