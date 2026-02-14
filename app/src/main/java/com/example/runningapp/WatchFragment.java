package com.example.runningapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


public class WatchFragment extends Fragment {

    private EditText Watch_Brand, Watch_Model,Watch_Strap,Watch_Movement,Watch_Color;


    public WatchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_watch, container, false);

        // Initialize EditText fields
        Watch_Brand = view.findViewById(R.id.Wat_Brand);
        Watch_Model = view.findViewById(R.id.Wat_Model);
        Watch_Strap = view.findViewById(R.id.Wat_Strap);
        Watch_Movement = view.findViewById(R.id.Wat_Movement);
        Watch_Color = view.findViewById(R.id.Wat_Color);

        return view;
    }

    // Public methods to get the data from EditText fields
    public String getBrand() {
        return Watch_Brand.getText().toString().trim();
    }

    public String getModel() {
        return Watch_Model.getText().toString().trim();
    }

    public String getStrap() {
        return Watch_Strap.getText().toString().trim();
    }

    public String getMovement() {
        return Watch_Movement.getText().toString().trim();
    }
    public String getcolor() {
        return Watch_Color.getText().toString().trim();
    }



}