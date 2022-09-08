package com.example.RetrofitUIdesign.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.RetrofitUIdesign.ModelResponse.DeleteResponse;
import com.example.RetrofitUIdesign.NavFragment.DashboardFragment;
import com.example.RetrofitUIdesign.NavFragment.ProfileFragment;
import com.example.RetrofitUIdesign.NavFragment.UserFragment;
import com.example.RetrofitUIdesign.R;
import com.example.RetrofitUIdesign.RetrofitClient;
import com.example.RetrofitUIdesign.SharedPrefManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    BottomNavigationView bottomNavigationView;
    SharedPrefManager sharedPrefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        loadFragment(new DashboardFragment());
        sharedPrefManager=new SharedPrefManager(getApplicationContext());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.dashboard:
                fragment = new DashboardFragment();
                break;
            case R.id.user:
                fragment = new UserFragment();
                break;
            case R.id.profile:
                fragment = new ProfileFragment();
                break;
        }if (fragment != null){
            loadFragment(fragment);

        }
        return true;
    }

    void   loadFragment(Fragment fragment) {

        getSupportFragmentManager().beginTransaction().replace(R.id.constraint,fragment).commit();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.logged_out,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()){
            case R.id.logout:
                logoutUser();
                break;

            case R.id.deleteAccount:
                deleteAccount();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteAccount() {


        Call<DeleteResponse> call  = RetrofitClient
                .getInstance()
                .getApi()
                .deleteUser(sharedPrefManager.getUser().getId());
        call.enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                if (response.isSuccessful()){
                    DeleteResponse deleteResponse = response.body();

                    if (deleteResponse.getError().equals("200")){

                        Toast.makeText(HomeActivity.this, deleteResponse.getMessage() , Toast.LENGTH_SHORT).show();
                        logoutUser();
                    }else{

                        Toast.makeText(HomeActivity.this, deleteResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }else{

                    Toast.makeText(HomeActivity.this, "failed", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable t) {
                Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void logoutUser() {

        sharedPrefManager.logout();
        Intent intent=new Intent(HomeActivity.this,SignIn.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        Toast.makeText(this, "You have been logged out", Toast.LENGTH_SHORT).show();


    }
}