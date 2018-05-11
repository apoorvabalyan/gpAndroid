package com.example.apoorva.gpandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Already_signed extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText mEmail;
    private EditText mPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_already_signed);


        if(getSupportActionBar() != null){

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        mAuth = FirebaseAuth.getInstance();
        //Get the data
        mEmail = findViewById(R.id.emailL);
        mPassword = findViewById(R.id.passL);
        findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString().trim();
                final String password = mPassword.getText().toString().trim();
                if(TextUtils.isEmpty(email))
                {
                    Toast.makeText(Already_signed.this,"Enter Email Address",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(password))
                {
                    Toast.makeText(Already_signed.this,"Enter Password",Toast.LENGTH_LONG).show();
                    return;
                }
                Log.d("Login","Before Logging in");
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(Already_signed.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            // there was an error
                            if (password.length() < 6) {
                                Toast.makeText(Already_signed.this, "Wrong credentials", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(Already_signed.this, "Not a valid account", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Log.d("Login","Logged in");
                            checkEmailVerification();
                            //startActivity(new Intent(login.this, exit.class));
                            finish();
                        }
                    }
                });
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            finish();}
        return super.onOptionsItemSelected(item);
    }
    private void checkEmailVerification(){
        FirebaseUser firebaseUser = mAuth.getInstance().getCurrentUser();
        Boolean emailflag = firebaseUser.isEmailVerified();
        if(emailflag){
            finish();
            startActivity(new Intent(Already_signed.this, MapsActivity.class));
            finish();
        }else{
            Toast.makeText(this, "Verify your email", Toast.LENGTH_SHORT).show();
            mAuth.signOut();
        }
    }
}
