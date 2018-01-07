package com.example.familyapp.familyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SetupActivity extends AppCompatActivity implements View.OnClickListener {
    EditText inputfamily;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        databaseReference = FirebaseDatabase.getInstance().getReference("FamilyGroup");

        inputfamily = findViewById(R.id.input_family);

        findViewById(R.id.btn_createfamily).setOnClickListener(this);
        findViewById(R.id.joinfamily).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_createfamily:
                CreateFamily();
                break;
            case R.id.joinfamily:
                startActivity(new Intent(this, HomeActivity.class));
                break;
        }
    }

    private void CreateFamily() {
        String name = inputfamily.getText().toString().trim();

        if(name.isEmpty()){
            inputfamily.setError("Please enter a FamilyGroupName");
            inputfamily.requestFocus();
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
