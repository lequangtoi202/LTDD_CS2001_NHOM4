package com.example.androidapp.activity;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapp.R;
import com.example.androidapp.adapter.CartAdapter;
import com.example.androidapp.api.ApiCartService;
import com.example.androidapp.api.ApiFlowerService;
import com.example.androidapp.api.ApiOrderService;
import com.example.androidapp.model.Cart;
import com.example.androidapp.model.CartDetail;
import com.example.androidapp.model.Flower;
import com.example.androidapp.model.Order;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentCartList extends Fragment {
    //Danh sách các view được định nghĩa
    Button mbtnConfirm, mbtnEditInfo, mbtnMinus, mbtnPlus, mbtnDelete;
    TextView mlblContentAddress, mlblCountProducts, mlblEstimatedValue, mlblPrice, mlblDeliveryValue, mlblServiceValue, mlblTotalValue, mlblArrivalDate;
    String recipientName, recipientPhone, recipientAddress, recipientArea;
    RecyclerView mlvProducts;
    ArrayList<CartDetail> listTest = new ArrayList<>();
    Cart cart;

    List<Flower> flowerList = new ArrayList<>();
    CartAdapter adapter;
    EditText medtQualities;
    int productsTotal = 0;
    String nameAreas = "";
    String token;
    Order order;
    double priceTotal, estimatedValue, deliveryValue, serviceValue = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart_list, container, false);
        recipientArea = "Quận 1";
        recipientName = "John Dave";
        recipientPhone = "081****132";
        recipientAddress = "50 Thành Thái, P.12, Q.10, HCM";

        mbtnConfirm = view.findViewById(R.id.btnConfirm);
        mbtnEditInfo = view.findViewById(R.id.btnEditInfo);
        mbtnMinus = view.findViewById(R.id.btnMinus);
        mbtnPlus = view.findViewById(R.id.btnPlus);
        mlblContentAddress = view.findViewById(R.id.lblContentAddress);
        mbtnDelete = view.findViewById(R.id.btnDelete);
        mlblCountProducts = view.findViewById(R.id.txtCountQuantities);
        mlblEstimatedValue = view.findViewById(R.id.lblEstimatedValue);
        mlblDeliveryValue = view.findViewById(R.id.lblDeliveryValue);
        mlblServiceValue = view.findViewById(R.id.lblServiceValue);
        mlblArrivalDate = view.findViewById(R.id.lblArrivalDate);
        mlblTotalValue = view.findViewById(R.id.lblTotalValue);
        mlvProducts = view.findViewById(R.id.lvProducts);
        medtQualities = view.findViewById(R.id.edtQualities);
        mlblPrice = view.findViewById(R.id.lblPrice);
        // Xử lý hiển thị thời gian dự kiến nhận hàng và thông tin cá nhân
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DATE);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        int jump = 2;
        mlblArrivalDate.setText(calcTimeArrival(day, month, year, jump));
        mlblContentAddress.setText(recipientName + " - " + recipientPhone + "\n" + recipientAddress + "\n" + "Khu vực: " + recipientArea);
        if (getArguments() != null) {
            String variableValue = getArguments().getString("token");
            // Now you have the variable value, and you can use it as needed
        }

        // listTest đã được xử lý lấy dữ liệu từ (Activity) CartList
        //CallApiGetAllFlowers();
        CallApiGetCart();
        CallApiGetProduct();
        mlvProducts.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CartAdapter(getContext(), listTest, this, mlblCountProducts, mlblTotalValue, token, cart);
        mlvProducts.setAdapter(adapter);


        String tempAdapterToString = adapter.toString();
        String[] str = splitString(tempAdapterToString);
        mlblCountProducts.setText(str[0]);

        estimatedValue = Double.parseDouble(str[1]);
        deliveryValue = 70000;
        serviceValue = 55000;

        mlblEstimatedValue.setText(Double.toString(estimatedValue));
        mlblDeliveryValue.setText(Double.toString(deliveryValue));
        mlblServiceValue.setText(Double.toString(serviceValue));


        mbtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(),
                        CartConfirmationActivity.class);
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
                token = sharedPreferences.getString("token", "");
                ApiOrderService.apiOrderService.saveOrder("Bearer " + token).enqueue(new Callback<Order>() {
                    @Override
                    public void onResponse(Call<Order> call, Response<Order> response) {

                        if (response.code() == 200){
                            order = response.body();
                            Toast.makeText(requireContext(), "Lưu order thành công", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(requireContext(), "Lưu order ko thành công", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Order> call, Throwable t) {

                    }
                });
                startActivity(intent);
            }
        });

        mbtnEditInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialogCustom = new Dialog(getContext());
                dialogCustom.setContentView(R.layout.cart_editdeliveryinfo);

                EditText medtName = dialogCustom.findViewById(R.id.edtName);
                EditText medtPhone = dialogCustom.findViewById(R.id.edtPhone);
                EditText medtAddress = dialogCustom.findViewById(R.id.edtAddress);
                Button mbtnSave = dialogCustom.findViewById(R.id.btnSaveInfo);
                Spinner mspListArea = dialogCustom.findViewById(R.id.spListArea);

                medtName.setText(recipientName);
                medtPhone.setText(recipientPhone);
                String nameArea = recipientArea;
                medtAddress.setText(recipientAddress);

                ArrayList<String> listArea = new ArrayList<String>();
                listArea.add("Quận 1");
                listArea.add("Quận 2");
                listArea.add("Quận 3");
                listArea.add("Quận 4");
                listArea.add("Quận 5");
                listArea.add("Quận 6");
                listArea.add("Quận 7");
                listArea.add("Quận 8");
                listArea.add("Quận 9");
                listArea.add("Quận 10");
                listArea.add("Quận 11");
                listArea.add("Quận 12");
                listArea.add("Huyện Bình Chánh");
                listArea.add("Quận Gò Vấp");
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, listArea);
                mspListArea.setAdapter(adapter);

                mspListArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ArrayAdapter<String> adapterS = (ArrayAdapter<String>) parent.getAdapter();

                        // Lấy đối tượng Department tại vị trí đã chọn
                        String s = adapterS.getItem(position);

                        recipientArea = s;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                mbtnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        recipientName = medtName.getText().toString();
                        recipientPhone = medtPhone.getText().toString();
                        recipientAddress = medtAddress.getText().toString();
                        mlblContentAddress.setText(recipientName + " - " + recipientPhone + "\n" + recipientAddress + "\n" + "Khu vực: " + recipientArea);

                        dialogCustom.dismiss();
                    }
                });

                dialogCustom.show();

