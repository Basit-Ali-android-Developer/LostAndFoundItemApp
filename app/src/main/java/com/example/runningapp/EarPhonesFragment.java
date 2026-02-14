package com.example.runningapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


public class EarPhonesFragment extends Fragment {

    private EditText EarPhones_Brand,EarPhones_Type,EarPhones_ConnectorType,EarPhones_Color;

    public EarPhonesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ear_phones, container, false);

        // Initialize EditText fields
        EarPhones_Brand= view.findViewById(R.id.EP_Brand);
        EarPhones_Type= view.findViewById(R.id.EP_Type);
        EarPhones_ConnectorType= view.findViewById(R.id.EP_ConnectorType);
        EarPhones_Color = view.findViewById(R.id.EP_Color);


        return view;
    }

    // Public methods to get the data from EditText fields
    public String getbrand() {

        return  EarPhones_Brand.getText().toString().trim();
    }

    public String gettype() {

        return EarPhones_Type.getText().toString().trim();
    }

    public String getConnectorType() {
        return EarPhones_ConnectorType.getText().toString().trim();
    }

    public String getcolor() {
        return  EarPhones_Color.getText().toString().trim();
    }


}