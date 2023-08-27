package com.example.androidapp.activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.androidapp.model.CartDetail;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.androidapp.databinding.ActivityCartConfirmationBinding;

import com.example.androidapp.R;

import java.util.ArrayList;

public class CartConfirmationActivity extends AppCompatActivity {

    Button mbtnBackToHomepagr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_confirmation);

        Intent intent = getIntent();
        String recipientInfo = intent.getStringExtra("recipientInfo");
        String arrival = intent.getStringExtra("timeArrival");
        String order = intent.getStringExtra("timeOrder");
        String totalItem = intent.getStringExtra("productsTotal");
        Double deliveryValue = intent.getDoubleExtra("deliveryValue", 0);
        Double serviceValue = intent.getDoubleExtra("serviceValue", 0);
        Double estimatedValue = intent.getDoubleExtra("estimatedValue", 0);
        Double priceTotal = intent.getDoubleExtra("priceTotal", 0);
        ArrayList<CartDetail> receivedList = (ArrayList<CartDetail>) getIntent().getSerializableExtra("dataList");

        mbtnBackToHomepagr = findViewById(R.id.btnBackToHomepage);

        mbtnBackToHomepagr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(CartConfirmationActivity.this, ProductPage.class);
                startActivity(intent1);
            }
        });

    }



    // Các phương thức xử lý tabbar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cartconfirm_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.back) {
            Intent intent = new Intent();
            intent.setClass(CartConfirmationActivity.this,
                    NullPageActivity.class);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.exit) {
            finishAffinity();   //Tắt hoàn toàn ứng dụng
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setClass(CartConfirmationActivity.this,
                NullPageActivity.class);
        startActivity(intent);
    }
}