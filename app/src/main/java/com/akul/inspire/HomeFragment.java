package com.akul.inspire;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dingmouren.layoutmanagergroup.skidright.SkidRightLayoutManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    View view;

    private RecyclerView postRecycler;
    QuoteAdapter adapter;
    List<Quote> quoteList;

    private SkidRightLayoutManager mSkidRightLayoutManager;



    DatabaseReference mDatabase;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference("Quotes");
        mDatabase.keepSynced(true);


        mSkidRightLayoutManager = new SkidRightLayoutManager(1.5f, 0.85f);


        postRecycler = view.findViewById(R.id.myRecycler);
        postRecycler.setHasFixedSize(true);
        postRecycler.setLayoutManager(mSkidRightLayoutManager);

        quoteList = new ArrayList<>();

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    for(DataSnapshot quoteSnapshot : dataSnapshot.getChildren()){
                        Quote quote1 = quoteSnapshot.getValue(Quote.class);
                        quoteList.add(quote1);
                    }

                    adapter = new QuoteAdapter(getContext(), quoteList);
                    postRecycler.setAdapter(adapter);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }

}