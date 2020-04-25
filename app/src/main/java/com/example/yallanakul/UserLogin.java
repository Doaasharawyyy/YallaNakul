package com.example.yallanakul;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yallanakul.Common.Common;
import com.example.yallanakul.Models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;


public class UserLogin extends AppCompatActivity {
TextView logo2;
EditText phone,pass;
CheckBox checkBox;
Button signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
                logo2=findViewById(R.id.logo2);
                phone=findViewById(R.id.phone);
                pass=findViewById(R.id.pass);
                checkBox=findViewById(R.id.checkbox);
                signin=findViewById(R.id.signin);

                //// -------------- font style ---------------
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/Sofye Demo.ttf");
            signin.setTypeface(typeface);
        Typeface typeface1=Typeface.createFromAsset(getAssets(),"fonts/NABILA.TTF");
            checkBox.setTypeface(typeface1);
            phone.setTypeface(typeface1);
            pass.setTypeface(typeface1);
            logo2.setTypeface(typeface1);


            // Init Database
       final FirebaseDatabase database=FirebaseDatabase.getInstance();
       final DatabaseReference table_user=database.getReference("User");

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ACProgressFlower dialog = new ACProgressFlower.Builder(UserLogin.this)
                        .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                        .themeColor(Color.WHITE)
                        .text("Uploading ...")
                        .fadeColor(Color.DKGRAY).build();
                dialog.show();


          table_user.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        String Phone = phone.getText().toString();
        String Pass = pass.getText().toString();
        if (TextUtils.isEmpty(Phone) || TextUtils.isEmpty(Pass)) {
            dialog.dismiss();
            Toast.makeText(UserLogin.this, "please fill empty field !!", Toast.LENGTH_SHORT).show();
        } else {
            /// check if user not exist on database
            if (dataSnapshot.child(Phone).exists()) {
                dialog.dismiss();


                // Get User Information
                User user = dataSnapshot.child(Phone).getValue(User.class);
                if (user.getPassword().equals(Pass)) {
                    Intent i = new Intent(UserLogin.this, UserHome.class);
                   Common.currentuser=user;
                    Bundle b = new Bundle();
                    b.putString("username", Common.currentuser.getName());
                    i.putExtras(b);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(UserLogin.this, "error username or pass", Toast.LENGTH_SHORT).show();

                }
            }
            /// else of if user not exist on database
            else {
                dialog.dismiss();
                Toast.makeText(UserLogin.this, "User not exist on database", Toast.LENGTH_SHORT).show();

            }
        }
    }
    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
});

            }
        });

    }


}
