package com.example.familyapp.familyapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    EditText inputName, inputEmail, inputPassword;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    DatabaseReference databaseReference;
    public final static String LEDEN_ID = "id";
    public final static String LEDEN_NAAM = "name";
    public final static String LEDEN_EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPassword = (EditText) findViewById(R.id.input_password);
        progressBar = (ProgressBar) findViewById(R.id.signupprogressBar);
        inputName = (EditText) findViewById(R.id.input_name);

        databaseReference = FirebaseDatabase.getInstance().getReference("Leden").child("-L2GyIAvj5tTQQPUY31j");

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.btn_signup).setOnClickListener(this);
        findViewById(R.id.returnToLogin).setOnClickListener(this);
    }

    //Check if user is currently signed in
    /*@Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }*/

    private void RegisterUser(){
        final String name = inputName.getText().toString().trim();
        final String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        if(name.isEmpty()){
            inputName.setError("Please enter a name");
            inputName.requestFocus();
            return;
        }

        if(email.isEmpty()){
            inputEmail.setError("Email is required");
            inputEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            inputEmail.setError("Please enter a valid email");
            inputEmail.requestFocus();
            return;
        }


        if(password.isEmpty()){
            inputPassword.setError("Password is required");
            inputPassword.requestFocus();
            return;
        }

        if(password.length() < 6){
            inputPassword.setError("Minimum lenght of password should be 6");
            inputPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);

                if(task.isSuccessful()){

                    //word lid aangemaakt in de groep default?
                    String id = databaseReference.push().getKey();
                    Leden lid = new Leden(id,name,email);
                    databaseReference.child(id).setValue(lid);

                    Toast.makeText(getApplicationContext(), "User Registered Succesful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, SetupActivity.class);
                    intent.putExtra(LEDEN_ID, id);
                    intent.putExtra(LEDEN_NAAM, name);
                    intent.putExtra(LEDEN_EMAIL, email);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else {
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(getApplicationContext(), "This email is already in use", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_signup:
                RegisterUser();
                break;
            case R.id.returnToLogin:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}
