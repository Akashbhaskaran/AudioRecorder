package com.dimowner.audiorecorder.app.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Toast;

import com.dimowner.audiorecorder.R;
import com.dimowner.audiorecorder.data.database.DataSource;
import com.dimowner.audiorecorder.util.FileUtil;

import java.io.FileNotFoundException;


public class SplashActivity extends Activity {

    private static int SPLASH_SCREEN_TIME_OUT=2000;
    //After completion of 2000 ms, the next activity will get started.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //This method is used so that your splash activity
        //can cover the entire screen.

        //this will bind your MainActivity.class file with activity_main.

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(getApplicationContext(),
                        LoginActivity.class);
                //Intent is used to switch from one activity to another.

                startActivity(i);
                //invoke the SecondActivity.

                finish();
                //the current activity will get finished.
            }
        }, SPLASH_SCREEN_TIME_OUT);
    }
}

