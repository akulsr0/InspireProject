package com.akul.inspire;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class DashboardFragment extends Fragment
{

    View view;
    EditText getq;
    Spinner qcategory;
    Button postq;

    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    String uid;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        getq = view.findViewById(R.id.getQuote);
        qcategory = view.findViewById(R.id.postCategory);
        postq = view.findViewById(R.id.postQuote);


        postq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mAuth = FirebaseAuth.getInstance();
                uid = mAuth.getCurrentUser().getUid();

                mDatabase = FirebaseDatabase.getInstance();
                mRef = mDatabase.getReference("Quotes/");


                final String quote = getq.getText().toString();
                final String qc = qcategory.getSelectedItem().toString();

                if(quote.isEmpty())
                {
                    getq.setError("Can't be empty, dude!");
                    getq.requestFocus();
                    return;
                }

                if(qcategory.getSelectedItem().equals("Choose category"))
                {
                    Toast.makeText(getContext(),"Choose any category",Toast.LENGTH_SHORT).show();
                    return;
                }

                HashMap <String,String> datamap = new HashMap<String, String>();
                datamap.put("Category",qc);
                datamap.put("Quote",quote);


                mRef.push().setValue(datamap);




                Toast.makeText(getContext(),"Post Published",Toast.LENGTH_SHORT).show();
            }
        });






        return view;
    }

}
