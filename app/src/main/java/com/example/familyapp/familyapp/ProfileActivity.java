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

    String name;// = "Kevin";
    String email;
    String lidid;// = "-L2HGsNMR46_Qk5BTqTH";
    String familyid;// = "-L2GyIAvj5tTQQPUY31j";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        intent = getIntent();
        familyid = intent.getStringExtra(HomeActivity.FAMILY_ID);
        lidid = intent.getStringExtra(HomeActivity.LID_ID);

        mAuth = FirebaseAuth.getInstance();
        email = mAuth.getCurrentUser().getEmail();

        System.out.println("email: " + email);
        System.out.println("lidid: " + lidid);
        System.out.println("familyid: " + familyid);

        databaseReference = FirebaseDatabase.getInstance().getReference("Leden").child(familyid);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot idSnapshot : dataSnapshot.getChildren()){
                    name = idSnapshot.child("naam").getValue(String.class);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        TextView txtNaam = findViewById(R.id.profilename);
        TextView txtEmail = findViewById(R.id.profileemail);
        EditText etxtFamID = findViewById(R.id.profilefamilyid);

        System.out.println("naam: " + name);

        txtNaam.setText(name);
        txtEmail.setText(email);
        etxtFamID.setText(familyid);
    }
}
