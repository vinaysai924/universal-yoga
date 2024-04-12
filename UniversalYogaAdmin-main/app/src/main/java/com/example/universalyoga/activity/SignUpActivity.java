package com.example.universalyoga.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.universalyoga.databinding.ActivitySignupBinding;
import com.example.universalyoga.db.DBHandler;
import com.example.universalyoga.model.SignupDataModel;
import com.example.universalyoga.model.UserDataModel;
import com.example.universalyoga.utils.ApiClient;
import com.example.universalyoga.utils.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignupBinding binding;
    private DBHandler dbHandler;
    ApiInterface apiInterface;
    private UserDataModel user;
    private final AppCompatActivity activity = SignUpActivity.this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        init();
    }

    public void init(){
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        dbHandler = new DBHandler(this);

        binding.signUpClickTv.setOnClickListener(view -> {
            String usernameStr = String.valueOf(binding.usernameEt.getText().toString().trim());
            String emailStr = String.valueOf(binding.emailEt.getText().toString().trim());
            String passwordStr = String.valueOf(binding.passwordEt.getText().toString().trim());
            String confirmPassStr = String.valueOf(binding.confirmpasswordEt.getText().toString().trim());
//            boolean b =dbHandler.insetUserData(emailStr,passwordStr);
            if (usernameStr.isEmpty()){
                Toast.makeText(SignUpActivity.this, "Please enter your User Name", Toast.LENGTH_SHORT).show();
            } else if (emailStr.isEmpty()){
                Toast.makeText(SignUpActivity.this, "Please enter your email Id", Toast.LENGTH_SHORT).show();
            }else if (passwordStr.isEmpty()){
                Toast.makeText(SignUpActivity.this, "Please enter your Password", Toast.LENGTH_SHORT).show();
            }else if (confirmPassStr.isEmpty()){
                Toast.makeText(SignUpActivity.this, "Please enter your Confirm Password", Toast.LENGTH_SHORT).show();
            }else if (passwordStr == confirmPassStr){
                Toast.makeText(SignUpActivity.this, "Confirm Password is incorrect", Toast.LENGTH_SHORT).show();
            }
//            sql save data
//            else if (b){
//                Toast.makeText(SignUpActivity.this, "Data inserted", Toast.LENGTH_SHORT).show();
//                Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
//                startActivity(i);
//            }
            else {
                signupRequest();
            }
        });

        binding.loginTv.setOnClickListener(view -> {
            Intent in = new Intent(SignUpActivity.this,LoginActivity.class);
            startActivity(in);
        });

    }
    public void signupRequest(){
        String emailStr ,passwordStr, confirmPassword, nameStr;
        nameStr = binding.passwordEt.getText().toString().trim();
        emailStr = binding.emailEt.getText().toString().trim();
        passwordStr = binding.passwordEt.getText().toString().trim();
        SignupDataModel signupDataModel = new SignupDataModel();
        signupDataModel.setAdminName(nameStr);
        signupDataModel.setAdminEmail(emailStr);
        signupDataModel.setPassword(passwordStr);
        Call<SignupDataModel> call = apiInterface.signUpAdmin(signupDataModel);
        call.enqueue(new Callback<SignupDataModel>() {
            @Override
            public void onResponse(Call<SignupDataModel> call, Response<SignupDataModel> response) {
                if (response.isSuccessful()){
                    Toast.makeText(SignUpActivity.this, "Signup Successfully", Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(SignUpActivity.this,LoginActivity.class);
                    startActivity(in);
                }else {
                    Toast.makeText(SignUpActivity.this, response.message().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SignupDataModel> call, Throwable t) {
            }
        });
    }
}
