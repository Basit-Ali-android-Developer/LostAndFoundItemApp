package com.example.runningapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class LostAttributeFragment extends Fragment {

    private EditText Lost_location, Lost_Date, award,Nominate;

   // RadioButton radioButton1;

    public LostAttributeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lost_attribute, container, false);

        // Initialize EditText fields
      //  RadioGroup radioButton1 = view.findViewById(R.id.radioButton1);
        Lost_location= view.findViewById(R.id.Llocation);
        Lost_Date = view.findViewById(R.id.LDate);
        award= view.findViewById(R.id.AD);
        Nominate= view.findViewById(R.id.Nominate);


        return view;
    }

    // Public methods to get the data from EditText fields
    public String getLost_location() {
        return Lost_location.getText().toString().trim();
    }

    public String getLost_Date() {
        return Lost_Date.getText().toString().trim();
    }

    public String getaward() {
        return award.getText().toString().trim();
    }
    public String getNominate() {
        return Nominate.getText().toString().trim();
    }

}