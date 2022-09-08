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
import android.widget.TextView;

import com.example.RetrofitUIdesign.R;


public class ProfileFragment extends Fragment {
    private SharedPreferences sharedPreferences;
    private static String SHARED_PREF_NAME="the hamza code";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate
                (R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences=getActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);


        TextView textView = view.findViewById(R.id.phoneNumber_profile);
        TextView textView1 = view.findViewById(R.id.first_Name_profile);
        TextView textView2 = view.findViewById(R.id.last_Name_profile);
        TextView textView3 = view.findViewById(R.id.profile_email);

        textView.setText(sharedPreferences.getString("phonenum","0"));
        textView1.setText(sharedPreferences.getString("firstName","0"));
        textView2.setText(sharedPreferences.getString("lastName","0"));
        textView3.setText(sharedPreferences.getString("email","0"));


    }
}