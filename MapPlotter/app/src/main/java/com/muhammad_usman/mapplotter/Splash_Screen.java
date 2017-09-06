package com.muhammad_usman.mapplotter;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Splash_Screen extends AppCompatActivity {
    String body;
    ProgressBar mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);
        mProgress = (ProgressBar) findViewById(R.id.progressBar);
        mProgress.getProgressDrawable().setColorFilter(
                Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);

        // Start lengthy operation in a background thread
        new Thread(new Runnable() {
            public void run() {
                doWork();
                startApp();
                finish();
            }
        }).start();
    }

    private void doWork() {
        for (int progress=0; progress<100; progress+=1) {
            try {
                Thread.sleep(30);
                mProgress.setProgress(progress);
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }

    private void startApp() {


//        if () {
//            Intent intent = new Intent(Splash.this, MainActivity.class);
//            startActivity(intent);
//        } else {
            Intent intent = new Intent(Splash_Screen.this, MainActivity.class);
            startActivity(intent);
//        }

    }


}

