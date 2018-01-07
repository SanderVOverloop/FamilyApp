package com.example.familyapp.familyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Set;

public class JoinActivity extends AppCompatActivity implements View.OnClickListener {
    EditText inputfamilyid;
    String familyid = "-L2GyIAvj5tTQQPUY31j";

    public final static String FAMILY_ID = "id";

    DatabaseReference databaseFamily;
    DatabaseReference databaseLeden;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        databaseFamily = FirebaseDatabase.getInstance().getReference("FamilyGroup");

        inputfamilyid = findViewById(R.id.input_familyid);

        findViewById(R.id.btn_joinfamily).setOnClickListener(this);
        findViewById(R.id.createfamily).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_joinfamily:
                JoinFamily();
                break;
            case R.id.createfamily:
                startActivity(new Intent(this, JoinActivity.class));
                break;
        }
    }

    private void JoinFamily() {
        familyid = inputfamilyid.getText().toString().trim();

        if(familyid.isEmpty()){
            inputfamilyid.setError("Please enter a FamilyGroupID");
            inputfamilyid.requestFocus();
            return;
        }
        else{

            Intent intent = getIntent();
            String lidid = intent.getStringExtra(SetupActivity.LEDEN_ID);
            String lidname = intent.getStringExtra(SignUpActivity.LEDEN_NAAM);
            String lidemail = intent.getStringExtra(SignUpActivity.LEDEN_EMAIL);
            Leden lid = new Leden(lidid, lidname, lidemail);

            databaseLeden = FirebaseDatabase.getInstance().getReference("Leden").child("-L2GyIAvj5tTQQPUY31j");
            databaseLeden.child(lidid).removeValue();

            databaseLeden = FirebaseDatabase.getInstance().getReference("Leden").child(familyid);
            databaseLeden.child(lidid).setValue(lid);

            intent = new Intent(this, HomeActivity.class);

            startActivity(intent);
        }
    }
}
