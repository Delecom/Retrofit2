package com.example.RetrofitUIdesign.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.RetrofitUIdesign.ModelResponse.LoginResponse;
import com.example.RetrofitUIdesign.R;
import com.example.RetrofitUIdesign.RetrofitClient;
import com.example.RetrofitUIdesign.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignIn extends AppCompatActivity implements View.OnClickListener {
    TextView forget, signLink;
    CardView signIn;
    EditText phoneNum, password;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        sharedPrefManager=new SharedPrefManager(getApplicationContext());

        phoneNum= findViewById(R.id.signIn_phoneNum);
        password = findViewById(R.id.signIn_password);
        signLink = findViewById(R.id.signup_link);
        signIn = findViewById(R.id.btn_signIn);
        forget = findViewById(R.id.forget_password);

        signLink.setOnClickListener(this);
        signIn.setOnClickListener(this);
        forget.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_signIn:
                userLogin();
                break;
            case R.id.signup_link:
                switchSignup();
                break;
            case R.id.forget_password:
                startActivity(new Intent(SignIn.this, ForgetPassword.class));
                break;

        }

    }

    private void switchSignup() {

        Intent i = new Intent(SignIn.this, SignUp.class);
        startActivity(i);
    }

    private void userLogin() {

        String userPhoneNum = phoneNum.getText().toString();
        String userPassword = password.getText().toString();


        if (userPhoneNum.isEmpty()) {
            phoneNum.requestFocus();
            phoneNum.setError("Please enter your Phone Number");
            return;
        }


        if (userPassword.isEmpty()) {
            password.requestFocus();
            password.setError("Please enter your Password");
            return;
        }
        if (userPassword.length() < 8) {
            password.requestFocus();
            password.setError("please enter 8 digit password");
            return;
        }

        Call<LoginResponse> call = RetrofitClient.getInstance().getApi().login(userPhoneNum, userPassword);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                if (response.isSuccessful()) {

                    if (loginResponse.getError().equals("000")){
                        sharedPrefManager.saveUser(response.body().getUser());
                        Intent i = new Intent(SignIn.this, HomeActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        finish();
                        Toast.makeText(SignIn.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(SignIn.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(SignIn.this, SignUp.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        finish();
                    }




                }else{
                    Toast.makeText(SignIn.this, "failed", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                Toast.makeText(SignIn.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });





    }


    @Override
    protected void onStart() {
        super.onStart();

        if(sharedPrefManager.isLoggedIn()){
            Intent intent=new Intent(SignIn.this,HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
