package com.example.runningapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


import com.example.runningapp.adapters.StudentnotificationAdapter;
import com.example.runningapp.adapters.notificationAdapter;

import com.example.runningapp.models.notificationModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Notification extends AppCompatActivity {

    ImageButton back_button;
    private RecyclerView recyclerView;
    private notificationAdapter notificationAdapter;
    private List<notificationModel> notificationList = new ArrayList<>();

    private TextView clearnotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notification);


        recyclerView = findViewById(R.id.NotificationsrecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the list of items

        notificationAdapter = new notificationAdapter(notificationList);
        recyclerView.setAdapter(notificationAdapter);




        fetchNotification();

        back_button = findViewById(R.id.AdminNotification_back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(Notification.this, AdminHomeFinal.class);
                startActivity(intent);

            }
        });

        clearnotification = findViewById(R.id.AdminclearAllNotifications);
        clearnotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  ChangeStatusRead();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void fetchNotification() {


        String URL = "http://10.0.2.2/Projectfinal/api/AdminNotification/GetUnreadNotifications";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("API Response", "Response: " + response.toString());
                        if (response.length() > 0) {
                            try {
                                parseNotification(response); // Parse all items
                            } catch (JSONException e) {
                                Log.e("JSON Parsing Error", "Error parsing JSON: " + e.getMessage(), e);

                            }
                        } else {

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", "Error: " + error.getMessage());
                        if (error.networkResponse != null) {
                            String errorMsg = new String(error.networkResponse.data);
                            Log.e("Volley Error", "Code: " + error.networkResponse.statusCode + ", Data: " + errorMsg);
                        }

                    }
                });

        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(this).add(request);
    }

    private void parseNotification(JSONArray response) throws JSONException {
        notificationList.clear(); // Clear previous data

        // Loop through the response and add each item to the start of the list
        for (int i = 0; i < response.length(); i++) {
            JSONObject item = response.getJSONObject(i);

            Integer notification_id = item.optInt("notification_id", 0);
            String message = item.optString("message", "Unknown");
            String notification_time = item.optString("notification_time", "Unknown");

            // Add the notification to the start of the list
            notificationList.add(0, new notificationModel(notification_id, message, notification_time));
        }

        notificationAdapter.notifyDataSetChanged();  // Notify the adapter about data changes
    }


    private void ChangeStatusRead() {
        // Immediately clear the notifications list and notify the adapter to refresh the UI
        notificationList.clear();
        notificationAdapter.notifyDataSetChanged();  // This will remove all notifications from the RecyclerView

        // Log the API call for debugging purposes
        Log.d("ChangeStatus", "Marking all unread notifications as read.");

        // Make the API request to mark all notifications as read on the server
        String URL = "http://10.0.2.2/Projectfinal/api/AdminNotificationHandler/MarkAsRead";

        // Since no body is required, use JsonObjectRequest with a null body
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, null,
                response -> {
                    // Handle success response
                    Log.d("API Response", "Response: " + response.toString());
                    Toast.makeText(Notification.this, "All unread notifications marked as read", Toast.LENGTH_SHORT).show();
                },
                error -> {
                    // Handle error response
                    Log.e("Volley Error", "Error: " + error.getMessage());
                    if (error.networkResponse != null) {
                        String errorMsg = new String(error.networkResponse.data);
                        Log.e("Volley Error", "Code: " + error.networkResponse.statusCode + ", Data: " + errorMsg);
                    }
                    Toast.makeText(Notification.this, "Failed to mark notifications as read", Toast.LENGTH_SHORT).show();
                }) {

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8"; // Set the content type to JSON
            }
        };

        // Set a retry policy
        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Add the request to the queue
        Volley.newRequestQueue(this).add(request);
    }

}