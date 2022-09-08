package com.example.RetrofitUIdesign.NavFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.RetrofitUIdesign.ModelResponse.LoginResponse;
import com.example.RetrofitUIdesign.R;
import com.example.RetrofitUIdesign.RetrofitClient;
import com.example.RetrofitUIdesign.SharedPrefManager;
import com.example.RetrofitUIdesign.databinding.FragmentDashboardBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment implements View.OnClickListener {
    SharedPreferences sharedPreferences;
    SharedPrefManager sharedPrefManager;
    private FragmentDashboardBinding binding;

    private static String SHARED_PREF_NAME = "the hamza code";

    EditText email_up, userName_up, city;
    Button Update;
    TextView phoneNumber;
    String phonenum;
    int userId;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentDashboardBinding.inflate(inflater,container,false);
        return binding.getRoot();


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);


        phoneNumber = binding.updatePhoneNum;
        email_up = binding.updateEmail;
        userName_up = binding.updateUserName;
        Update = binding.btnUpdate;
        city = binding.updateCity;

        phoneNumber.setText(sharedPreferences.getString("phonenum", "0"));

        sharedPrefManager = new SharedPrefManager(getActivity());
        phonenum = sharedPrefManager.getUser().getPhonenum();
        userId = sharedPrefManager.getUser().getId();
        Update.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_update:
                UserUpdateAccount();
                break;

        }
    }

    private void UserUpdateAccount() {
        String upEmail = email_up.getText().toString().trim();
        String upCity = city.getText().toString().trim();
        String upUserName = userName_up.getText().toString().trim();
        if (upEmail.isEmpty()) {
            email_up.requestFocus();
            email_up.setError("Please enter your email");
            return;
        }
        if (upCity.isEmpty()) {
            city.requestFocus();
            city.setError("Please enter your City Name");
            return;
        }
        if (upUserName.isEmpty()) {
            userName_up.requestFocus();
            userName_up.setError("Please enter your User Name");
            return;
        }
        Call<LoginResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .updateUser(phonenum,userId, upEmail,upUserName,upCity);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse update = response.body();

                if (response.isSuccessful()){
                    if (update.getError().equals("000")){


                        Toast.makeText(getActivity(),update.getMessage(), Toast.LENGTH_SHORT).show();


                    }else{
                        Toast.makeText(getActivity(),update.getMessage(), Toast.LENGTH_SHORT).show();


                    }
                }else {
                    Toast.makeText(getActivity(),"failed", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });

    }
}