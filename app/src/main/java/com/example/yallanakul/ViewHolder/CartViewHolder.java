package com.example.yallanakul.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yallanakul.R;
import com.example.yallanakul.inteface.ItemClicklistener;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtcartname,txtprice,count;
    private ItemClicklistener itemClicklistener;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        txtcartname=itemView.findViewById(R.id.cartitem);
        txtprice=itemView.findViewById(R.id.itemprice);

        count=itemView.findViewById(R.id.cartitemcount);
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
