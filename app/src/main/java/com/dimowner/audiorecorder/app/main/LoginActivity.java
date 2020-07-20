package com.dimowner.audiorecorder.app.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dimowner.audiorecorder.Api;
import com.dimowner.audiorecorder.ApiClient;
import com.dimowner.audiorecorder.Constants;
import com.dimowner.audiorecorder.R;
import com.dimowner.audiorecorder.app.model.LoginResponse;
import com.dimowner.audiorecorder.app.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends Activity {

    Button login_btn;
    Api apiInterface;
    EditText email,password;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText)findViewById(R.id.input_email);
        password = (EditText)findViewById(R.id.input_password);
        login_btn = (Button)findViewById(R.id.login_btn);
        progressBar = (ProgressBar)findViewById(R.id.login_progress_bar);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                if(!email.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {
                    login(email.getText().toString(),password.getText().toString());
                }
                else{
                    Toast.makeText(getApplicationContext(),"Invalid Credentails",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void login(final String email, String password){


        progressBar.setVisibility(View.VISIBLE);


        User user = new User(email,password);
        apiInterface = ApiClient.getClient().create(Api.class);


        Call<LoginResponse> call = apiInterface.login(user);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                progressBar.setVisibility(View.GONE);
                if(response.code() == 200 && response.body().getAccess_token()!=null) {
                    Constants.access_token = response.body().getAccess_token();
                    Constants.username = email;
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Invalid Credentials",Toast.LENGTH_LONG).show();
                    call.cancel();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_HOME);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}
