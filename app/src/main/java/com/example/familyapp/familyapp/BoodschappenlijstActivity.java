package com.example.familyapp.familyapp;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BoodschappenlijstActivity extends AppCompatActivity {

    private DatabaseReference dref;

    private ListView listview;
    private EditText editText;
    private Button button;
    private ImageView imageView;

    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boodschappenlijst);

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.CustomToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Boodschappenlijst");
        getSupportActionBar().setIcon(getDrawable(R.drawable.ic_addwhite));*/



        dref = FirebaseDatabase.getInstance().getReference("Boodschappenlijst");

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);

        listview = (ListView)findViewById(R.id.listview);
        imageView = (ImageView) findViewById(R.id.img_Add);


        listview.setAdapter(adapter);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(BoodschappenlijstActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_boodschappen, null);
                final EditText mProduct = (EditText) mView.findViewById(R.id.etProduct);
                Button mToevoegen = (Button) mView.findViewById(R.id.btn_toevoegen);

                mToevoegen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!mProduct.getText().toString().isEmpty()){
                            Toast.makeText(BoodschappenlijstActivity.this, "Product toegevoegd", Toast.LENGTH_SHORT).show();
                            dref.push().setValue(mProduct.getText().toString());
                        }
                        else{
                            Toast.makeText(BoodschappenlijstActivity.this, "Vul een product in", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
                
            }
        });

        dref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String string = dataSnapshot.getValue(String.class);
                arrayList.add(string);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
