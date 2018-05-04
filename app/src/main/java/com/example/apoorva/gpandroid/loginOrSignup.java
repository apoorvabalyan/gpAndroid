package com.example.apoorva.gpandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginOrSignup extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText email, pass;
    private static final String TAG = "FirebaseEmailPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_signup);
        //Checks whether already logged in or not.
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(loginOrSignup.this, MapsActivity.class));
            finish();
        }
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(loginOrSignup.this,login.class));
            }
        });
    }

  public void sign_up(View view)
    {
     startActivity(new Intent(loginOrSignup.this, signup.class));
    }


    public void signphone(View view)
    {
        startActivity(new Intent(loginOrSignup.this, phone.class));
    }
}
