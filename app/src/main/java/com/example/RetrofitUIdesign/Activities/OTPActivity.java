package com.example.RetrofitUIdesign.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.RetrofitUIdesign.R;

public class OTPActivity extends AppCompatActivity implements TextWatcher {
EditText ed1,ed2,ed3,ed4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity);
        getSupportActionBar().hide();
        ed1 = findViewById(R.id.ed1);
        ed2 = findViewById(R.id.ed2);
         ed3= findViewById(R.id.ed3);
         ed4= findViewById(R.id.ed4);
         ed1.addTextChangedListener(this);
         ed2.addTextChangedListener(this);
         ed3.addTextChangedListener(this);
         ed4.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editable.length() == 1)
        {
            if (ed1.length() == 1) {
                ed2 .requestFocus();
            }
            if (ed2.length() == 1){
                ed3.requestFocus();
            }
            if (ed3.length() == 1){
                ed4.requestFocus();
            }
            if (!ed4.getText().toString().equalsIgnoreCase("")) {
                Intent i = new Intent(OTPActivity.this, ResetPassword.class);
                startActivity(i);

            }
        }
        else  if (editable .length() == 0)
        {
            if (ed4.length() == 0){
                ed3.requestFocus();
            }
            if (ed3.length() == 0){
                ed2.requestFocus();
            }
            if (ed2.length() == 0){
                ed1.requestFocus();
            }
        }

    }
}