package com.example.yallanakul.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yallanakul.inteface.ItemClicklistener;
import com.example.yallanakul.R;

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtmenuname;
    public ImageView imageView;
    private ItemClicklistener itemClicklistener;
    public MenuViewHolder(@NonNull View itemView) {
        super(itemView);

            txtmenuname=itemView.findViewById(R.id.menu_name);
            imageView=itemView.findViewById(R.id.menu_image);
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
