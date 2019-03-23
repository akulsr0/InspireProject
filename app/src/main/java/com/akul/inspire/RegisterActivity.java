package com.akul.inspire;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
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
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity
{

    ActionBar mbar;
    EditText edtFullname, edtEmail, edtPhone, edtUsername, edtPassword;
    FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mbar = getSupportActionBar();
        mbar.hide();

        mAuth = FirebaseAuth.getInstance();

        edtFullname = findViewById(R.id.getFullName);
        edtEmail = findViewById(R.id.getEmail);
        edtPhone = findViewById(R.id.getPhone);
        edtUsername = findViewById(R.id.getUsername);
        edtPassword = findViewById(R.id.getPassword);

        progressBar = findViewById(R.id.pBar);
        progressBar.setVisibility(View.GONE);


    }

    public void registerUser(View view)
    {


        final String fullname = edtFullname.getText().toString();
        final String email = edtEmail.getText().toString();
        final String phone = edtPhone.getText().toString();
        final String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();

        if(fullname.isEmpty())
        {
            edtFullname.setError("Enter your name");
            edtFullname.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            edtEmail.setError("Enter valid Email address");
            edtEmail.requestFocus();
            return;
        }

        if(email.isEmpty())
        {
            edtEmail.setError("Enter Email address");
            edtEmail.requestFocus();
            return;
        }


        if (username.isEmpty())
        {
            edtUsername.setError("Enter desired username");
            edtUsername.requestFocus();
            return;
        }


        if(phone.length()<10)
        {
            edtPhone.setError("Enter valid phone number");
            edtPhone.requestFocus();
            return;
        }



        if(password.isEmpty())
        {
            edtPassword.setError("Enter password");
            edtPassword.requestFocus();
            return;
        }

        if(password.length()<6)
        {
            edtPassword.setError("Weak password");
            edtPassword.requestFocus();
            return;
        }


        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {

                            User user = new User(
                                    fullname,
                                    email,
                                    username,
                                    phone
                            );

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user);


                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "Registered Successfully",Toast.LENGTH_SHORT).show();
                            finish();
                            Intent i = new Intent(RegisterActivity.this,UserActivity.class);
                            startActivity(i);

                        }
                        else {
                            progressBar.setVisibility(View.GONE);
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

    }
}
