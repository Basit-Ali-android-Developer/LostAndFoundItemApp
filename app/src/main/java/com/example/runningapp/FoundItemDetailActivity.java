package com.example.runningapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.runningapp.models.ItemModel;

import java.util.List;

public class FoundItemDetailActivity extends AppCompatActivity {

    ImageButton backbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_found_item_detail);

// Initialize views
        TextView categoryTextView = findViewById(R.id.categoryTextView);
        TextView dateTextView = findViewById(R.id.dateTextView);
        TextView locationTextView = findViewById(R.id.locationTextView);
        TextView personTextView = findViewById(R.id.personTextView);
        TextView moneyTextView = findViewById(R.id.moneyTextView);
        TextView attributesTextView = findViewById(R.id.SpecificationTextView);

        ImageView itemImageView = findViewById(R.id.itemImageView);

        // Get data from the intent
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
                Intent intent=new Intent(FoundItemDetailActivity.this,HomeFinal.class);
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