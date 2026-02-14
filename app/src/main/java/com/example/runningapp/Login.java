package com.example.runningapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;

public class Login extends AppCompatActivity {

    private TextView signupSIbtn;
    private Button SignInbtn;
    private RequestQueue requestQueue;
    String title, password, role;

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.adminAnduserloginContainer, fragment);
        ft.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signupSIbtn = findViewById(R.id.signupSIbtn);
        SignInbtn = findViewById(R.id.SignInbtn);

        loadFragment(new UserLoginFragment());

        RadioGroup radioGroup = findViewById(R.id.roleRadioButton);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.User) {
                    loadFragment(new UserLoginFragment());
                } else if (checkedId == R.id.Admin) {
                    loadFragment(new AdminLoginFragment());
                }
            }
        });

        testNetworkConnection();

        signupSIbtn.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, SIgnUp.class);
            startActivity(intent);
        });

        SignInbtn.setOnClickListener(v -> {

            Intent intent = new Intent(Login.this, AdminHomeFinal.class);
            startActivity(intent);

            Fragment curFragment = getSupportFragmentManager().findFragmentById(R.id.adminAnduserloginContainer);
            if (curFragment instanceof UserLoginFragment) {
                UserLoginFragment lFragment = (UserLoginFragment) curFragment;
                title = lFragment.getregno();  // User's Registration No.
                password = lFragment.getUPassword();
                role = "User";  // User role
            } else if (curFragment instanceof AdminLoginFragment) {
                AdminLoginFragment fAttributesFragment = (AdminLoginFragment) curFragment;
                title = fAttributesFragment.getName();  // Admin's Username
                password = fAttributesFragment.getAPassword();
                role = "Admin";  // Admin role
            }

            // Log the details for debugging
            Log.d("Login", "Title: " + title + ", Password: " + password + ", Role: " + role);

            loginUser(title, password, role);
        });
    }

    private void loginUser(String username, String password, String role) {

        if(role == "User"){

            Intent intent = new Intent(Login.this, AdminHomeFinal.class);
            startActivity(intent);

        }else{

            Intent intent = new Intent(Login.this, HomeFinal.class);
            startActivity(intent);
        }

        // Create the JSON body for the login request
//        JSONObject jsonBody = new JSONObject();
//        try {
//            jsonBody.put("Role", role);
//            jsonBody.put("title", username);  // User's RegNo or Admin's Username
//            jsonBody.put("Password", password);
//            Log.d("LoginRequest", jsonBody.toString());  // Log request payload
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        // Define your login API endpoint
//        String loginUrl = "http://10.0.2.2/Projectfinal/api/Login/LogIn";  // Update for real device
//
//        // Create a JsonObjectRequest for the login request
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
//                Request.Method.POST,
//                loginUrl,
//                jsonBody,
//                response -> {
//                    try {
//                        // Extract the message and RegNo from the response
//                        String message = response.getString("Message");
//                        String Role = response.getString("Role");
//                        String regNo = response.getString("RegNo");
//                        String Username = response.getString("Username");
//
//                        if (message.equals("Admin login successful") || message.equals("User login successful")) {
//                            // Store RegNo and other details in SharedPreferences
//                            SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
//                            SharedPreferences.Editor editor = sharedPreferences.edit();
//                            editor.putString("Drawername", Username);
//                            editor.putString("DrawerDetail", regNo);
//                            editor.putString("RegNo", regNo);
//                            editor.apply();  // Save changes
//
//                            // Navigate to the correct home activity
//                            Intent intent;
//                            if (Role.equals("Admin")) {
//                                intent = new Intent(Login.this, AdminHomeFinal.class);  // Admin Home Activity
//                            } else {
//                                intent = new Intent(Login.this, HomeFinal.class);  // User Home Activity
//                            }
//                            startActivity(intent);
//                            finish();  // Optionally, finish this activity to prevent going back to login screen
//
//                            // Show the login success message
//                            Toast.makeText(Login.this, message, Toast.LENGTH_SHORT).show();
//                        } else {
//                            // Handle unsuccessful login
//                            Toast.makeText(Login.this, "Invalid login credentials", Toast.LENGTH_SHORT).show();
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                        Log.e("LoginError", "Error parsing response: " + e.getMessage());
//                        Toast.makeText(Login.this, "An error occurred while processing the response", Toast.LENGTH_SHORT).show();
//                    }
//                },
//                error -> {
//                    if (error.networkResponse != null) {
//                        int statusCode = error.networkResponse.statusCode;
//                        Log.e("LoginError", "Status Code: " + statusCode);
//                        String response = new String(error.networkResponse.data);
//                        Log.e("LoginError", "Response: " + response);
//
//                        if (statusCode == 401) {
//                            Toast.makeText(Login.this, "Invalid login credentials", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(Login.this, "An error occurred. Please try again.", Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//                        Log.e("LoginError", "Network Error: " + error.getMessage());
//                        Toast.makeText(Login.this, "Network error. Please check your connection.", Toast.LENGTH_SHORT).show();
//                    }
//                }
//        );
//
//        // Add the request to the Volley queue
//        if (requestQueue == null) {
//            requestQueue = Volley.newRequestQueue(this);
//        }
//        requestQueue.add(jsonObjectRequest);
    }

    private void testNetworkConnection() {
        new Thread(() -> {
            try {
                InetAddress address = InetAddress.getByName("10.0.2.2");
                Log.d("NetworkTest", "Reachable: " + address.isReachable(2000));
            } catch (Exception e) {
                Log.e("NetworkTest", "Error: " + e.getMessage());
            }
        }).start();
    }
}
