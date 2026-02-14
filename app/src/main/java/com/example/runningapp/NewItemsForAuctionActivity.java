package com.example.runningapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class NewItemsForAuctionActivity extends AppCompatActivity {
    ImageButton backbutton;
    Button save;
    private EditText StartDate, EndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_items_for_auction);


// Initialize views
        TextView categoryTextView = findViewById(R.id.categoryTextView);
        TextView dateTextView = findViewById(R.id.dateTextView);
        TextView locationTextView = findViewById(R.id.locationTextView);
        TextView personTextView = findViewById(R.id.personTextView);
        TextView moneyTextView = findViewById(R.id.moneyTextView);
        TextView attributesTextView = findViewById(R.id.SpecificationTextView);

        ImageView itemImageView = findViewById(R.id.itemImageView);

        // Get data from the intent
        int item_id = getIntent().getIntExtra("ItemId", -1);
        String category = getIntent().getStringExtra("category");
        String date = getIntent().getStringExtra("date");
        String location = getIntent().getStringExtra("location");
        String person = getIntent().getStringExtra("person");
        String attributes = getIntent().getStringExtra("attributes");
        int award = getIntent().getIntExtra("money", 0);
        byte[] byteArray = getIntent().getByteArrayExtra("image");



        // Set the data to the views
        categoryTextView.setText(category);

        locationTextView.setText(location);
        dateTextView.setText(date);
        personTextView.setText(person);
        attributesTextView.setText(attributes);
        moneyTextView.setText(award == 0 ? "no Award" : String.valueOf(award));

        if (byteArray != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            itemImageView.setImageBitmap(bitmap);
        } else {
            itemImageView.setImageResource(R.drawable.ic_placeholder); // Fallback image
        }




        backbutton = findViewById(R.id.Activityback_button);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewItemsForAuctionActivity.this, AdminHomeFinal.class);
                intent.putExtra("FragmentToLoad", "AdminAuctionFragment");  // You can change the fragment name here
                startActivity(intent);
            }
        });




        save = findViewById(R.id.save_btn);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartDate = findViewById(R.id.StartDate);
                EndDate = findViewById(R.id.EndDate);

                // Get the start and end dates from the EditText
                String startDate = StartDate.getText().toString().trim();
                String endDate = EndDate.getText().toString().trim();

                // Get the item ID (passed via the Intent)
                int itemId = getIntent().getIntExtra("ItemId", -1);

                // Validate input fields
                if (startDate.isEmpty() || endDate.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Call method to send data to API
                    addItemToAuction(itemId, startDate, endDate);
                }
                StartDate.setText("");
                EndDate.setText("");

                Intent intent = new Intent(NewItemsForAuctionActivity.this, AdminHomeFinal.class);
                intent.putExtra("FragmentToLoad", "AdminAuctionFragment");  // You can change the fragment name here
                startActivity(intent);
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    private void addItemToAuction(int itemId, String startDate, String endDate) {
        String url = "http://10.0.2.2/Projectfinal/api/Auction/AddToAuction";

        // Ensure correct datetime format
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        try {
            // Convert input string to Date, then format it correctly
            String formattedStartDate = outputFormat.format(inputFormat.parse(startDate));
            String formattedEndDate = outputFormat.format(inputFormat.parse(endDate));

            // Create JSON object
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("itemId", itemId);
            jsonBody.put("startDate", formattedStartDate);
            jsonBody.put("endDate", formattedEndDate);
            jsonBody.put("status", "Active");

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    jsonBody,
                    response -> {
                        try {
                            String message = response.getString("message");
                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> {
                        Toast.makeText(this, "Failed to add item to auction", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
            );

            Volley.newRequestQueue(this).add(jsonObjectRequest);

        } catch (ParseException | JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Invalid date format", Toast.LENGTH_SHORT).show();
        }
    }
}