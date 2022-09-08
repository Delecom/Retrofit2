package com.example.RetrofitUIdesign.NavFragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.RetrofitUIdesign.ModelResponse.FetchUserResponse;
import com.example.RetrofitUIdesign.ModelResponse.User;
import com.example.RetrofitUIdesign.R;
import com.example.RetrofitUIdesign.RetrofitClient;
import com.example.RetrofitUIdesign.UserAdapter;
//import com.example.RetrofitUIdesign.UserAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserFragment extends Fragment {

    RecyclerView recyclerView;
    List<User> userList;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        recyclerView=view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Call<FetchUserResponse> call= RetrofitClient.getInstance().getApi().fetchAllUsers();

        call.enqueue(new Callback<FetchUserResponse>() {
            @Override
            public void onResponse(Call<FetchUserResponse> call, Response<FetchUserResponse> response) {

                if(response.isSuccessful()){
                    Log.d("user",response.body().getUserList().toString());
                    userList=response.body().getUserList();
                    UserAdapter adapter= new UserAdapter(getActivity(),userList);
                    recyclerView.setAdapter(adapter);

                }


            }

            @Override
            public void onFailure(Call<FetchUserResponse> call, Throwable t) {

               Log.d("user","fatchuserfailure");
            }
        });
    }
}