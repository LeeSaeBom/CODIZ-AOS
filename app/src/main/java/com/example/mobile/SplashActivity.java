package com.example.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.os.*;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
        @Override
        protected  void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);

            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 700);
        }
}
