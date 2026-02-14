package com.example.runningapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


public class MobileFragment extends Fragment {

    private EditText mobBrand, mobModel, mobColor, mobStorage;



    public MobileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mobile, container, false);

        // Initialize EditText fields
        mobBrand = view.findViewById(R.id.Mob_Brand);
        mobModel = view.findViewById(R.id.Mob_Model);
        mobColor = view.findViewById(R.id.Mob_color);
        mobStorage = view.findViewById(R.id.Mob_Storage);

        return view;
    }

    // Public methods to get the data from EditText fields
    public String getBrand() {
        return mobBrand.getText().toString().trim();
    }

    public String getModel() {
        return mobModel.getText().toString().trim();
    }

    public String getColor() {
        return mobColor.getText().toString().trim();
    }

    public String getStorage() {
        return mobStorage.getText().toString().trim();
    }

}