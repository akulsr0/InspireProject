package com.akul.inspire;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    View view;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    String uid;
    EditText ufname, uemail, uphone, uname;
    Button btnsave;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        uid = firebaseAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference("Users/"+uid+"/");


        ufname = view.findViewById(R.id.getfullname);
        uemail = view.findViewById(R.id.getemail);
        uphone = view.findViewById(R.id.getphone);
        uname = view.findViewById(R.id.getusername);

        btnsave = view.findViewById(R.id.btn_save);
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                String ufn = ufname.getText().toString();
                String uun = uname.getText().toString();
                String up = uphone.getText().toString();
                String ue = uemail.getText().toString();

                mRef.child("fullname").setValue(ufn);
                mRef.child("username").setValue(uun);
                mRef.child("phonenumber").setValue(up);
                mRef.child("email").setValue(ue);

            }
        });


        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String fullname = dataSnapshot.child("fullname").getValue().toString();
                ufname.setText(fullname);

                String email = dataSnapshot.child("email").getValue().toString();
                uemail.setText(email);

                String phone = dataSnapshot.child("phonenumber").getValue().toString();
                uphone.setText(phone);

                String username = dataSnapshot.child("username").getValue().toString();
                uname.setText(username);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;

    }



}