//                Intent intent = new Intent();
//                intent.setClass(getContext(),
//                        CartEditDeliveryInfo.class);

//                String[] subAddress = mlblContentAddress.getText().toString().split("[\\n-]");
//
//                for (int i = 0; i < subAddress.length; i++) {
//                    if (i == 0) {
//                        recipientName = subAddress[0];
//                    }
//                    else if (i == 1) {
//                        recipientPhone = subAddress[1];
//                    }
//                    else if (i == 2) {
//                        recipientAddress = subAddress[2];
//                    }
//                    else {
//                        recipientArea = subAddress[3];
//                    }
//                }

//                intent.putExtra("value1", recipientName);
//                intent.putExtra("value2", recipientPhone);
//                intent.putExtra("value3", recipientAddress);
//
//                activityResultLauncher.launch(intent);
            }
        });

        return view;
    }


    // Phương thức xử lý ngắt chuỗi
    public String[] splitString(String temp) {
        String[] str = temp.split(" ");
        return str;
    }

    // Các phương thức xử lý thời gian
    public String getTimeOrderNow(int d, int m, int y, int hour, int minute, int second) {
        String result = "";

        result = Integer.toString(hour) + ":" + Integer.toString(minute) + ":" + Integer.toString(second) + ", " + Integer.toString(d) + "-" + Integer.toString(m) + "-" + Integer.toString(y);

        return result;
    }

    public String calcTimeArrival(int d, int m, int y, int jump) {
        String result = "";
        int maxDay, month, year;

        month = m;
        year = y;

        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                maxDay = 31;
                break;
            case 2:
                if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) {
                    maxDay = 29;
                } else {
                    maxDay = 28;
                }
                break;
            default:
                maxDay = 30;
                break;
        }

        int newDay = d + jump;
        int newMonth = month;
        int newYear = year;
        if (newDay > maxDay) {
            newDay = newDay - maxDay;
            if (month == 12) {
                newMonth = 1;
                newYear = year + 1;
            } else {
                newMonth = month + 1;
            }
        }

        result = newDay + "-" + newMonth + "-" + newYear;

        return result;
    }

    // Phương thức lấy cart từ API
    private void CallApiGetProduct() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        ApiCartService.apiCartService.getListProducts("Bearer " + token)
                .enqueue(new Callback<ArrayList<CartDetail>>() {
                    @Override
                    public void onResponse(Call<ArrayList<CartDetail>> call, Response<ArrayList<CartDetail>> response) {
                        listTest = response.body();
                        adapter = new CartAdapter(getContext(), listTest, getParentFragment(), mlblCountProducts, mlblTotalValue, token, cart);
                        mlvProducts.setAdapter(adapter);
                        Double totalPrice = listTest.stream()
                                .mapToDouble(f -> f.getTotalPrice())
                                .sum();
                        priceTotal = totalPrice + deliveryValue + serviceValue;
                        mlblTotalValue.setText(Double.toString(priceTotal));
                    }

                    @Override
                    public void onFailure(Call<ArrayList<CartDetail>> call, Throwable t) {
                        Toast.makeText(requireContext(), "Call API ERROR", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void CallApiGetCart() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        ApiCartService.apiCartService.getCart("Bearer " + token)
                .enqueue(new Callback<Cart>() {
                    @Override
                    public void onResponse(Call<Cart> call, Response<Cart> response) {
                        cart = response.body();
                    }

                    @Override
                    public void onFailure(Call<Cart> call, Throwable t) {
                        Toast.makeText(requireContext(), "Call API ERROR", Toast.LENGTH_LONG).show();
                    }
                });
    }
}