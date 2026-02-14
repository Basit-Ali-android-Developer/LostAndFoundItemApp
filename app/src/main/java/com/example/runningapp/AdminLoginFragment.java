package com.example.runningapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class AdminLoginFragment extends Fragment {

    private EditText Name, APassword;

    public AdminLoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_login, container, false);

        // Initialize EditText fields
        Name= view.findViewById(R.id.Name);
        APassword = view.findViewById(R.id.APassword);



        return view;
    }

    // Public methods to get the data from EditText fields
    public String getName() {
        return Name.getText().toString().trim();
    }

    public String getAPassword() {
        return APassword.getText().toString().trim();
    }


}