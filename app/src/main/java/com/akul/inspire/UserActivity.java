package com.akul.inspire;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.LoginFilter;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserActivity extends AppCompatActivity
{

    ActionBar mBar;
    Animation fbtnopen, fbtnclose, rc, ranc;
    Boolean isOpen = false;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    String uID;

    EditText userfname, useremail, userphone, useruname;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        firebaseAuth = FirebaseAuth.getInstance();

        Fragment fr;
        fr = new HomeFragment();
        FragmentTransaction tr = getSupportFragmentManager().beginTransaction()
                .replace(R.id.userframe ,fr);
        tr.commit();

        mBar = getSupportActionBar();
        mBar.hide();



        uID = firebaseAuth.getCurrentUser().getUid();


        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Users/"+uID+"/");



        userfname = findViewById(R.id.getfullname);
        useremail = findViewById(R.id.getemail);
        userphone = findViewById(R.id.getphone);
        useruname = findViewById(R.id.getusername);

    }

    public void fragclick(View view)
    {
        Fragment f;


        if(view == findViewById(R.id.btn_profile))
        {
            f = new ProfileFragment();
        }
        else if(view == findViewById(R.id.btn_quote))
        {
            f = new DashboardFragment();
        }
        else
        {
            f = new HomeFragment();
        }


        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.setCustomAnimations(R.anim.fadein,R.anim.fadeout);
        t.replace(R.id.userframe ,f);
        t.commit();


    }


    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }



    }

    public void logoutclick(View view)
    {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this,MainActivity.class));
    }


}
