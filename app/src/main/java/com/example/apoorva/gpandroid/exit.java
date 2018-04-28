package com.example.apoorva.gpandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class exit extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit);
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    mAuth.signOut();
                    Log.d("Sign out","signing out");
                    startActivity(new Intent(exit.this,loginOrSignup.class));
            }
        });
    }
}
