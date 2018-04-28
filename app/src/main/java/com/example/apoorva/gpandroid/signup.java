package com.example.apoorva.gpandroid;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class signup extends AppCompatActivity {
    private static final String TAG = "PhoneAuth";
    private EditText mEmail;
    private EditText mPassword;
    private FirebaseAuth fbAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        /*
        *************************************FIREBASE CODE*******************************************
         */
        fbAuth = FirebaseAuth.getInstance();
        //Get the data
        mEmail = findViewById(R.id.e1);
        mPassword = findViewById(R.id.e2);
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        //Click on the button
        findViewById(R.id.sign_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check whether the data is accurate
                if (!checkData()) {
                    Log.d(TAG, "Not correct details");
                    return;
                }
                //Create a user and sign in via firebase Auth
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();
                fbAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(signup.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(signup.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        }
                        else{
                            //If successful Store in the database also
                            onAuthSucess(task.getResult().getUser());
                        }
                    }
                });
            }
        });
         /*
    ********************************************************CODE ENDS HERE**********************************************
     */
    }

    //Firebase sign in success function
    private void onAuthSucess(FirebaseUser user){
        //Returns the name of the user from the email address
        String userName = getUserName(user.getEmail());
        //Write the user to the database
        writeNewUser(user.getUid(),userName,user.getEmail());
        startActivity(new Intent(signup.this,exit.class));
        finish();
    }
    //Firebase sign in:Gets the user name from email address
    private String getUserName(String email)
    {
        if(email.contains("@"))
        {
            return (email.split("@")[0]);
        }
        else
            return email;
    }
    //Firebase sign in: Writes the user info to database
    private void writeNewUser(String userId,String name,String email)
    {
        //Store the data as a user object in the database
        User user = new User(userId,name,email);
        mDatabase.child(userId).setValue(user);
    }
    //Firebase sign in: Checks the validity of the data
    private boolean checkData(){
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        Boolean result = true;
        if(TextUtils.isEmpty(email)){
            mEmail.setError("Required field");
            result = false;
        }
        if(TextUtils.isEmpty(password)){
            mPassword.setError("Required field");
            result = false;
        }
        if(password.length() < 6)
        {
            result = false;
            mPassword.setError("Length should be 6.");
        }
        return result;
    }
}
