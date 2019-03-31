package com.akul.inspire;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;

// Created by Akul Srivastava
// Date: 21 March 2019

import android.view.View;
import android.view.ViewGroup;

// Created by Akul Srivastava
// Date: 21 March 2019

public class MenuFragment extends Fragment
{

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view =  inflater.inflate(R.layout.fragment_menu, container, false);


        return view;
    }

}

// Created by Akul Srivastava
// Date: 21 March 2019