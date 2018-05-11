package com.example.apoorva.gpandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
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

public class signup extends AppCompatActivity {
    private static final String TAG = "PhoneAuth";
    private EditText mEmail;
    private EditText mPassword;
    private FirebaseAuth fbAuth;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    public static EditText name;

    private EditText email,  pass;
    private Button googleMaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        if(getSupportActionBar() != null){

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        email = (EditText) findViewById(R.id.e1);
        name = (EditText) findViewById(R.id.name);
        pass = (EditText) findViewById(R.id.e2);
        googleMaps = (Button)findViewById(R.id.sign_up);

        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.btn_email_create_account).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount(email.getText().toString(), pass.getText().toString());
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null)
            startActivity(new Intent(signup.this, MapsActivity.class));
    }

    //    @Override
//    public void onClick(View view) {
//        int i = view.getId();
//        Log.d("click","create");
//        if (i == R.id.btn_email_create_account) {
//            createAccount(email.getText().toString(), pass.getText().toString());
//        } else if (i == R.id.btn_verify_email) {
//            sendEmailVerification();
//        }
//    }
    private void createAccount(String email, String password) {
        Log.e(TAG, "createAccount:" + email);
        if (!validateForm(email, password)) {
            return;
        }
        Log.d("ap", "here");
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.e(TAG, "createAccount: Success!");
                            sendEmailVerification();

                            // update UI with the signed-in user's information
                            //FirebaseUser user = mAuth.getCurrentUser();
                            //startActivity(new Intent(signup.this, exit.class));
                        } else {
                            Log.e(TAG, "createAccount: Fail!", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void sendEmailVerification() {
        // Disable Verify Email button
        //findViewById(R.id.btn_verify_email).setEnabled(false);

        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Re-enable Verify Email button
                        // findViewById(R.id.btn_verify_email).setEnabled(true);

                        if (task.isSuccessful()) {
                            Log.d("email", "sent");
                            startActivity(new Intent(signup.this, login.class));
                            Toast.makeText(getApplicationContext(), "Verification email sent to " + user.getEmail(), Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "sendEmailVerification failed!", task.getException());
                            Toast.makeText(getApplicationContext(), "Failed to send verification email.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            finish();}
        return super.onOptionsItemSelected(item);
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

}