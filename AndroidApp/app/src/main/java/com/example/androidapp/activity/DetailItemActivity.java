package com.example.androidapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.androidapp.R;
import com.example.androidapp.api.ApiCartService;
import com.example.androidapp.api.ApiFlowerService;
import com.example.androidapp.model.Account;
import com.example.androidapp.model.Cart;
import com.example.androidapp.model.Flower;
import com.nex3z.notificationbadge.NotificationBadge;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailItemActivity extends AppCompatActivity {
    TextView txtName, txtPrice, txtDescription, txtRate, txtSaleAmount;
    ImageView img, imgRate1, imgRate2, imgRate3, imgRate4, imgRate5, imgCart;
    Toolbar toolbar;
    Button btnAddToCartDetail;
    NotificationBadge notificationBadge;
    Cart cart;
    int selectedQuantity = 0;
    Flower flower;
    Account account;
    long id;
    int idAccount;
    String token;
    double score;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_item);

        Mapping();
        ActionToolBar();
        DataTransfer();

        btnAddToCartDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddToCartButtonClicked();
            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getIntExtra("flower_id", 0);
        }
        getReview(id);

        btnAddToCartDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddToCartButtonClicked();
            }
        });

        imgRate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score = 1.0;
                updateStarRating(score);
                imgRate1.setColorFilter(getResources().getColor(R.color.yellow));
            }
        });

        imgRate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score = 2.0;
                updateStarRating(score);
                imgRate1.setColorFilter(getResources().getColor(R.color.yellow));
                imgRate2.setColorFilter(getResources().getColor(R.color.yellow));
            }
        });

        imgRate3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score = 3.0;
                updateStarRating(score);
                imgRate1.setColorFilter(getResources().getColor(R.color.yellow));
                imgRate2.setColorFilter(getResources().getColor(R.color.yellow));
                imgRate3.setColorFilter(getResources().getColor(R.color.yellow));
            }
        });

        imgRate4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score = 4.0;
                updateStarRating(score);
                imgRate1.setColorFilter(getResources().getColor(R.color.yellow));
                imgRate2.setColorFilter(getResources().getColor(R.color.yellow));
                imgRate3.setColorFilter(getResources().getColor(R.color.yellow));
                imgRate4.setColorFilter(getResources().getColor(R.color.yellow));

            }
        });

        imgRate5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score = 5.0;
                updateStarRating(score);
                imgRate1.setColorFilter(getResources().getColor(R.color.yellow));
                imgRate2.setColorFilter(getResources().getColor(R.color.yellow));
                imgRate3.setColorFilter(getResources().getColor(R.color.yellow));
                imgRate4.setColorFilter(getResources().getColor(R.color.yellow));
                imgRate5.setColorFilter(getResources().getColor(R.color.yellow));

            }
        });
        GetMyCart();
        updateNotificationBadge(selectedQuantity);

        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(DetailItemActivity.this, CartList.class);
                startActivity(intent1);
            }
        });
    }

    private void updateStarRating(double scoreRate){
        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getIntExtra("flower_id", 0);
        }
        postReview(id, scoreRate);
    }

    private void postReview(long id, double score) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");

        ApiFlowerService.apiFlowerService.postPreview("Bearer " + token, id, score).enqueue(new Callback<Flower>() {
            @Override
            public void onResponse(Call<Flower> call, Response<Flower> response) {
                int errorCode = response.code();
                if (response.isSuccessful()) {
                    flower = response.body();
                    getReview(id);
                    Toast.makeText(getApplicationContext(), "Đánh giá thành công", Toast.LENGTH_SHORT).show();
                }else if(errorCode == 500){
                    Toast.makeText(getApplicationContext(), "Bạn đã đánh giá rồi", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Lỗi " + errorCode, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Flower> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getReview(long id) {
        ApiFlowerService.apiFlowerService.getPreview(id).enqueue(new Callback<Flower>() {
            @Override
            public void onResponse(Call<Flower> call, Response<Flower> response) {
                if (response.isSuccessful()) {
                    flower = response.body();
                    double rate = flower.getAvgScore();
                    txtRate.setText(String.valueOf(rate));
                }else {
                    String errorBody = response.errorBody().toString();
                    int errorCode = response.code();
                    Toast.makeText(getApplicationContext(), "Lỗi " + errorCode + ": " + errorBody, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Flower> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void updateNotificationBadge(int quantity) {
        if (quantity > 0) {
            notificationBadge.setVisibility(View.VISIBLE);
            notificationBadge.setText(String.valueOf(selectedQuantity));
        } else {
            notificationBadge.setVisibility(View.GONE);
        }
    }

    private void addToCart(long id, int quantity) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        ApiFlowerService.apiFlowerService.addItemToCart("Bearer " + token, id, quantity).enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                if (response.isSuccessful()) {
                    cart = response.body();
                    Toast.makeText(getApplicationContext(), "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                    GetMyCart();
                } else {
                    String errorBody = response.errorBody().toString();
                    int errorCode = response.code();

                    // Hiển thị thông báo lỗi hoặc ghi log để xem thông tin lỗi cụ thể
                    Toast.makeText(getApplicationContext(), "Lỗi " + errorCode + ": " + errorBody, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Cart> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void GetMyCart() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        ApiCartService.apiCartService.getCart("Bearer " + token).enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                if (response.isSuccessful()) {
                    selectedQuantity = response.body().getTotalAmount();
                    updateNotificationBadge(selectedQuantity);

                } else {
                    String errorBody = response.errorBody().toString();
                    int errorCode = response.code();

                    // Hiển thị thông báo lỗi hoặc ghi log để xem thông tin lỗi cụ thể
                    Toast.makeText(getApplicationContext(), "Lỗi " + errorCode + ": " + errorBody, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Cart> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void onAddToCartButtonClicked() {
        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getIntExtra("flower_id", 0);
        }

        selectedQuantity += 1;
        updateNotificationBadge(selectedQuantity);
        addToCart(id, 1);
    }


    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void DataTransfer() {
        Intent intent = getIntent();
        if (intent != null) {
            String flowerName = intent.getStringExtra("flower_name");
            double flowerPrice = intent.getDoubleExtra("flower_price", 0.0);
            String flowerImg = intent.getStringExtra("flower_img");
            String flowerDescription = intent.getStringExtra("flower_description");
            double flowerRate = intent.getDoubleExtra("flower_rate", 0.0);
            Integer flowerAmount = intent.getIntExtra("flower_amount", 0);


            txtName.setText(flowerName);
            txtDescription.setText(flowerDescription);
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            if (flowerPrice != 0) {
                txtPrice.setText(decimalFormat.format(flowerPrice) + " VNĐ");
            }
            Picasso.with(this).load(flowerImg).into(img);
            txtRate.setText(String.valueOf(flowerRate));
            txtSaleAmount.setText(String.valueOf("(" + flowerAmount.intValue() + " lượt bán)"));
        }
    }

    private void Mapping() {
        txtName = findViewById(R.id.txtName);
        txtPrice = findViewById(R.id.txtPrice);
        img = findViewById(R.id.imageDetail);
        imgRate1 = findViewById(R.id.imgRate1);
        imgRate2 = findViewById(R.id.imgRate2);
        imgRate3 = findViewById(R.id.imgRate3);
        imgRate4 = findViewById(R.id.imgRate4);
        imgRate5 = findViewById(R.id.imgRate5);
        imgCart = findViewById(R.id.imgCart);
        txtDescription = findViewById(R.id.txtDescription);
        txtRate = findViewById(R.id.txtRate);
        txtSaleAmount = findViewById(R.id.txtSaleAmount);
        toolbar = findViewById(R.id.toolbar);
        btnAddToCartDetail = findViewById(R.id.btnAddToCartDetail);
        notificationBadge = findViewById(R.id.notificationBadge);
        cart = new Cart();
        notificationBadge.setVisibility(View.GONE);
        flower = new Flower();
        account = new Account();
    }
}
