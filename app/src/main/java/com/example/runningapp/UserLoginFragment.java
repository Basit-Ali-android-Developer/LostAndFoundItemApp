package com.example.runningapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class UserLoginFragment extends Fragment {

    private EditText regno, UPassword;

    public UserLoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_login, container, false);

        // Initialize EditText fields
        regno= view.findViewById(R.id.regno);
        UPassword = view.findViewById(R.id.UPassword);



        return view;
    }

    // Public methods to get the data from EditText fields
    public String getregno() {
        return regno.getText().toString().trim();
    }

    public String getUPassword() {
        return UPassword.getText().toString().trim();
    }


}