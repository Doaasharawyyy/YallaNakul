package com.example.yallanakul;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yallanakul.Models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;


public class SignUp extends AppCompatActivity {


    TextView member,logo;
    EditText phone,pass,Name;
    Button register;
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Toolbar  toolbar=findViewById(R.id.toolbar);
      //  setSupportActionBar(toolbar);
        logo=findViewById(R.id.useLogo);
        phone=findViewById(R.id.phone);
        pass=findViewById(R.id.pass);
        Name=findViewById(R.id.UserName);
        member=findViewById(R.id.member);
        register=findViewById(R.id.Register);



      /*  new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company


            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                // close this activity
            }
        }, SPLASH_TIME_OUT);*/
        //// -------------- font style ---------------
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/hazel grace.ttf");
        register.setTypeface(typeface);
        member.setTypeface(typeface);
        Typeface typeface1=Typeface.createFromAsset(getAssets(),"fonts/NABILA.TTF");
        logo.setTypeface(typeface1);
                member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SignUp.this,UserLogin.class);
                startActivity(i);
            }
        });

// Init Database
        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference table_user=database.getReference("User");

register.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        final ACProgressFlower dialog = new ACProgressFlower.Builder(SignUp.this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Uploading ...")
                .fadeColor(Color.DKGRAY).build();
        dialog.show();
   table_user.addValueEventListener(new ValueEventListener() {
       @Override
       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
           String name = Name.getText().toString();
           String Phone = phone.getText().toString();
           String Pass = pass.getText().toString();

           if (TextUtils.isEmpty(Phone) || TextUtils.isEmpty(Pass)|| TextUtils.isEmpty(name)) {
               dialog.dismiss();
               Toast.makeText(SignUp.this, "please fill empty field !!", Toast.LENGTH_SHORT).show();
           } else {
               // check if phone user already in database
               if (dataSnapshot.child(Phone).exists()) {
                   dialog.dismiss();
                   Toast.makeText(SignUp.this, "user Already Exist Sign In", Toast.LENGTH_SHORT).show();

                   Intent intent = new Intent(SignUp.this, UserLogin.class);
                   startActivity(intent);
               } else {
                   dialog.dismiss();
                   User user = new User(name, Pass,Phone);
                   table_user.child(Phone).setValue(user);
                   Toast.makeText(SignUp.this, "Sign Up Success", Toast.LENGTH_SHORT).show();
                   finish();

               }
           }
       }

       @Override
       public void onCancelled(@NonNull DatabaseError databaseError) {

       }
   });
    }

});

    }


}
