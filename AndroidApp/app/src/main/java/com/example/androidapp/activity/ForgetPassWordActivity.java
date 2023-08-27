package com.example.androidapp.activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.androidapp.api.ApiForgetPassword;
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

import com.example.androidapp.databinding.ActivityForgetPassWordBinding;

import com.example.androidapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPassWordActivity extends AppCompatActivity {

    Button btnSendMail;
    EditText edtMail;
    TextView txtBack1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass_word);
        AnhXa();
        btnSendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiForgetPassword.apiService.forgotPass(edtMail.getText().toString()).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                       int status = response.code();
                        if(status == 200) {
                            Toast.makeText(getApplicationContext(), "CALL API SUCCESS", Toast.LENGTH_SHORT).show();
                            Intent myIntent = new Intent(ForgetPassWordActivity.this, ChangePassWordActivity.class);
                            String tokens = response.body();
                            myIntent.putExtra("token", tokens);
                            startActivity(myIntent);
                        }
                        else if(status == 500){
                            Toast.makeText(getApplicationContext(), "EMAIL ĐĂNG KÍ KHÔNG ĐÚNG", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"CALL API ERROR",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        txtBack1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentLogin();
            }
        });
    }
    private void AnhXa(){
        btnSendMail = findViewById(R.id.btnSendMail);
        edtMail = findViewById(R.id.edtMail);
        txtBack1 = findViewById(R.id.txtBack1);
    }

    private void IntentLogin(){
        Intent intent = new Intent(ForgetPassWordActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}