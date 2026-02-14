package com.example.runningapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdminDeliveredItemDetails extends AppCompatActivity {
    ImageButton backbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_delivered_item_details);



//        // Initialize views
        TextView categoryTextView = findViewById(R.id.categoryTextView);

        TextView lostbyTextView = findViewById(R.id.lostbyTextView);
        TextView lostLocationTextView = findViewById(R.id.lostLocationTextView);
        TextView lostdateTextView = findViewById(R.id.lostdateTextView);


        TextView FoundbyTextView = findViewById(R.id.FoundbyTextView);
        TextView foundlocationTextView = findViewById(R.id.foundlocationTextView);
        TextView founddateTextView = findViewById(R.id.founddateTextView);

        TextView TypeTextView = findViewById(R.id.typeTextView);
        TextView deliveredDateTextView = findViewById(R.id.delivereddateTextView);

        TextView attributesTextView = findViewById(R.id.SpecificationTextView);

        ImageView itemImageView = findViewById(R.id.itemImageView);


        String category = getIntent().getStringExtra("category");

        String lostDate = getIntent().getStringExtra("lostDate");
        String lostLocation = getIntent().getStringExtra("lostLocation");
        String LostBy = getIntent().getStringExtra("LostBy");


        String FoundDate = getIntent().getStringExtra("FoundDate");
        String foundLocation = getIntent().getStringExtra("foundLocation");
        String FoundBy = getIntent().getStringExtra("FoundBy");

        String Delivereddate = getIntent().getStringExtra("Delivered_date");
        String type = getIntent().getStringExtra("Type");




        String attributes = getIntent().getStringExtra("attributes");

        byte[] byteArray = getIntent().getByteArrayExtra("image");



        // Set the data to the views
        categoryTextView.setText(category);

        lostbyTextView.setText(LostBy);
        lostLocationTextView.setText(lostLocation);
        lostdateTextView.setText(lostDate);



        FoundbyTextView.setText(FoundBy);
        foundlocationTextView.setText(foundLocation);
        founddateTextView.setText(FoundDate);

        deliveredDateTextView.setText(Delivereddate);
        TypeTextView.setText(type);







        attributesTextView.setText(attributes);


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


                Intent intent = new Intent(AdminDeliveredItemDetails.this, AdminHomeFinal.class);
                intent.putExtra("FragmentToLoad", "AdminDeliveredFragment");  // You can change the fragment name here
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