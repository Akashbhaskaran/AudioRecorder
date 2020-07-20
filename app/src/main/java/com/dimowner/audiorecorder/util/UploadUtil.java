package com.dimowner.audiorecorder.util;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.dimowner.audiorecorder.Api;
import com.dimowner.audiorecorder.ApiClient;
import com.dimowner.audiorecorder.Constants;
import com.dimowner.audiorecorder.R;
import com.dimowner.audiorecorder.app.model.FileUploadRequest;
import com.dimowner.audiorecorder.app.model.HttpResponse;
import com.dimowner.audiorecorder.app.records.RecordsActivity;
import com.dimowner.audiorecorder.app.records.RecordsPresenter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;


import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.common.collect.Lists;


public class UploadUtil {

    public static String uploadFile(String sourceFileUri,String patient_id,final Boolean isLast,String dept) {

        File file = new File(sourceFileUri);
        Log.e("http","inside upload function");
        //creating request body for file
        // Parsing any Media type file
//        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
//        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
//        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        FileUploadRequest fileUploadRequest = new FileUploadRequest(new File(sourceFileUri).getName(),patient_id,dept);

        //creating retrofit object
        Retrofit retrofit  = ApiClient.getClient();
        //creating our api
        Api api = retrofit.create(Api.class);
        //creating a call and calling the upload image method
       // Call<HttpResponse> call = api.uploadFile("JWT " + Constants.access_token, fileToUpload, filename, patient_id);
        Call<HttpResponse> call = api.uploadFile("JWT " + Constants.access_token, fileUploadRequest);
        boolean is_success;
        //finally performing the call
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                if (!response.body().isError()) {

                    if (isLast){
                        RecordsPresenter.hideProgress(true);
                    }
                }
            }
            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                Log.e("http","upload failed");
                if (isLast){
                    RecordsPresenter.hideProgress(false);
                }
            }
        });
        return "success";
    }

    public static String uploadFilegcp(final String sourceFileUri, final String patient_id, Context ctx, final Boolean isLast, final String dept) throws IOException {
        //File cred = new File(Environment.getExternalStorageDirectory()+"/google.json");

        InputStream input = ctx.getAssets().open("google.json");
        final File file = new File(ctx.getCacheDir()+"google.json");;
        try {

            try (OutputStream output = new FileOutputStream(file)) {
                byte[] buffer = new byte[4 * 1024]; // or other buffer size
                int read;

                while ((read = input.read(buffer)) != -1) {
                    output.write(buffer, 0, read);
                }

                output.flush();
            }
        } finally {
            input.close();
        }

        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(file))
                .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));

            final Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
            BlobId blobId = BlobId.of("ascribe-ananth", Constants.username + "/"+new File(sourceFileUri).getName() );
            final BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    //Your code goes here
                    RecordsPresenter.showProgress();
                    byte[] bytesArray = new byte[(int) new File(sourceFileUri).length()];
                    FileInputStream fis = new FileInputStream(new File(sourceFileUri));
                    fis.read(bytesArray); //read file into bytes[]
                    fis.close();
                    storage.create(blobInfo, bytesArray);
                    file.delete();
                    uploadFile(sourceFileUri,patient_id,isLast,dept);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();


            return "";
    }
}


