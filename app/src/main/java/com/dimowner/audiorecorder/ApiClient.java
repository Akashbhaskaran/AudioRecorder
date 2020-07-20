package com.dimowner.audiorecorder;

import com.dimowner.audiorecorder.util.RestrictedSocketFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.dimowner.audiorecorder.Constants.BASE_URL;

public class ApiClient {


    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
         HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
         interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
         OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor)
                 .connectTimeout(200, TimeUnit.SECONDS)
                 .readTimeout(200, TimeUnit.SECONDS)
                 .writeTimeout(200, TimeUnit.SECONDS)
        .build();

        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
