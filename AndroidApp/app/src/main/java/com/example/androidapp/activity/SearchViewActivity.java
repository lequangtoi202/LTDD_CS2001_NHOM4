package com.example.androidapp.activity;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapp.R;
import com.example.androidapp.adapter.FlowerAdapter;
import com.example.androidapp.api.ApiFlowerService;
import com.example.androidapp.model.Flower;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchViewActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SearchView searchView;
    List<Flower> flowerList;
    FlowerAdapter flowerAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_view);

        Mapping();
        GetCLickOnSearchView();
        Config();
        CallApiGetFlower();
        GetCLickOnSearchView();
    }

    private void GetCLickOnSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Flower> listFilter = new ArrayList<>();
                for(Flower flower: flowerList){
                    if(flower.getName().toLowerCase().contains(newText.toLowerCase())){
                        listFilter.add(flower);
                    }
                }
                LoadData(listFilter);
                return false;
            }
        });
    }

    private void Mapping() {
        recyclerView = findViewById(R.id.recycleView);
        searchView = findViewById(R.id.searchView);
        flowerList = new ArrayList<>();
    }

    private void Config() {
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    private void CallApiGetFlower() {
        ApiFlowerService.apiFlowerService.getListFlowers()
                .enqueue(new Callback<List<Flower>>() {
                    @Override
                    public void onResponse(Call<List<Flower>> call, Response<List<Flower>> response) {

                        flowerList = response.body();
                        flowerAdapter = new FlowerAdapter(flowerList, getApplicationContext());
                        recyclerView.setAdapter(flowerAdapter);
                    }

                    @Override
                    public void onFailure(Call<List<Flower>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Call API ERROR", Toast.LENGTH_LONG).show();
                    }
                });

    }

    private void LoadData(List<Flower> flowerList){
        FlowerAdapter flowerAdapter = new FlowerAdapter(flowerList, getApplicationContext());
        recyclerView.setAdapter(flowerAdapter);
    }
}
