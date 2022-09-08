package com.example.RetrofitUIdesign;

import com.example.RetrofitUIdesign.ModelResponse.DeleteResponse;
import com.example.RetrofitUIdesign.ModelResponse.FetchUserResponse;
import com.example.RetrofitUIdesign.ModelResponse.LoginResponse;
import com.example.RetrofitUIdesign.ModelResponse.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;

import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("register.php")
    Call<RegisterResponse> register(
            @Field("phonenum") String phonenum,
            @Field("password") String password,
            @Field("confirmPassword") String confirmPassword,
            @Field("firstName") String firstName,
            @Field("lastName") String lastName

    );
    @GET("fatchuser.php")
    Call<FetchUserResponse> fetchAllUsers();



    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> login(
            @Field("phonenum") String phonenum,
            @Field("password") String password

    );
    @FormUrlEncoded
    @POST("deleteacount.php")
    Call<DeleteResponse> deleteUser(
            @Field("id") int userId


    );
    @FormUrlEncoded
    @POST("personalProfile.php")
    Call<LoginResponse> updateUser(

            @Field("phonenum") String phonenum,
            @Field("id") int userId,
            @Field("email") String email,
            @Field("userName") String userName,
            @Field("city") String City



    );
}
