package com.example.runningapp;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class AdminProfileFragment extends Fragment {

    private EditText etRegNo, etUsername, etPassword, etContactNo, etEmail;
    private Button btnUpdate;

    public AdminProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_profile, container, false);

        // Initialize the EditTexts and Button
        etRegNo = view.findViewById(R.id.Registraton_no);
        etUsername = view.findViewById(R.id.Name);
        etPassword = view.findViewById(R.id.Password);
        etContactNo = view.findViewById(R.id.Contact_no);
        etEmail = view.findViewById(R.id.Email);
        btnUpdate = view.findViewById(R.id.Updatebtn);

        // Fetch and display user details
        fetchUserDetails();

        // Set click listener for the Update button
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserDetails();
            }
        });

        return view;
    }

    private void fetchUserDetails() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("UserDetails", getContext().MODE_PRIVATE);
        String regNo = sharedPreferences.getString("RegNo", "");

        String URL = "http://10.0.2.2/Projectfinal/api/Signup/GetUserInfo?regno=" + regNo;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null,
                response -> {
                    try {
                        etRegNo.setText(response.optString("Registration_No", ""));
                        etUsername.setText(response.optString("Username", ""));
                        etPassword.setText(response.optString("Password", ""));
                        etContactNo.setText(response.optString("Contact_No", ""));
                        etEmail.setText(response.optString("Email", ""));
                    } catch (Exception e) {
                        Log.e("ProfileFragment", "Error parsing user details: " + e.getMessage());
                    }
                },
                error -> Log.e("ProfileFragment", "Error fetching user details: " + error.getMessage()));

        Volley.newRequestQueue(getContext()).add(request);
    }

    private void updateUserDetails() {
        // Validate the input fields
        String regNo = etRegNo.getText().toString().trim();
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String contactNo = etContactNo.getText().toString().trim();
        String email = etEmail.getText().toString().trim();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserDetails", getContext().MODE_PRIVATE);
        String regno = sharedPreferences.getString("RegNo", "");

        if (regNo.isEmpty() || username.isEmpty() || password.isEmpty() || contactNo.isEmpty() || email.isEmpty()) {
            Toast.makeText(getContext(), "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        // Construct the API URL
        String URL = "http://10.0.2.2/Projectfinal/api/Signup/UpdateUserInfo";

        // Construct the JSON payload
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("Registration_No", regno); // Original RegNo
            jsonBody.put("NewRegistration_No", regNo); // Updated RegNo
            jsonBody.put("NewUsername", username);
            jsonBody.put("NewPassword", password);
            jsonBody.put("NewContact_No", contactNo);
            jsonBody.put("Email", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Use StringRequest to handle plain string response
        StringRequest request = new StringRequest(Request.Method.PUT, URL,
                response -> {
                    // Handle the plain string response
                    Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                },
                error -> {
                    // Handle errors
                    if (error.networkResponse != null) {
                        String errorMsg = new String(error.networkResponse.data);
                        Log.e("ProfileFragment", "Error Code: " + error.networkResponse.statusCode + ", Message: " + errorMsg);
                        Toast.makeText(getContext(), "Update failed: " + errorMsg, Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("ProfileFragment", "Unknown error: " + error.getMessage());
                        Toast.makeText(getContext(), "Update failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            public byte[] getBody() {
                return jsonBody.toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };

        // Add the request to the Volley queue
        Volley.newRequestQueue(getContext()).add(request);
    }



}
