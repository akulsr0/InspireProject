package com.akul.inspire;


import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {

    View view;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef,iRef;
    String uid;
    EditText ufname, uemail, uphone, uname;
    Button btnsave;
    
    ImageView editDP;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference,aRef;
    Uri mDPuri;
    public static final int SELECTED=100;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        uid = firebaseAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference("Users/"+uid+"/");
        iRef = FirebaseDatabase.getInstance().getReference("ProfileImages/"+uid+"/");

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference("ProfileImages/");
        aRef = firebaseStorage.getReference("ProfileImage/"+uid+"/");


        ufname = view.findViewById(R.id.getfullname);
        uemail = view.findViewById(R.id.getemail);
        uphone = view.findViewById(R.id.getphone);
        uname = view.findViewById(R.id.getusername);
        
        
        editDP = view.findViewById(R.id.editdp);
        editDP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                chooseImg();
            }
        });
        
        

        btnsave = view.findViewById(R.id.btn_save);
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveDp();

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

    private String getFileExtension(Uri uri){
        ContentResolver cr = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    private void saveDp()
    {

        if(mDPuri!=null) {

            final StorageReference fileRef = storageReference.child(uid+"."+getFileExtension(mDPuri));
            fileRef.putFile(mDPuri)
                    .continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                        {
                            if(!task.isSuccessful())
                            {
                                throw task.getException();
                            }
                            return fileRef.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    Uri downuri = task.getResult();
                    String url = downuri.toString();
                    UploadDp uploadDp = new UploadDp(url);
                    iRef.setValue(uploadDp);
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    })
            ;
        }
        else {
            Toast.makeText(getContext(),"No image selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void chooseImg()
    {
        Intent picPick = new Intent();
        picPick.setType("image/*");
        picPick.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(picPick,SELECTED);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SELECTED && resultCode == RESULT_OK && data!=null && data.getData()!=null)
        {
            mDPuri = data.getData();

            Picasso.get().load(mDPuri).into(editDP);
        }


    }


    @Override
    public void onStart() {
        super.onStart();

        iRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("imgUrl").getValue()==null)
                {
                    editDP.setImageResource(R.drawable.editdp);
                }
                else {
                    String url = dataSnapshot.child("imgUrl").getValue().toString();
                    Picasso.get().load(url).into(editDP);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }


}
