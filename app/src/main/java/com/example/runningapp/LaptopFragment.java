package com.example.runningapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class LaptopFragment extends Fragment {

    private EditText LaptopBrand, LaptopModel, LaptopColor, LaptopStorage;

    public LaptopFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_laptop, container, false);

        // Initialize EditText fields
        LaptopBrand = view.findViewById(R.id.Lap_Brand);
        LaptopModel = view.findViewById(R.id.Lap_Model);
        LaptopColor = view.findViewById(R.id.Lap_color);
        LaptopStorage= view.findViewById(R.id.Lap_Storage);

        return view;
    }

    // Public methods to get the data from EditText fields
    public String getBrand() {
        return LaptopBrand.getText().toString().trim();
    }

    public String getModel() {
        return LaptopModel.getText().toString().trim();
    }

    public String getColor() {
        return LaptopColor.getText().toString().trim();
    }

    public String getStorage() {
        return LaptopStorage.getText().toString().trim();
    }



}