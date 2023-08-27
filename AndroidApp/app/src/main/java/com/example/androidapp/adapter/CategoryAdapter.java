package com.example.androidapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.androidapp.R;
import com.example.androidapp.activity.HoaTuoiDaLatActivity;
import com.example.androidapp.model.Category;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {
    private List<Category> category;
    private Context context;

    public CategoryAdapter(List<Category> category, Context context) {
        this.category = category;
        this.context = context;
    }

    @Override
    public int getCount() {
        return category.size();
    }

    @Override
    public Object getItem(int position) {

        return category.get(position);
    }

    @Override
    public long getItemId(int position) {
        return category.get(position).getId();
    }

    public class ViewHolder {
        TextView txtCategory;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.category_item, null);
            viewHolder.txtCategory = view.findViewById(R.id.txtCategory);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.txtCategory.setText(category.get(position).getName());

        viewHolder.txtCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Category category1 = category.get(position);

                long categoryId = getItemId(position);

                // Chuyển sang DetailItemActivity và chuyển dữ liệu qua Intent
                Intent intent = new Intent(v.getContext(), HoaTuoiDaLatActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Thêm cờ FLAG_ACTIVITY_NEW_TASK vào intent
                intent.putExtra("category_id", category1.getId());
                v.getContext().startActivity(intent);
            }
        });

        return view;
    }

}
