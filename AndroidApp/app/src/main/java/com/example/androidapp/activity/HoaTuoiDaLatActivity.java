package com.example.androidapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.androidapp.R;
import com.example.androidapp.adapter.FlowerAdapter;
import com.example.androidapp.api.ApiFlowerService;
import com.example.androidapp.model.Category;
import com.example.androidapp.model.Flower;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HoaTuoiDaLatActivity extends AppCompatActivity {
    RecyclerView recycleView;
    List<Flower> flowerList;
    FlowerAdapter flowerAdapter;
    List<Category> categoryList;
    int categoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hoatuoidalat_page);
        Mapping();
        Config();

        Intent intent = getIntent();
        if (intent != null) {
            categoryId = intent.getIntExtra("category_id", 0);
        }

        CallApiGetFlowerByCategoryId();
    }

    private void Mapping() {
        recycleView = findViewById(R.id.recycleView);
        categoryList = new ArrayList<>();
        flowerList = new ArrayList<>();
    }

    private void Config() {
        recycleView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recycleView.setLayoutManager(gridLayoutManager);
    }



    private void CallApiGetFlowerByCategoryId() {
        ApiFlowerService.apiFlowerService.getListFlowersByCategory(categoryId)
                .enqueue(new Callback<List<Flower>>() {
                    @Override
                    public void onResponse(Call<List<Flower>> call, Response<List<Flower>> response) {
                        if (response.isSuccessful()) {
                            flowerList = response.body();
                            flowerAdapter = new FlowerAdapter(flowerList, HoaTuoiDaLatActivity.this);
                            recycleView.setAdapter(flowerAdapter);
                        } else {
                            Toast.makeText(HoaTuoiDaLatActivity.this,"Call API ERROR", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Flower>> call, Throwable t) {
                        Toast.makeText(HoaTuoiDaLatActivity.this, "CALL API ERROR", Toast.LENGTH_SHORT).show();
                    }
                });

    }

}