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
    String familyid = "-L2GyIAvj5tTQQPUY31j";

    public final static String FAMILY_ID = "id";

    DatabaseReference databaseFamily;
    DatabaseReference databaseLeden;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        databaseFamily = FirebaseDatabase.getInstance().getReference("FamilyGroup");

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
                startActivity(new Intent(this, JoinActivity.class));
                break;
        }
    }

    private void CreateFamily() {
        String familyname = inputfamily.getText().toString().trim();

        if(familyname.isEmpty()){
            inputfamily.setError("Please enter a FamilyGroupName");
            inputfamily.requestFocus();
            return;
        }
        else{
            familyid = databaseFamily.push().getKey();
            FamilyGroup familyGroup = new FamilyGroup(familyid, familyname);

            Intent intent = getIntent();
            String lidid = intent.getStringExtra(SignUpActivity.LEDEN_ID);
            String lidname = intent.getStringExtra(SignUpActivity.LEDEN_NAAM);
            String lidemail = intent.getStringExtra(SignUpActivity.LEDEN_EMAIL);
            Leden lid = new Leden(lidid, lidname, lidemail);

            databaseFamily.child(familyid).setValue(familyGroup);

            databaseLeden = FirebaseDatabase.getInstance().getReference("Leden").child("-L2GyIAvj5tTQQPUY31j");
            databaseLeden.child(lidid).removeValue();

            databaseLeden = FirebaseDatabase.getInstance().getReference("Leden").child(familyid);
            databaseLeden.child(lidid).setValue(lid);

            intent = new Intent(this, HomeActivity.class);
            intent.putExtra(FAMILY_ID, familyid);

            startActivity(intent);
        }
    }
}
