package com.example.RetrofitUIdesign.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.RetrofitUIdesign.ModelResponse.RegisterResponse;
import com.example.RetrofitUIdesign.R;
import com.example.RetrofitUIdesign.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity  implements View.OnClickListener {
    TextView loginLink;
    CardView signup;
    EditText phonenum, confirmPassword, password,first_name,last_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        phonenum = findViewById(R.id.signup_phoneNum);
        confirmPassword = findViewById(R.id.confirm_password);
        password = findViewById(R.id.signup_password);
        loginLink = findViewById(R.id.login_link);
        signup = findViewById(R.id.btn_signup);
        first_name = findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);
        loginLink.setOnClickListener(this);
        signup.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_signup:
                registerUser();
                break;
            case R.id.login_link:
                switchLogin();
                break;
        }
    }

    private void registerUser() {

        String phoneNum = phonenum.getText().toString();
        String userConfirmPassword = confirmPassword.getText().toString();
        String userPassword = password.getText().toString();
        String userFirstName = first_name.getText().toString();
        String userLastName = last_name.getText().toString();



        if (phoneNum.isEmpty()) {
            phonenum.requestFocus();
            phonenum.setError("Please enter your name");
            return;
        }
        if (userFirstName.isEmpty()) {
            first_name.requestFocus();
            first_name.setError("Please enter your first name");
            return;
        }
        if (userLastName.isEmpty()) {
            last_name.requestFocus();
            last_name.setError("Please enter your last name");
            return;
        }

        if (!userConfirmPassword.isEmpty()) {
            if (userPassword.isEmpty()) {
                password.requestFocus();
                password.setError("Please enter your password");
                return;
            }

            if (userPassword.length() < 8) {
                password.requestFocus();
                password.setError("Please enter Eight digit password");
                return;
            }


            Call<RegisterResponse> call;
            call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .register(phoneNum, userPassword, userConfirmPassword,userFirstName,userLastName);
            call.enqueue(new Callback<RegisterResponse>() {
                @Override
                public void onResponse(@NonNull Call<RegisterResponse> call, @NonNull Response<RegisterResponse> response) {


                    RegisterResponse registerResponse = response.body();
                    if (response.isSuccessful()) {

                        Intent i = new Intent(SignUp.this, SignIn.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        finish();
                        Toast.makeText(SignUp.this, registerResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("signup", "signup--- success "+registerResponse.getMessage());


                    } else {
                        Toast.makeText(SignUp.this, registerResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("signup", "signup--- "+registerResponse.getMessage());

                    }
                }

                @Override
                public void onFailure(@NonNull Call<RegisterResponse> call, @NonNull Throwable t) {

                    Log.d("signup", "signup--- failed "+t.getMessage());

                    Toast.makeText(SignUp.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        } else {
            confirmPassword.requestFocus();
            confirmPassword.setError("Please enter your confirmPassword");
            return;
        }

    }

        private void switchLogin() {
            Intent i = new Intent(SignUp.this, SignIn.class);
            startActivity(i);
        }



    }

