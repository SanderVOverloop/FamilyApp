package com.example.familyapp.familyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    public final static String FAMILY_ID = "id";

    FirebaseAuth mAuth;

    private String email;
    private String lidid;
    private String id;
    private Intent intent;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        email = mAuth.getCurrentUser().getEmail();
        System.out.println("acc email : " + email);

        databaseReference = FirebaseDatabase.getInstance().getReference("Leden");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot idSnapshot : dataSnapshot.getChildren()){
                    String data1 = idSnapshot.getKey();
                    //System.out.println("key : " + data1);
                    for (DataSnapshot snapshot : idSnapshot.getChildren()){
                        String data2 = snapshot.child("email").getValue(String.class);
                        //System.out.println("email : " + data2);
                        if(data2.equals(email)){
                            id = data1;
                            //System.out.println("Yeah");
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
    public void onClickKomenEten(View view){
        startActivity(new Intent(this, KomenEtenActivity.class));
    }

    public void onClickBoodschappenLijst(View view){
        intent = new Intent(this, BoodschappenlijstActivity.class);
        intent.putExtra(FAMILY_ID, id);
        startActivity(intent);
    }
}
