package com.example.runningapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.runningapp.adapters.StudentnotificationAdapter;
import com.example.runningapp.models.notificationModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StudentNotification extends AppCompatActivity {

    private RecyclerView recyclerView;
    private StudentnotificationAdapter adapter;
    private List<notificationModel> notificationList = new ArrayList<>();
    private ImageButton back_button;
    private TextView clearnotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_notification);

        recyclerView = findViewById(R.id.StudentNotificationsrecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new StudentnotificationAdapter(this, notificationList);
        recyclerView.setAdapter(adapter);

        back_button = findViewById(R.id.StudentNotification_back_button);
        back_button.setOnClickListener(view -> {
            Intent intent = new Intent(StudentNotification.this, HomeFinal.class);
            startActivity(intent);
        });
        SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
        String regNo = sharedPreferences.getString("RegNo", "");

        fetchNotification(regNo);

        clearnotification = findViewById(R.id.StudentclearAllNotifications);
        clearnotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllNotifications(regNo);

            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void fetchNotification(String regNo) {

        String url = "http://10.0.2.2/Projectfinal/api/StudentNotifiaction/GetNotifications?regNo=" + regNo;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    Log.d("API Response", response.toString());
                    parseNotification(response);
                },
                error -> Log.e("API Error", "Error fetching notifications: " + error.getMessage()));

        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(this).add(request);
    }

    private void parseNotification(JSONArray response) {
        try {
            notificationList.clear();
            for (int i = 0; i < response.length(); i++) {
                JSONObject item = response.getJSONObject(i);
                int notificationId = item.optInt("notification_id", 0);
                String message = item.optString("message", "Unknown");
                String time = item.optString("notification_time", "Unknown");

                notificationList.add(0,new notificationModel(notificationId, message, time));
            }
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            Log.e("Parse Error", "Error parsing notifications: " + e.getMessage());
        }
    }



    private void clearAllNotifications(String regNo) {
        // Immediately clear the notifications list and notify the adapter to refresh the UI
        notificationList.clear();
        adapter.notifyDataSetChanged();  // This will remove all notifications from the RecyclerView

        // API URL with Registration_No as a query parameter
        String URL = "http://10.0.2.2/Projectfinal/api/StudentNotificationHandler/ClearProcessingNotifications?regNo=" + regNo;

        // Log the API call for debugging purposes
        Log.d("ChangeStatus", "Clearing all 'Processing' notifications for user: " + regNo);

        // Since the API doesn't require a request body, use JsonObjectRequest with a null body
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, null,
                response -> {
                    // Handle success response
                    Log.d("API Response", "Response: " + response.toString());
                    Toast.makeText(StudentNotification.this, "All 'Processing' notifications cleared.", Toast.LENGTH_SHORT).show();
                },
                error -> {
                    // Handle error response
                    Log.e("Volley Error", "Error: " + error.getMessage());
                    if (error.networkResponse != null) {
                        String errorMsg = new String(error.networkResponse.data);
                        Log.e("Volley Error", "Code: " + error.networkResponse.statusCode + ", Data: " + errorMsg);
                    }
                    Toast.makeText(StudentNotification.this, "Failed to clear notifications.", Toast.LENGTH_SHORT).show();
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
