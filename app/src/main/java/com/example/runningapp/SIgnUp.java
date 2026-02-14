package com.example.runningapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SIgnUp extends AppCompatActivity {

    //--------------initialize all textboxes and buttons-----------------------------------------------------
     private TextView signinSUbtn;
     private EditText Name, Registraton_no, Password, Contact_no, Email;
    private Button signUpbtn;

    private RequestQueue requestQueue;

    //-------------------------------------------------------------------------------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        //--code when user mistakenly open signup screen and want to go to login screen-----------------------------

        signinSUbtn = findViewById(R.id.signinSUbtn);
        signinSUbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SIgnUp.this,Login.class);
                startActivity(intent);
            }
        });


        //code when user create new account and press sign up btn and then go to login screen-----------------------

        signUpbtn = findViewById(R.id.signUpbtn);
        signUpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getinput();

                Name.setText("");
                Registraton_no.setText("");
                Password.setText("");
                Contact_no.setText("");
                Email.setText("");


            }
        });



    //-----code for sign up----------------------------------------------------------------------------------------------


        // Initialize EditText fields
        Name = findViewById(R.id.Name);
        Registraton_no = findViewById(R.id.Registraton_no);
        Password = findViewById(R.id.Password);
        Contact_no = findViewById(R.id.Contact_no);
        Email = findViewById(R.id.Email);


        // Initialize Volley request queue
        requestQueue =Volley.newRequestQueue(getApplicationContext());

        // Get input values












            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



    }

    private void getinput() {

        String name = Name.getText().toString().trim();
        String regNo = Registraton_no.getText().toString().trim();
        String password = Password.getText().toString().trim();
        String contactNo = Contact_no.getText().toString().trim();
        String email = Email.getText().toString().trim();
        String role="User";

        // Validate inputs
        if (name.isEmpty() || regNo.isEmpty() || password.isEmpty() || contactNo.isEmpty() || email.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
        } else {
            // Call method to send data to API
            signUpUser(name, regNo, password, contactNo, email,role);
        }


    }

    private void signUpUser(String name, String regNo, String password, String contactNo, String email,String role) {
        String url = "http://10.0.2.2/Projectfinal/api/Signup/SignUp";

        // Create JSON object with input data
        JSONObject jsonBody = new JSONObject();
        try {

            jsonBody.put("Registration_No", regNo);
            jsonBody.put("Username", name);
            jsonBody.put("Password", password);
            jsonBody.put("Contact_No", contactNo); // Correct key
            jsonBody.put("Email", email);
            jsonBody.put("Role", role);
            jsonBody.put("Claim_Count", 0);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Create a JsonObjectRequest for the sign-up request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle a successful response
                        Toast.makeText(getApplicationContext(), "Sign up successful!", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(SIgnUp.this,Login.class);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error response
                        Toast.makeText(getApplicationContext(), "Sign up failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Add the request to the Volley request queue
        requestQueue.add(jsonObjectRequest);










    }
}