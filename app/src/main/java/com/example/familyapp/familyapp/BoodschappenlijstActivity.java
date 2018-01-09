package com.example.familyapp.familyapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoodschappenlijstActivity extends AppCompatActivity {

    private DatabaseReference dref;

    SwipeMenuListView listview;
    private EditText editText;
    private Button button;
    private ImageView imageView;
    private ArrayList<String> arrayList = new ArrayList<>();
    Map<String, String> keyList = new HashMap<String,String>();
    private ArrayAdapter<String> adapter;

    private String id = "-L2GyIAvj5tTQQPUY31j";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boodschappenlijst);


        Intent intent = getIntent();
        id = intent.getStringExtra(HomeActivity.FAMILY_ID);
        dref = FirebaseDatabase.getInstance().getReference("Boodschappenlijst").child(id);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);

        listview = (SwipeMenuListView)findViewById(R.id.listview);
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
                String key = dataSnapshot.getKey();
                String string = dataSnapshot.getValue(String.class);
                keyList.put(string, key);
                arrayList.add(string);

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                arrayList.remove(dataSnapshot.getValue().toString());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(190);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_action_name);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        listview.setMenuCreator(creator);

        listview.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        String removeValue = keyList.get(arrayList.get(position));
                        dref.child(removeValue).removeValue();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }
}
