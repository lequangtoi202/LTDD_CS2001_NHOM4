package com.example.androidapp.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapp.R;
import com.example.androidapp.activity.DetailItemActivity;
import com.example.androidapp.api.ApiFlowerService;
import com.example.androidapp.model.Flower;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FlowerAdapter extends RecyclerView.Adapter<FlowerAdapter.ViewHolder> {

    private List<Flower> alFlower;
    private Context context;
    Integer n = 0;


    public FlowerAdapter(List<Flower> alFlower, Context context) {
        this.alFlower = alFlower;
        this.context = context;;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.flower_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Flower flower = alFlower.get(position);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        Picasso.with(context).load(flower.getUrlImage()).into(holder.imgView);
        holder.txtFlowerName.setText(String.valueOf(alFlower.get(position).getName()));
        if (holder.txtFlowerPrice != null) {
            holder.txtFlowerPrice.setText(decimalFormat.format(alFlower.get(position).getUnitPrice()) + " VNĐ");
        }



        ApiFlowerService.apiFlowerService.getAmountSoldByFLowerId(flower.getId()).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                n = response.body();
                if (n != null) {
                    holder.txtAmount.setText("Lượt bán: " + String.valueOf(n.intValue()));
                } else {
                    Toast.makeText(context.getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(context.getApplicationContext(), "Call API ERROR", Toast.LENGTH_LONG).show();
            }
        });

        holder.imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Flower flower = alFlower.get(position);

                Intent intent = new Intent(v.getContext(), DetailItemActivity.class);
                intent.putExtra("flower_id", flower.getId());
                intent.putExtra("flower_name", flower.getName());
                intent.putExtra("flower_price", flower.getUnitPrice());
                intent.putExtra("flower_img", flower.getUrlImage());
                intent.putExtra("flower_description", flower.getDescription());
                intent.putExtra("flower_rate", flower.getAvgScore());
                intent.putExtra("flower_amount", n);
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {

        return alFlower.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView txtFlowerName, txtFlowerPrice, txtAmount;
        private final ImageView imgView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtFlowerName = itemView.findViewById(R.id.txtFlowerName);
            txtFlowerPrice = itemView.findViewById(R.id.txtFlowerPrice);
            txtAmount = itemView.findViewById(R.id.txtAmount);
            imgView = itemView.findViewById(R.id.imgView);
        }

    }


}
