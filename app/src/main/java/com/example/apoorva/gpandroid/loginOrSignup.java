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
        email = (EditText) findViewById(R.id.sign_email);
        pass = (EditText) findViewById(R.id.sign_pass);
        /*
        *************************************FIREBASE CODE*******************************************
         */
        //Checks whether already logged in or not.
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(loginOrSignup.this, exit.class));
            finish();
        }
    }
         /*
          ********************************************************CODE ENDS HERE**********************************************
         */
  public void sign_up(View view)
    {
     startActivity(new Intent(loginOrSignup.this, signup.class));
    }

    public void login(View view)
    {
        signIn(email.getText().toString(), pass.getText().toString());
    }
    private void signIn(String email, String password) {
        Log.e(TAG, "signIn:" + email);
        if (!validateForm(email, password)) {
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.e(TAG, "signIn: Success!");

                            // update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(loginOrSignup.this, exit.class));
                        } else {
                            Log.e(TAG, "signIn: Fail!", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed!", Toast.LENGTH_SHORT).show();

                        }

                        if (!task.isSuccessful()) {

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

    public void signphone(View view)
    {
        startActivity(new Intent(loginOrSignup.this, phone.class));
    }
}
