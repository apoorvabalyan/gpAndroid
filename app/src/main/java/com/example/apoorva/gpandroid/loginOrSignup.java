package com.example.apoorva.gpandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class loginOrSignup extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_signup);
        /*
        *************************************FIREBASE CODE*******************************************
         */
        //Checks whether already logged in or not.
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(loginOrSignup.this, exit.class));
            finish();
        }
         /*
          ********************************************************CODE ENDS HERE**********************************************
         */
        findViewById(R.id.sign_inButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(loginOrSignup.this,signup.class));
            }
        });
    }
    public void signphone(View view)
    {
        startActivity(new Intent(loginOrSignup.this, phone.class));
    }
}
