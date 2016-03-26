package com.example.dominik.bierdeckel;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by dominik on 25.03.16.
 */
//TODO: Create adapter
public class FoodItemAdapter extends ArrayAdapter<Food>{
    Context context;
    int layoutResourceId;
    List<Food> data = null;

    public FoodItemAdapter(Context context, int layoutResourceId, List<Food> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        FoodHolder holder;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new FoodHolder();
            holder.foodName = (TextView)row.findViewById(R.id.foodName);
            holder.foodPrice = (TextView)row.findViewById(R.id.foodPrice);
            holder.foodCount = (TextView)row.findViewById(R.id.foodCount);

            row.setTag(holder);
        }
        else
        {
            holder = (FoodHolder)row.getTag();
        }

        Food food = data.get(position);
        holder.foodName.setText(food.foodName);
        holder.foodPrice.setText(String.format("%.2f", food.price) + "€");
        holder.foodCount.setText(Integer.toString(food.count));

        Button decrementBtn = (Button)row.findViewById(R.id.decrementBtn);
        Button incrementBtn = (Button)row.findViewById(R.id.incrementBtn);

        decrementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(data.get(position).count > 0) {
                    data.get(position).count--;
                }
                notifyDataSetChanged();
                MainActivity mainActivity = (MainActivity)context;
                mainActivity.refreshPriceSumView();
            }
        });
        incrementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.get(position).count++;
                notifyDataSetChanged();
                MainActivity mainActivity = (MainActivity)context;
                mainActivity.refreshPriceSumView();
            }
        });

        return row;
    }

    static class FoodHolder
    {
        TextView foodName;
        TextView foodPrice;
        TextView foodCount;
    }
}
