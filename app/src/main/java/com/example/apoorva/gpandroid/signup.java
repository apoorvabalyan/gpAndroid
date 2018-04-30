package com.example.apoorva.gpandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class signup extends AppCompatActivity implements
        View.OnClickListener {
    private static final String TAG = "PhoneAuth";
    private EditText mEmail;
    private EditText mPassword;
    private FirebaseAuth fbAuth;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private EditText email, name, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        email = (EditText)findViewById(R.id.e1);
        name = (EditText)findViewById(R.id.name);
        pass = (EditText)findViewById(R.id.e2);

        mAuth = FirebaseAuth.getInstance();
    }






    /*
        *************************************FIREBASE CODE*******************************************
         */
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null)
         startActivity(new Intent(signup.this, exit.class));
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();

        if (i == R.id.btn_email_create_account) {
            createAccount(email.getText().toString(), pass.getText().toString());
        } else if (i == R.id.btn_verify_email) {
            sendEmailVerification();
        }
    }
    private void createAccount(String email, String password) {
        Log.e(TAG, "createAccount:" + email);
        if (!validateForm(email, password)) {
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.e(TAG, "createAccount: Success!");

                            // update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(signup.this, exit.class));
                        } else {
                            Log.e(TAG, "createAccount: Fail!", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void sendEmailVerification() {
        // Disable Verify Email button
        findViewById(R.id.btn_verify_email).setEnabled(false);

        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Re-enable Verify Email button
                        findViewById(R.id.btn_verify_email).setEnabled(true);

                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Verification email sent to " + user.getEmail(), Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "sendEmailVerification failed!", task.getException());
                            Toast.makeText(getApplicationContext(), "Failed to send verification email.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private boolean validateForm(String email, String password) {

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
         /*
    ********************************************************CODE ENDS HERE**********************************************
     */

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
        String email;
        email = mEmail.getText().toString();
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