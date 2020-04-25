package com.example.yallanakul.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yallanakul.R;
import com.example.yallanakul.inteface.ItemClicklistener;

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView foodtxtname;
    public ImageView imageView;
    private ItemClicklistener itemClicklistener;

    public FoodViewHolder(@NonNull View itemView) {
        super(itemView);

        foodtxtname=itemView.findViewById(R.id.food_name);
        imageView=itemView.findViewById(R.id.food_image);
        itemView.setOnClickListener(this);

    }

    public void setItemClicklistener(ItemClicklistener itemClicklistener) {
        this.itemClicklistener = itemClicklistener;
    }


    @Override
    public void onClick(View v) {
        itemClicklistener.onclick(v,getAdapterPosition(),false);

    }
}
