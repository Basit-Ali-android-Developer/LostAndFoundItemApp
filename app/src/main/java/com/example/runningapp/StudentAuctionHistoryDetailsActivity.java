package com.example.runningapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class StudentAuctionHistoryDetailsActivity extends AppCompatActivity {
    ImageButton backbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_auction_history_details);




        TextView categoryTextView = findViewById(R.id.categoryTextView);
        TextView foundbyTextView = findViewById(R.id.foundbyTextView);
        TextView foundlocationTextView = findViewById(R.id.foundLocationTextView);
        TextView founddateTextView = findViewById(R.id.founddateTextView);
        TextView estimatedpriceTextView = findViewById(R.id.estimatedpriceTextView);
        TextView startdateTextView = findViewById(R.id.startdateTextView);
        TextView enddateTextView = findViewById(R.id.enddateTextView);
        TextView finalbidTextView = findViewById(R.id.finalbidTextView);
        TextView soldtoTextView = findViewById(R.id.soldtoTextView);
        TextView solddateTextView = findViewById(R.id.solddateTextView);
        TextView attributeTextView = findViewById(R.id.attributeTextView);
//
        ImageView itemImageView = findViewById(R.id.itemImageView);






        String category = getIntent().getStringExtra("category");
        String foundDate = getIntent().getStringExtra("foundDate");
        String foundLocation = getIntent().getStringExtra("foundLocation");
        String foundBy = getIntent().getStringExtra("foundBy");

        int startingPrice = getIntent().getIntExtra("startingPrice", 0);


        String startingdate = getIntent().getStringExtra("startingdate");
        String enddate = getIntent().getStringExtra("enddate");

        int soldPrice = getIntent().getIntExtra("soldPrice", 0);

        String soldto = getIntent().getStringExtra("soldto");
        String solddate = getIntent().getStringExtra("solddate");

        String attributes = getIntent().getStringExtra("attributes");

        byte[] byteArray = getIntent().getByteArrayExtra("image");



        // Set the data to the views
        categoryTextView.setText(category);
        foundbyTextView.setText(foundBy);
        foundlocationTextView.setText(foundLocation);
        founddateTextView.setText(foundDate);
        estimatedpriceTextView.setText(String.valueOf(startingPrice));

        startdateTextView.setText(startingdate);
        enddateTextView.setText(enddate);
        finalbidTextView.setText(String.valueOf(soldPrice));
        soldtoTextView.setText(soldto);
        solddateTextView.setText(solddate);
        attributeTextView.setText(attributes);

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
                Intent intent=new Intent(StudentAuctionHistoryDetailsActivity.this,HomeFinal.class);
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