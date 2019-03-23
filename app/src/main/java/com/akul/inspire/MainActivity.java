package com.akul.inspire;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity
{

    Button btnLogin, btnRegister ,btnContinue;
    Animation anim;
    ActionBar bar;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fAuth = FirebaseAuth.getInstance();



        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);
        btnLogin.setVisibility(View.GONE);
        btnRegister.setVisibility(View.GONE);
        btnContinue = findViewById(R.id.btn_continue);
        bar = getSupportActionBar();
        bar.hide();

    }

    public void continueClicked(View view) {

        anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        btnContinue.startAnimation(anim);
        btnContinue.setVisibility(View.GONE);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                btnLogin.setVisibility(View.VISIBLE);
                btnRegister.setVisibility(View.VISIBLE);
            }
        }, 500);
    }

    public void loginClicked(View view)
    {
        finish();
        startActivity(new Intent(this,LoginActivity.class));
    }

    public void registerClicked(View view) {
        finish();
        startActivity(new Intent(this,RegisterActivity.class));
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
