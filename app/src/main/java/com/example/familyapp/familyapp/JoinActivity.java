package com.example.familyapp.familyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Set;

public class JoinActivity extends AppCompatActivity implements View.OnClickListener {
    EditText inputfamilyid;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        databaseReference = FirebaseDatabase.getInstance().getReference("FamilyGroup");

        inputfamilyid = findViewById(R.id.input_family);

        findViewById(R.id.btn_joinfamily).setOnClickListener(this);
        findViewById(R.id.createfamily).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_joinfamily:
                CreateFamily();
                break;
            case R.id.createfamily:
                startActivity(new Intent(this, SetupActivity.class));
                break;
        }
    }

    private void CreateFamily() {
        String name = inputfamilyid.getText().toString().trim();

        if(name.isEmpty()){
            inputfamilyid.setError("Please enter a FamilyGroupID");
            inputfamilyid.requestFocus();
            return;
        }
        else{
            String id = databaseReference.push().getKey();
            FamilyGroup familyGroup = new FamilyGroup(id, name);

            databaseReference.child(id).setValue(familyGroup);

            startActivity(new Intent(this, HomeActivity.class));
        }
    }
}
