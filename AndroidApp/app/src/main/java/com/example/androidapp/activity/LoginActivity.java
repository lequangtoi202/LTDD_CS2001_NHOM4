package com.example.androidapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.androidapp.api.ApiLogin;
import com.example.androidapp.model.Login;
import com.example.androidapp.model.LoginResponse;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.androidapp.databinding.ActivityLoginBinding;

import com.example.androidapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText edtUsername;
    EditText edtPassword;
    Button btnLogin;
    TextView txtBack;
    TextView txtSingUp, txtForgotPass;
    String username;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Mapping();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendLogin();
            }
        });
        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentMain();
            }
        });
        txtSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentSignUp();
            }
        });
        txtForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentForgotPass();
            }
        });
    }

    private void sendLogin() {
        username = edtUsername.getText().toString();
        password = edtPassword.getText().toString();
        Login login = new Login(username, password);
        ApiLogin.apiService.login(login)
                .enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        int statusCode = response.code();
                        if (statusCode == 200) {
                            String token = response.body().getAccessToken();
                            Toast.makeText(getApplicationContext(), "LOGIN SUCCESS", Toast.LENGTH_LONG).show();
                            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("token", token);
                            editor.apply();
                            Intent intent = new Intent(LoginActivity.this, ProductPage.class);
                            intent.putExtra("token", token);
                            intent.putExtra("username", edtUsername.getText().toString());
                            intent.putExtra("password", edtPassword.getText().toString());
                            startActivity(intent);
                        }else {
                            Toast.makeText(getApplicationContext(), "Username or Password Invalid", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "LOGIN FAILED" + t.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });
    }


    private void IntentMain() {
        Intent intent = new Intent(LoginActivity.this, ProductPage.class);
        startActivity(intent);
    }

    private void IntentSignUp() {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    private void IntentForgotPass() {
        Intent intent = new Intent(LoginActivity.this, ForgetPassWordActivity.class);
        startActivity(intent);
    }

    private void Mapping() {
        edtPassword = findViewById(R.id.edtPassword);
        edtUsername = findViewById(R.id.edtUsername);
        btnLogin = findViewById(R.id.btnlogin);
        txtBack = findViewById(R.id.txtBack);
        txtSingUp = findViewById(R.id.txtSignup);
        txtForgotPass = findViewById(R.id.txtForgotPass);
    }
}