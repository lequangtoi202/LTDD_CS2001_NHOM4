package com.example.androidapp.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapp.R;


//Cần fix lỗi onDestroy()
//Hiện trạng, nhấn double back lần đầu sẽ bị gọi chương trình CartList
//Nhấn double back lần 2 mới thật sự tắt chương trình
public class NullPageActivity extends AppCompatActivity {
    Button mbtnBackToHomepage;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nullpage);

        mbtnBackToHomepage = findViewById(R.id.btnBackToHomepage);
        mbtnBackToHomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });
    }


    // Các phương thức xử lý tabbar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nullpage_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.exit) {
            finishAffinity();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(NullPageActivity.this,
                "Nhấn quay lại 1 lần nữa để thoát ứng dụng",
                Toast.LENGTH_SHORT).show();
        onDestroy();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finishAffinity();
    }
}