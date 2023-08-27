package com.example.androidapp.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapp.R;
import com.example.androidapp.api.ApiCartService;
import com.example.androidapp.api.ApiFlowerService;
import com.example.androidapp.model.Cart;
import com.example.androidapp.model.CartDetail;
import com.example.androidapp.model.Flower;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private List<CartDetail> db;
    TextView mlblCountProducts, mlblTotalValue;
    private Context context;
    Cart cart;
    long flowerId;
    Flower flower;


    private Fragment fragment;
    private boolean isEditTextFocused = false;
    String token;

    public CartAdapter(Context aContext, List<CartDetail> arr, Fragment fragment, TextView count, TextView totalPrice, String token, Cart cart) {
        this.db = arr;
        this.context = aContext;
        this.fragment = fragment;
        this.mlblCountProducts = count;
        this.mlblTotalValue = totalPrice;
        this.token = token;
        this.cart = cart;

    }

    // Tạo lớp ViewHolder để giữ các thành phần giao diện cho mỗi phần tử
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemNameView;
        public TextView itemPriceView;
        public EditText itemQualityView;
        public ImageView itemImgView;
        public Button itemBtnMinus;
        public Button itemBtnPlus;
        public Button itemDelete;

        public ViewHolder(View convertView) {
            super(convertView);
            itemNameView = convertView.findViewById(R.id.lblNameProduct);
            itemPriceView = convertView.findViewById(R.id.lblPrice);
            itemQualityView = convertView.findViewById(R.id.edtQualities);
            itemImgView = convertView.findViewById(R.id.imgvProduct);
            itemBtnMinus = convertView.findViewById(R.id.btnMinus);
            itemBtnPlus = convertView.findViewById(R.id.btnPlus);
            itemDelete = convertView.findViewById(R.id.btnDelete);
        }
    }

    // Phương thức này được gọi khi RecyclerView cần tạo một ViewHolder mới
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Tạo một View từ layout layout_item.xml
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new ViewHolder(convertView);
    }

    // Phương thức này được gọi khi RecyclerView cần hiển thị dữ liệu cho một ViewHolder cụ thể
    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CartDetail item = db.get(position);

//        int imageId = context.getResources().getIdentifier(item.getItemImg(), "drawable", context.getPackageName());
        flowerId = item.getFlowerId();
        ApiFlowerService.apiFlowerService.getFlowerById(flowerId).enqueue(new Callback<Flower>() {
            @Override
            public void onResponse(Call<Flower> call, Response<Flower> response) {
                flower = response.body();
                holder.itemNameView.setText(String.valueOf(flower.getName()));
                holder.itemPriceView.setText(String.valueOf(flower.getUnitPrice()));
                Picasso.with(context).load(flower.getUrlImage()).into(holder.itemImgView);
            }

            @Override
            public void onFailure(Call<Flower> call, Throwable t) {
                Toast.makeText(context.getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
            }
        });
        String quantity = Integer.toString(item.getQuantity());
        holder.itemQualityView.setText(quantity);
        mlblCountProducts.setText(String.valueOf(cart.getTotalAmount()));

        holder.itemDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItemToCart(db.get(position).getFlowerId());
                db.remove(position);
                SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", MODE_PRIVATE);
                token = sharedPreferences.getString("token", "");
                ApiCartService.apiCartService.getCart("Bearer " + token)
                        .enqueue(new Callback<Cart>() {
                            @Override
                            public void onResponse(Call<Cart> call, Response<Cart> response) {
                                cart = response.body();
                            }

                            @Override
                            public void onFailure(Call<Cart> call, Throwable t) {
                                Toast.makeText(context, "Call API ERROR", Toast.LENGTH_LONG).show();
                            }
                        });
                notifyDataSetChanged();
            }
        });

        holder.itemBtnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int choice = 1; //Biến này dùng để xác định loại updateQuantity
                int temp = db.get(position).getQuantity();
                temp++;

                String s = updateQuantity(db, choice, position);
                String[] str = s.split(" ");

                String tmp = str[0];
                mlblCountProducts.setText(tmp);
                updateItemToCart(db.get(position).getFlowerId(), temp);
                db.get(position).setQuantity(temp);
                notifyDataSetChanged();
            }
        });

        holder.itemBtnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = db.get(position).getQuantity();
                if (temp > 1) {
                    temp--;
                    int choice = 2; //Biến này dùng để xác định loại updateQuantity
                    String s = updateQuantity(db, choice, position);
                    String[] str = s.split(" ");

                    String tmp = str[0];
                    mlblCountProducts.setText(tmp);
                    updateItemToCart(db.get(position).getFlowerId(), temp);
                    db.get(position).setQuantity(temp);
                    notifyDataSetChanged();
                }
            }
        });
    }

    // Phương thức này trả về số lượng phần tử trong danh sách dữ liệu
    @Override
    public int getItemCount() {
        return db.size();
    }

    // Phương thức này trả về chuỗi gồm tổng số lượng sản phầm và tổng tiền tạm tính của sản phẩm
    @Override
    public String toString() {
        int countItems = 0;
        double priceTotal = 0;
        for (CartDetail p : db) {
            countItems = countItems + p.getQuantity();
            double x = p.getQuantity();
            priceTotal = priceTotal + (p.getTotalPrice()) * x;
        }
        String str = Integer.toString(countItems) + " " + Double.toString(priceTotal);

        return str;
    }

    // Phương thức này cập nhật thay đổi số lượng trên sản phẩm truyền vào, từ đó thay đổi giá tiền tạm tính khi sản phẩm truyền vào tăng/giảm
    public String updateQuantity(List<CartDetail> arr, int choice, int position) {
        String s = "";
        int tmp = 0;
        double value = 0;
        for (CartDetail p : arr) {
            tmp = tmp + p.getQuantity();
            double x = Double.parseDouble(String.valueOf(p.getQuantity()));
            value = value + p.getTotalPrice() * x;
        }

        if (choice == 0) {
            tmp = tmp - arr.get(position).getQuantity();
            double x = Double.parseDouble(String.valueOf(arr.get(position).getQuantity()));
            value = value - (arr.get(position).getTotalPrice()) * x;
        } else if (choice == 1) {
            tmp = tmp + 1;
            value = value + arr.get(position).getTotalPrice();
        } else if (choice == 2) {
            tmp = tmp - 1;
            value = value - arr.get(position).getTotalPrice();
        }

        s = tmp + " " + value;
        return s;
    }

    public void updateItemToCart(long id, int quantity) {
        ApiCartService.apiCartService.updateItemToCart("Bearer " + token, id, quantity)
                .enqueue(new Callback<Cart>() {
                    @Override
                    public void onResponse(Call<Cart> call, Response<Cart> response) {
                        cart = response.body();
                        mlblTotalValue.setText(String.valueOf(cart.getTotalPrice()));
                    }

                    @Override
                    public void onFailure(Call<Cart> call, Throwable t) {
                        Toast.makeText(context, "Call API ERROR", Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void deleteItemToCart(long id) {
        ApiCartService.apiCartService.deleteItemToCart("Bearer " + token, id)
                .enqueue(new Callback<Cart>() {
                    @Override
                    public void onResponse(Call<Cart> call, Response<Cart> response) {
                        cart = response.body();
                        mlblTotalValue.setText(String.valueOf(cart.getTotalPrice()));
                        mlblCountProducts.setText(String.valueOf(cart.getTotalAmount()));
                    }

                    @Override
                    public void onFailure(Call<Cart> call, Throwable t) {
                        Toast.makeText(context, "Call API ERROR", Toast.LENGTH_LONG).show();
                    }
                });
    }


}

