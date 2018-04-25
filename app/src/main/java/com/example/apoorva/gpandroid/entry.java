package com.example.apoorva.gpandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class entry extends AppCompatActivity {
    FirebaseAuth fbAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        fbAuth = FirebaseAuth.getInstance();
    }

    public void signOut(View view) {
        fbAuth.signOut();
        startActivity(new Intent(entry.this, signup.class));
    }
}
