package com.example.familyapp.familyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
    public void onClickKomenEten(View view){
        startActivity(new Intent(this, KomenEtenActivity.class));
    }

    public void onClickBoodschappenLijst(View view){
        startActivity(new Intent(this, BoodschappenlijstActivity.class));
    }
}
