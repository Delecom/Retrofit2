package com.example.RetrofitUIdesign.Activities;



import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.RetrofitUIdesign.R;
import com.example.RetrofitUIdesign.databinding.ActivityForgetPasswordBinding;

public class ForgetPassword extends AppCompatActivity {
CardView btn;
private ActivityForgetPasswordBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        btn = findViewById(R.id.btn_sentOtp);
        btn.setOnClickListener(view -> {
            Intent i = new Intent(ForgetPassword.this,OTPActivity.class);
            startActivity(i);
        });

    }
}