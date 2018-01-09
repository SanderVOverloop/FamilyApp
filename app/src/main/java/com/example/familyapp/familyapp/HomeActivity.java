package com.example.familyapp.familyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    public final static String FAMILY_ID = "fam_id";
    public final static String LID_ID = "lid_id";

    FirebaseAuth mAuth;

    private String email;
    private String lidid;
    private String familyid;
    private Intent intent;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.CustomToolbar);
        setSupportActionBar(toolbar);


        mAuth = FirebaseAuth.getInstance();
        email = mAuth.getCurrentUser().getEmail();
        System.out.println("acc email : " + email);

        databaseReference = FirebaseDatabase.getInstance().getReference("Leden");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot idSnapshot : dataSnapshot.getChildren()){
                    String data = idSnapshot.getKey();
                    for (DataSnapshot snapshot : idSnapshot.getChildren()){
                        String datamail = snapshot.child("email").getValue(String.class);
                        //System.out.println("email : " + datamail);
                        if(datamail.equals(email)){
                            familyid = data;
                            lidid = snapshot.getKey();
                            System.out.println("key : " + data);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings_profiel:
                intent = new Intent(this, ProfileActivity.class);
                intent.putExtra(FAMILY_ID, familyid);
                intent.putExtra(LID_ID, lidid);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.settings_uitloggen:
                mAuth.getInstance().signOut();
                startActivity(new Intent(this, MainActivity.class));
                break;
            default:
                //error
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickKomenEten(View view){
        startActivity(new Intent(this, KomenEtenActivity.class));
    }

    public void onClickBoodschappenLijst(View view){
        intent = new Intent(this, BoodschappenlijstActivity.class);
        intent.putExtra(FAMILY_ID, familyid);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
