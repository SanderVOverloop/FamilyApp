package com.example.familyapp.familyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BoodschappenlijstActivity extends AppCompatActivity implements View.OnClickListener {
    DatabaseReference dref;
    ListView listview;
    ArrayList<String> list=new ArrayList<>();
    EditText product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boodschappenlijst);

        listview = (ListView)findViewById(R.id.listview);
        final ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,list);
        listview.setAdapter(adapter);

        dref = FirebaseDatabase.getInstance().getReference("Boodschappenlijst");

        findViewById(R.id.btn_add).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add:
                btnClick();
                break;
        }
    }



    public void btnClick(){
        String productname = product.getText().toString().trim();

        if(productname.isEmpty()){
            product.setError("Please enter a product");
            product.requestFocus();
        }
        else {
            String id = dref.push().getKey();
            Boodschappen boodschappen = new Boodschappen(id,productname);
            dref.child(id).setValue(id,productname);

        }
    }


}
