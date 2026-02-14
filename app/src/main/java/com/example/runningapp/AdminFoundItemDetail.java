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

public class AdminFoundItemDetail extends AppCompatActivity {
    ImageButton backbutton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_found_item_detail);


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


                Intent intent = new Intent(AdminFoundItemDetail.this, AdminHomeFinal.class);
                intent.putExtra("FragmentToLoad", "AdminFoundFragment");  // You can change the fragment name here
                startActivity(intent);
            }
        });








        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }



}