 package com.example.yallanakul;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.yallanakul.Common.Common;
import com.example.yallanakul.Models.Foods;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

 public class FoodDetails extends AppCompatActivity {
TextView foodname,foodprice,fooddescription;
ImageView foodimage;
CollapsingToolbarLayout collapsingToolbarLayout;
FloatingActionButton cartbtn;
ElegantNumberButton counter;
String foodId="";
FirebaseDatabase database;
DatabaseReference foods;
//private String FoodId="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);

database=FirebaseDatabase.getInstance();
foods=database.getReference("Foods");

        counter=findViewById(R.id.btnincrement);
        cartbtn=findViewById(R.id.btn_cart);
        fooddescription=findViewById(R.id.fooddescription);
        foodname=findViewById(R.id.foodname);
        foodprice=findViewById(R.id.foodprice);
        foodimage=findViewById(R.id.foodimg);
        collapsingToolbarLayout=findViewById(R.id.colapase);
collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);

// get food id from intent From FOODLIST Activity
        if (getIntent()!=null)
            foodId=getIntent().getStringExtra("FoodId");
        if (!foodId.isEmpty()){
            getDetailedFood(foodId);
        }

        // cart listener
        cartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addingToCart();
            }
        });
    }

     private void addingToCart() {
        String savecurrenttime,savecurrentdate;
         Calendar calender= Calendar.getInstance();
         SimpleDateFormat currentdate=new SimpleDateFormat("MMM dd,YYYY");
         savecurrentdate=currentdate.format(calender.getTime());
         SimpleDateFormat currenttime=new SimpleDateFormat("HH:mm:ss ");
         savecurrenttime=currenttime.format(calender.getTime());
         final DatabaseReference cartlist=FirebaseDatabase.getInstance().getReference().child("CartList");
         final HashMap<String,Object>cartMap=new HashMap<>();
         cartMap.put("FoodId",foodId);
         cartMap.put("Foodname",foodname.getText().toString());
         cartMap.put("Price",foodprice.getText().toString());
         cartMap.put("Date",savecurrentdate);
         cartMap.put("Time",savecurrenttime);
         cartMap.put("Quantity",counter.getNumber());
         cartMap.put("Details",fooddescription.getText().toString());
         cartlist.child("UserView").child(Common.currentuser.getPhone()).child("Foods").child(foodId)
                 .updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
             @Override
             public void onComplete(@NonNull Task<Void> task) {
            if (task.isSuccessful()){
                cartlist.child("AdminView ").child(Common.currentuser.getPhone()).child("Foods").child(foodId)
                        .updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                   if (task.isSuccessful()){
                       Toast.makeText(FoodDetails.this, "Added To Cart List", Toast.LENGTH_SHORT).show();
                     //  Intent intent=new Intent(FoodDetails.this,UserHome.class);
                       //startActivity(intent);
                   }
                    }
                });
            }
             }
         });


     }

     private void getDetailedFood(String foodId) {
        foods.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               
                Foods foods=dataSnapshot.getValue(Foods.class);
                Picasso.with(getBaseContext()).load(foods.getImage()).into(foodimage);
                collapsingToolbarLayout.setTitle(foods.getName());
                foodprice.setText(foods.getPrice());
               foodname.setText(foods.getName());
                fooddescription.setText(foods.getDescription());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


     }
 }
