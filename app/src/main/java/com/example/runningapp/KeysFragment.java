package com.example.runningapp;



import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


public class KeysFragment extends Fragment {

    private EditText Keys_Material,Keys_Type,Keys_identity;

    public KeysFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_keys, container, false);

        // Initialize EditText fields
        Keys_Material= view.findViewById(R.id.Ke_Material);
        Keys_Type= view.findViewById(R.id.Ke_Type);
        Keys_identity= view.findViewById(R.id.Ke_Idn);


        return view;
    }

    // Public methods to get the data from EditText fields
    public String getMaterial() {
        return Keys_Material.getText().toString().trim();
    }

    public String gettype() {
        return Keys_Type.getText().toString().trim();
    }

    public String gettidentity() {
        return Keys_identity.getText().toString().trim();
    }



}