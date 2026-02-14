package com.example.runningapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


public class BagFragment extends Fragment {

    private EditText Bag_Brand,Bag_stuff, Bag_color, Bag_type;


    public BagFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bag, container, false);

        // Initialize EditText fields
        Bag_Brand = view.findViewById(R.id.B_Brand);
        Bag_stuff = view.findViewById(R.id.B_stuff);
        Bag_color = view.findViewById(R.id.B_Color);
        Bag_type= view.findViewById(R.id.B_Type);

        return view;
    }

    // Public methods to get the data from EditText fields
    public String getbrand() {
        return Bag_Brand.getText().toString().trim();
    }

    public String getStuff() {
        return Bag_stuff.getText().toString().trim();
    }

    public String getcolor() {
        return Bag_color.getText().toString().trim();
    }

    public String gettype() {
        return Bag_type.getText().toString().trim();
    }



}