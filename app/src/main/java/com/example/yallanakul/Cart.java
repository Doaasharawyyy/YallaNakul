package com.example.yallanakul;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.yallanakul.Common.Common;
import com.example.yallanakul.Models.CartList;
import com.example.yallanakul.Models.Foods;
import com.example.yallanakul.ViewHolder.CartViewHolder;
import com.example.yallanakul.ViewHolder.FoodViewHolder;
import com.example.yallanakul.inteface.ItemClicklistener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.Locale;

public class Cart extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    DatabaseReference request;
    TextView txttotalprice;
    FirebaseDatabase database;
    DatabaseReference cart;
    Button placeorder;
    int total=0;
    FirebaseRecyclerAdapter<CartList, CartViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        txttotalprice = findViewById(R.id.total);
        placeorder = findViewById(R.id.placeorder);
        database = FirebaseDatabase.getInstance();
        cart = (DatabaseReference) database.getReference("CartList").child("UserView").child(Common.currentuser.getPhone()).child("Foods");

        recyclerView = findViewById(R.id.cartlist);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadMenu();

    }

    private void loadMenu() {
        adapter = new FirebaseRecyclerAdapter<CartList, CartViewHolder>(CartList.class,
                R.layout.cart_layout, CartViewHolder.class, cart) {
            @Override
            protected void populateViewHolder(CartViewHolder cartViewHolder, CartList cartList, int i) {

                cartViewHolder.txtcartname.setText(cartList.getFoodname());
                cartViewHolder.txtprice.setText(cartList.getPrice());
                cartViewHolder.count.setText(cartList.getQuantity());
               // total price calculation
                 int oneitemprice=(Integer.valueOf(cartList.getPrice()))*(Integer.valueOf(cartList.getQuantity()));
               total+=oneitemprice;
                Locale locale=new Locale("en","EG");
                NumberFormat fmt=NumberFormat.getCurrencyInstance(locale);
                txttotalprice.setText(fmt.format(total));
            }
        };
        recyclerView.setAdapter(adapter);

    }
}







  /*  final DatabaseReference cartlist=FirebaseDatabase.getInstance().getReference().child("OrderDetails");
    FirebaseRecyclerOptions<CartList> options=new FirebaseRecyclerOptions.Builder<CartList>()
            .setQuery(cartlist.child("User View").child(Common.currentuser.getPhone()).child("Foods"),CartList.class).build();
        FirebaseRecyclerAdapter<CartList, CartViewHolder>adapter=new FirebaseRecyclerAdapter<CartList, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i, @NonNull CartList cartList) {

            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return null;
            }
        }
    }*/


