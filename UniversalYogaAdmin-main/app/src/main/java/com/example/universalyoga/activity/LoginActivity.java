package com.example.universalyoga.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.universalyoga.databinding.ActivityLoginBinding;
import com.example.universalyoga.db.DBHandler;
import com.example.universalyoga.model.LoginData;
import com.example.universalyoga.model.LoginListData;
import com.example.universalyoga.model.LoginModel;
import com.example.universalyoga.utils.ApiClient;
import com.example.universalyoga.utils.ApiInterface;
import com.example.universalyoga.utils.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    private DBHandler dbHandler;
    ApiInterface apiInterface;
    private final AppCompatActivity activity = LoginActivity.this;

    SharedPreferences sharedpreferences;
    public static final String SHARED_PREFS = "shared_prefs";
    String email, password;
    public static final String EMAIL_KEY = "email_key";
    public static final String PASSWORD_KEY = "password_key";
    public static final String USERID = "userId_key";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        init();
        login();
    }

    public void init() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        login();
        binding.signUpTv.setOnClickListener(view -> {
            Intent in = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(in);
        });
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        email = sharedpreferences.getString("EMAIL_KEY", null);
        password = sharedpreferences.getString("PASSWORD_KEY", null);

        binding.passwordShowIv.setOnClickListener(view -> {
            binding.passwordHideIv.setVisibility(View.VISIBLE);
            binding.passwordEt.setTransformationMethod(new PasswordTransformationMethod());
        });

        binding.passwordHideIv.setOnClickListener(view -> {
            binding.passwordShowIv.setVisibility(View.VISIBLE);
            binding.passwordEt.setTransformationMethod(null);
        });
    }

    public void login() {
        dbHandler = new DBHandler(this);

        binding.loginClickTv.setOnClickListener(view -> {
            String emailStr = String.valueOf(binding.emailEt.getText().toString().trim());
            String passwordStr = String.valueOf(binding.passwordEt.getText().toString().trim());
            Cursor cursor = dbHandler.getDataLogin();

            if (emailStr.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please enter your email Id", Toast.LENGTH_SHORT).show();
            } else if (passwordStr.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please enter your Password", Toast.LENGTH_SHORT).show();
            }
//            sqlDatabse login
//            else if (loginCheck(cursor,emailStr,passwordStr)) {
//                SharedPreferences.Editor editor = sharedpreferences.edit();
//                editor.putString(EMAIL_KEY, binding.emailEt.getText().toString().trim());
//                editor.putString(PASSWORD_KEY, passwordStr);
//                sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
//                editor.apply();
//
//                Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
//                intent.putExtra("email",emailStr);
//                binding.emailEt.setText("");
//                binding.passwordEt.setText("");
//                startActivity(intent);
//            }else {
//                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
//                builder.setCancelable(true);
//                builder.setTitle("Wrong Credential");
//                builder.setMessage("Wrong Credential");
//                builder.show();
//            }
            else {
                loginRequest();
            }
            dbHandler.close();
        });
    }

    public static boolean loginCheck(Cursor cursor, String emailCheck, String passCheck) {
        while (cursor.moveToNext()) {
            if (cursor.getString(0).equals(emailCheck)) {
                if (cursor.getString(2).equals(passCheck)) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public void loginRequest() {
        String emailStr, passwordStr;
        emailStr = binding.emailEt.getText().toString().trim();
        passwordStr = binding.passwordEt.getText().toString().trim();

        JSONObject jsonObject = new JSONObject();
        try {
            // Add key-value pairs to the JSON object
            jsonObject.put("admin_Email", emailStr);
            jsonObject.put("password", passwordStr);

            // Convert the JSON object to RequestBody
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

            Call<LoginModel> call = apiInterface.loginAdmin(requestBody);
            call.enqueue(new Callback<LoginModel>() {
                @Override
                public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                    if (response.isSuccessful()) {
                        LoginData loginListData = response.body().getData();

                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(EMAIL_KEY, emailStr);
                        editor.putString(PASSWORD_KEY, passwordStr);
                        editor.putString(USERID, loginListData.getId());
                        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
                        editor.apply();
                        SharedPreferences sharedPreferencess = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editors = sharedPreferencess.edit();
                        editors.putString("USERID", loginListData.getId());
                        Intent in = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(in);
                    } else {
                        Toast.makeText(LoginActivity.this, response.message().toString(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginModel> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("onFailureLogin", t.getMessage().toString() + "notWork");
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
