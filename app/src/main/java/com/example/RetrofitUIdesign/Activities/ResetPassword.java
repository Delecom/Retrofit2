package com.example.RetrofitUIdesign.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.RetrofitUIdesign.R;

public class ResetPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);
        getSupportActionBar().hide();
    }
}