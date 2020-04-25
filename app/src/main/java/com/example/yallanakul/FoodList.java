package com.example.yallanakul;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.yallanakul.Models.Category;
import com.example.yallanakul.Models.Foods;
import com.example.yallanakul.ViewHolder.FoodViewHolder;
import com.example.yallanakul.ViewHolder.MenuViewHolder;
import com.example.yallanakul.inteface.ItemClicklistener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class FoodList extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference food_table;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    String MenuId="";
    FirebaseRecyclerAdapter<Foods, FoodViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        database = FirebaseDatabase.getInstance();
        food_table = database.getReference("Foods");

        // load menu
        recyclerView = findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //get intent category id---------
        if (getIntent()!=null) {
            MenuId = getIntent().getStringExtra("categorId");
        }
        if (!MenuId.isEmpty()&&MenuId!=null){
            loadMenu(MenuId);

        }


    }

    private void loadMenu(String categortyId) {
        adapter = new FirebaseRecyclerAdapter<Foods, FoodViewHolder>(
                Foods.class, R.layout.food_item, FoodViewHolder.class, food_table.orderByChild("categoryId").equalTo(categortyId)
        ) {


            @Override
            protected void populateViewHolder(FoodViewHolder foodViewHolder, Foods foods, int i) {

                foodViewHolder.foodtxtname.setText(foods.getName());
                Picasso.with(getBaseContext()).load(foods.getImage()).into(foodViewHolder.imageView);
                final Foods clickitem = foods;
                foodViewHolder.setItemClicklistener(new ItemClicklistener() {
                    @Override
                    public void onclick(View view, int position, boolean islongclick) {

                // start description activity ( *************FoodDetails.Activity******* )
                        Intent fooddetails=new Intent(FoodList.this,FoodDetails.class);

                        // send food  id to food details activity
                        fooddetails.putExtra("FoodId",adapter.getRef(position).getKey());
                        startActivity(fooddetails);
                    }
                    
                });
            }
        };
        recyclerView.setAdapter(adapter);

    }
}
