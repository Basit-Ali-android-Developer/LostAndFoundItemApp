package com.example.runningapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class FoundAttributesFragment extends Fragment {

    private EditText Found_location, Found_Date, Estimated_price;

    public FoundAttributesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_found_attributes, container, false);

        // Initialize EditText fields
        Found_location= view.findViewById(R.id.Flocation);
        Found_Date = view.findViewById(R.id.FDate);
        Estimated_price= view.findViewById(R.id.EP);


        return view;
    }

    // Public methods to get the data from EditText fields
    public String getFound_location() {
        return Found_location.getText().toString().trim();
    }

    public String getFound_Date() {
        return Found_Date.getText().toString().trim();
    }

    public String getEstimated_price() {
        return Estimated_price.getText().toString().trim();
    }
}