package com.example.androidapp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.androidapp.R;
import com.example.androidapp.adapter.ViewPagerAdapter;
import com.example.androidapp.model.Order;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

//Chưa thể xử lý editText cập nhật số lượng sản phẩm
//Chưa xử lý tự động thay đổi phí ship khi đổi khu vực

//Đã xây dựng phương thức xử lý thông tin của các chức năng cơ bàn như:
//+Button tăng giảm số lượng của 1 sản phẩm, truyền đi và cập nhật lại thông tin giữa 2 Activities.
//+Button xác nhận hoàn tất đặt đơn hàng.
//+Đã lấy thời gian thực truyền thẳng đến [Order Detail] khi [Confirm]
//+Đã lấy thời gian dự kiến giao hàng (+2 ngày kể từ khi giỏ hàng hiện lên)
//+Đã hiện AlertDialog xác nhận có muốn xóa khỏi giỏ hàng khi thoa tác [giảm] số lượng sản phẩm < 1 hay thao tác [delete].
//+Đã thực hiện xây dựng phương thức tính tổng giá tiền
//+Đã chặn phương thức quay lại từ [Cart confirm] sau khi đã thao tác [confirm]
//+Đã tương tác được giá trị Đếm tổng sản phẩm về 0 để thoát khỏi giao diện CartList
//+Đã thay đổi được giá trị cũng như số lượng khi thao tác tăng giảm xóa sản phẩm

public class CartList extends AppCompatActivity{
    private TabLayout mtabBar;
    private ViewPager2 mviewPager;
    private FragmentCartList fragmentCartList;
    private FragmentOrdersList fragmentOrdersList;
    ArrayList<Order> orders;

    //Phương thức truyền - nhận dữ liệu giữa 2 Activities
//    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult activityResult) {
//                    int result = activityResult.getResultCode();
//                    Intent data = activityResult.getData();
//                    if (result == CartEditDeliveryInfo.requestCodeDeli) {
//                        String value1 = data.getStringExtra("key1");
//                        String value2 = data.getStringExtra("key2");
//                        String value3 = data.getStringExtra("key3");
//                        String value4 = data.getStringExtra("key4");
//                        String str = value1 + "-" + value2 + "\n" + value3 + "\n" + "Khu vực:" + value4;
//                        lblContentAddress.setText(str);
//
//                        if ("Quận 4".equals(value4)) {
//                            deliveryValue = 55000;
//                            mlblDeliveryValue.setText(Double.toString(deliveryValue));
//                        }
//                    }
//                    else {
//                        Toast.makeText(CartList.this,
//                                "Can not get new recipient's information",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_list);

        mtabBar = findViewById(R.id.tabBar);
        mviewPager = findViewById(R.id.viewPager);



        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle());
        viewPagerAdapter.addFragment(new FragmentCartList(), "Product List");
        viewPagerAdapter.addFragment(new FragmentOrdersList(), "Orders List");
        mviewPager.setAdapter(viewPagerAdapter);

        new TabLayoutMediator(mtabBar, mviewPager, (tab, position) -> {
            // Thiết lập tiêu đề tab tại vị trí position
            // Ví dụ: tab.setText("Tab " + position);
            tab.setText(viewPagerAdapter.getPagerTitle(position));
        }).attach();
    }

    // Các phương thức xử lý ActionBar-TabMenu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cart_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.back) {
            Intent intent = new Intent();
            intent.setClass(CartList.this,
                    NullPageActivity.class);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.exit) {
            finishAffinity();
        }
        else if (item.getItemId() == R.id.undo) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed(); // Đóng chỉ activity hiện tại
        //Nếu không có Activity nào trước đó thì sẽ đóng ứng dụng - vào trạng thái onStop() chứ không phải onDestroy()
    }
}
