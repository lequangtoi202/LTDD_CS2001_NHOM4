package com.example.androidapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.androidapp.api.ApiResetPassWord;
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

import com.example.androidapp.databinding.ActivityChangePassWordBinding;

import com.example.androidapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassWordActivity extends AppCompatActivity {

    Button btnConfirm;
    EditText edtNewPass, edtNewPass2;
    String token;
    TextView txtBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass_word);
        AnhXa();
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });
        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentLogin();
            }
        });
    }

    private void AnhXa() {
        btnConfirm = findViewById(R.id.btnConfirm);
        edtNewPass = findViewById(R.id.newPass);
        edtNewPass2 = findViewById(R.id.newPass2);
        txtBack = findViewById(R.id.txtBack2);
    }

    private void resetPassword() {

        String tokens = getIntent().getStringExtra("token");
        String[] parts = tokens.split("=");
        token = parts[1];
        if (edtNewPass.getText().toString().equals(edtNewPass2.getText().toString())) {
            ApiResetPassWord.apiService.resetPass(token, edtNewPass.getText().toString())
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Toast.makeText(getApplicationContext(), "RESET PASSWORD SUCCESS", Toast.LENGTH_SHORT).show();
                        IntentLogin();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "RESET PASSWORD FAILED", Toast.LENGTH_SHORT).show();
                        IntentLogin();
                    }
                });
        } else {
            Toast.makeText(getApplicationContext(), "PASSWORD INCORRECT", Toast.LENGTH_SHORT).show();
        }
    }

    private void IntentLogin() {
        Intent myIntent = new Intent(ChangePassWordActivity.this, LoginActivity.class);
        startActivity(myIntent);
    }
}