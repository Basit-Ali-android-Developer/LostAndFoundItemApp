package com.example.runningapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class GlassesFragment extends Fragment {

    private EditText Glasses_Brand,Glasses_Material,Glasses_Type,Glasses_Lenscolor,Glasses_Framecolor;



    public GlassesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_glasses, container, false);

        // Initialize EditText fields
        Glasses_Brand= view.findViewById(R.id.Gl_Brand);
        Glasses_Material= view.findViewById(R.id.Gl_Material);
        Glasses_Type= view.findViewById(R.id.Gl_Type);
        Glasses_Lenscolor = view.findViewById(R.id.Gl_LensColor);
        Glasses_Framecolor = view.findViewById(R.id.Gl_FrameColor);

        return view;
    }

    // Public methods to get the data from EditText fields
    public String getBrand() {
        return Glasses_Brand.getText().toString().trim();
    }

    public String getMaterial() {
        return Glasses_Material.getText().toString().trim();
    }

    public String gettype() {
        return Glasses_Type.getText().toString().trim();
    }

    public String getLenscolor() {
        return  Glasses_Lenscolor.getText().toString().trim();
    }
    public String getFramecolor() {
        return Glasses_Framecolor.getText().toString().trim();
    }

}