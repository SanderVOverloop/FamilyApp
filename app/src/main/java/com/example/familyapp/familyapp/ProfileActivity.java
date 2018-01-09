package com.example.familyapp.familyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    FirebaseAuth mAuth;

    Intent intent;

    String name = "Kevin";
    String email;
    String lidid = "-L2HGsNMR46_Qk5BTqTH";
    String familyid = "-L2GyIAvj5tTQQPUY31j";

    TextView txtNaam;
    TextView txtEmail;
    EditText etxtFamID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        intent = getIntent();
        familyid = intent.getStringExtra(HomeActivity.FAMILY_ID);
        lidid = intent.getStringExtra(HomeActivity.LID_ID);

        txtNaam = findViewById(R.id.profilename);
        txtEmail = findViewById(R.id.profileemail);
        etxtFamID = findViewById(R.id.profilefamilyid);

        mAuth = FirebaseAuth.getInstance();
        email = mAuth.getCurrentUser().getEmail();

        databaseReference = FirebaseDatabase.getInstance().getReference("Leden").child(familyid);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot idSnapshot : dataSnapshot.getChildren()){
                    String data = idSnapshot.getKey();
                    System.out.println("ID: " + data);
                    String data2 = idSnapshot.child("naam").getValue(String.class);
                    System.out.println("Naam: " + data2);
                    if (data.equals(lidid)){
                        name = data2;
                        System.out.println("lol");
                        Display();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


    }

    public void Display(){
        txtNaam.setText(name);
        txtEmail.setText(email);
        etxtFamID.setText(familyid);
    }
}
