package com.akul.inspire;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity
{
    ActionBar bar;
    EditText userEmail, userPass;
    FirebaseAuth fAuth;
    private ProgressBar progressBar2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fAuth = FirebaseAuth.getInstance();

        bar = getSupportActionBar();
        bar.hide();

        progressBar2 = findViewById(R.id.prBar);
        progressBar2.setVisibility(View.GONE);

        userEmail = findViewById(R.id.username);
        userPass = findViewById(R.id.password);


    }

    public void loguser(View view)
    {
        String un = userEmail.getText().toString();
        String up = userPass.getText().toString();

        progressBar2.setVisibility(View.VISIBLE);


        fAuth.signInWithEmailAndPassword(un,up)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            finish();
                        Toast.makeText(getApplicationContext(),"Login Successfull", Toast.LENGTH_SHORT).show();
                        progressBar2.setVisibility(View.GONE);
                        Intent i = new Intent(LoginActivity.this,UserActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        }
                        else{

                        }
                    }
                });





    }


    @Override
    protected void onStart() {
        super.onStart();

        if (fAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(this, UserActivity.class));
        }
    }

}
