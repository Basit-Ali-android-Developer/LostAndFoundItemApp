package com.example.runningapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


public class ChargerFragment extends Fragment {

    private EditText Charger_devicetype,Charger_Brand,Charger_Connector,Charger_color;

    public ChargerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_charger, container, false);

        // Initialize EditText fields
        Charger_devicetype= view.findViewById(R.id.Ch_DeviceType);
        Charger_Brand= view.findViewById(R.id.Ch_Brand);
        Charger_Connector= view.findViewById(R.id.Ch_ConnectorType);
        Charger_color = view.findViewById(R.id.Ch_Color);


        return view;
    }

    // Public methods to get the data from EditText fields
    public String getdevicetype() {
        return Charger_devicetype.getText().toString().trim();
    }

    public String getBrand() {
        return Charger_Brand.getText().toString().trim();
    }

    public String getConnector() {
        return Charger_Connector.getText().toString().trim();
    }

    public String getcolor() {
        return   Charger_color.getText().toString().trim();
    }


}