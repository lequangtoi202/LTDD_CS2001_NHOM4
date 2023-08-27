package com.example.androidapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.androidapp.R;
import com.example.androidapp.adapter.CategoryAdapter;
import com.example.androidapp.api.ApiAccountService;
import com.example.androidapp.api.ApiCartService;
import com.example.androidapp.api.ApiFlowerService;
import com.example.androidapp.model.Account;
import com.example.androidapp.model.Cart;
import com.example.androidapp.model.Category;
import com.google.android.material.navigation.NavigationView;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductPage extends AppCompatActivity {
    CategoryAdapter categoryAdapter;
    List<Category> lCategory;
    Toolbar toolBarMain;
    ViewFlipper viewFlipper;
    NavigationView narView;
    ListView lvMain;
    DrawerLayout drawerLayout;
    ImageButton imageButtonSearch;
    FragmentContainerView fragmentContainerView;

    Button btnNew, btnBestSeller, btnFavourite, btnDefault;
    ImageView imgView, imgCart;
    TextView txtNameCus;
    NotificationBadge notificationBadge1;
    int textColor = Color.WHITE;

    int textColor1;

    int selectedQuantity = 0;
    String token;
    Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_page);
        textColor1 = ContextCompat.getColor(this, R.color.clGray);
        Mapping();
        ActionBar();
        ActionViewFlipper();
        getEventCategoryClick();
        getEventButtonSearchClick();
        if (isConnected(this)) {
            CallApiGetCategory();
        } else {
            Toast.makeText(getApplicationContext(), "Internet Erorr!", Toast.LENGTH_LONG).show();
        }

        ButtonTransferFragment();
        SearchTransferNewActivity();
        GetMyCart();
        updateNotificationBadge();

        txtNameCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });
        getAccount();

        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ProductPage.this, CartList.class);
                startActivity(intent1);
            }
        });
    }

    private void getAccount() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");

        ApiAccountService.apiAccountService.getMyAccount("Bearer " + token).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response.isSuccessful()) {
                    account = response.body();
                    String name = account.getName();
                    txtNameCus.setText(name);
                }else {
                    String errorBody = response.errorBody().toString();
                    int errorCode = response.code();
                    Toast.makeText(getApplicationContext(), "Lỗi " + errorCode + ": " + errorBody, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateNotificationBadge() {
        GetMyCart();
        if (selectedQuantity > 0) {
            notificationBadge1.setVisibility(View.VISIBLE);
            notificationBadge1.setText(String.valueOf(selectedQuantity));
        } else {
            notificationBadge1.setVisibility(View.GONE);
        }
    }


    private void GetMyCart() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        ApiCartService.apiCartService.getCart("Bearer " + token).enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                if (response.isSuccessful()) {
                    selectedQuantity = response.body().getTotalAmount();
                    updateNotificationBadge();
                } else {
                    String errorBody = response.errorBody().toString();
                    int errorCode = response.code();
                    Toast.makeText(getApplicationContext(), "Lỗi " + errorCode + ": " + errorBody, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Cart> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Product page ERROR", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void SearchTransferNewActivity() {
        imageButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchPage = new Intent(getApplicationContext(), SearchViewActivity.class);
                startActivity(searchPage);
            }
        });
    }

    private void ButtonTransferFragment() {
        btnDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, DefaultFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();
                btnDefault.setBackgroundResource(R.drawable.ic_button_rectangle);
                btnDefault.setTextColor(textColor);
                btnNew.setBackgroundResource(R.drawable.custom_button_1);
                btnNew.setTextColor(textColor1);
                btnBestSeller.setBackgroundResource(R.drawable.custom_button_1);
                btnBestSeller.setTextColor(textColor1);
                btnFavourite.setBackgroundResource(R.drawable.custom_button_1);
                btnFavourite.setTextColor(textColor1);
            }
        });

        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, NewFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();
                btnNew.setBackgroundResource(R.drawable.ic_button_rectangle);
                btnNew.setTextColor(textColor);
                btnDefault.setBackgroundResource(R.drawable.custom_button_1);
                btnDefault.setTextColor(textColor1);
                btnBestSeller.setBackgroundResource(R.drawable.custom_button_1);
                btnBestSeller.setTextColor(textColor1);
                btnFavourite.setBackgroundResource(R.drawable.custom_button_1);
                btnFavourite.setTextColor(textColor1);
            }
        });
        btnFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, FavouriteFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();

                btnFavourite.setBackgroundResource(R.drawable.ic_button_rectangle);
                btnFavourite.setTextColor(textColor);
                btnDefault.setBackgroundResource(R.drawable.custom_button_1);
                btnDefault.setTextColor(textColor1);
                btnBestSeller.setBackgroundResource(R.drawable.custom_button_1);
                btnBestSeller.setTextColor(textColor1);
                btnNew.setBackgroundResource(R.drawable.custom_button_1);
                btnNew.setTextColor(textColor1);
            }
        });
        btnBestSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, BestSellerFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();

                btnBestSeller.setBackgroundResource(R.drawable.ic_button_rectangle);
                btnBestSeller.setTextColor(textColor);
                btnDefault.setBackgroundResource(R.drawable.custom_button_1);
                btnDefault.setTextColor(textColor1);
                btnNew.setBackgroundResource(R.drawable.custom_button_1);
                btnNew.setTextColor(textColor1);
                btnFavourite.setBackgroundResource(R.drawable.custom_button_1);
                btnFavourite.setTextColor(textColor1);
            }
        });
    }


    private void getEventCategoryClick() {
        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent hoaTuoiDaLat = new Intent(getApplicationContext(), HoaTuoiDaLatActivity.class);
                        startActivity(hoaTuoiDaLat);
                        break;
                    case 1:
                        Intent hoaTrangTriTiec = new Intent(getApplicationContext(), HoaTrangTriTiecActivity.class);
                        startActivity(hoaTrangTriTiec);
                        break;
                }
            }
        });
    }

    private void getEventButtonSearchClick() {
        imageButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchPage = new Intent(getApplicationContext(), SearchViewActivity.class);
                startActivity(searchPage);
            }
        });

    }


    private void CallApiGetCategory() {
        ApiFlowerService.apiFlowerService.getListCategories()
            .enqueue(new Callback<List<Category>>() {
                @Override
                public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                    if (response.isSuccessful()) {
                        lCategory = response.body();
                        categoryAdapter = new CategoryAdapter(lCategory, getApplicationContext());
                        lvMain.setAdapter(categoryAdapter);
                    } else {
                        Toast.makeText(getApplicationContext(), "Call API ERROR", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Category>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Call API ERROR", Toast.LENGTH_LONG).show();
                }
            });
    }

    private void ActionViewFlipper() {
        List<String> advertise = new ArrayList<>();
        advertise.add("https://img-cdn.pixlr.com/pixlr-templates/620ef774113be322f3db22c3/preview.webp");
        advertise.add("https://images.foody.vn/res/g72/714583/prof/s640x400/file_restaurant_photo_2p3r_16054-cb706533-201116091020.jpg");
        advertise.add("https://kenh14cdn.com/2018/2/10/photo-5-15182306840001655319275.jpg");
        for (int i = 0; i < advertise.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(advertise.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(slide_in);
        viewFlipper.setOutAnimation(slide_out);
    }

    private void Mapping() {
        viewFlipper = findViewById(R.id.viewFlipper);
        narView = findViewById(R.id.narView);
        toolBarMain = findViewById(R.id.toolBarMain);
        lvMain = findViewById(R.id.lvMain);
        drawerLayout = findViewById(R.id.drawerLayout);
        imgView = findViewById(R.id.imgView);
        imageButtonSearch =  findViewById(R.id.imgButtonSearch);
        btnDefault = findViewById(R.id.btnDefault);
        btnNew = findViewById(R.id.btnNew);
        btnBestSeller = findViewById(R.id.btnBestSeller);
        btnFavourite = findViewById(R.id.btnFavourite);
        fragmentContainerView = findViewById(R.id.fragmentContainerView);
        notificationBadge1 = findViewById(R.id.notificationBadge1);
        notificationBadge1.setVisibility(View.GONE);
        lCategory = new ArrayList<>();
        txtNameCus = findViewById(R.id.txtNameCus);
        account = new Account();
        imgCart = findViewById(R.id.imgCart);
    }

    public void ActionBar() {
        toolBarMain.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolBarMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBarMain.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(narView);
            }
        });
    }

    private boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifi != null && wifi.isConnected()) || (mobile != null && mobile.isConnected())) {
            return true;
        } else {
            return false;
        }
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.login_logout_menu, popupMenu.getMenu());


        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.option1) {
                    Intent intent = new Intent(ProductPage.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                }
                return false;
            }
        });

        popupMenu.show();
    }

}