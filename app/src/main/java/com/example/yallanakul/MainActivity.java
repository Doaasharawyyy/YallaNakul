package com.example.yallanakul;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;

import com.spark.submitbutton.SubmitButton;

public class MainActivity extends AppCompatActivity  {
SubmitButton btnwlc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnwlc=findViewById(R.id.btnwelc);



          }


    public void submitBtn(View view) {
        Intent i =new Intent(MainActivity.this,SignUp.class);
        startActivity(i);
        finish();

    }
}
