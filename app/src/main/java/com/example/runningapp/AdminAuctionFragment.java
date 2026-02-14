package com.example.runningapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.example.runningapp.models.studentAuctionmodel;
import com.example.runningapp.adapters.adminAuctionAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdminAuctionFragment extends Fragment {

    FloatingActionButton newauction;

    private RecyclerView recyclerView;
    private adminAuctionAdapter adminAuctionAdapter;
    private List<studentAuctionmodel> auctionList = new ArrayList<>();



    public AdminAuctionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_auction, container, false);

        // Initialize the button
        newauction = view.findViewById(R.id.Add_button);

        // Set onClickListener for the button
        newauction.setOnClickListener(v -> {
            // Navigate to the new fragment
            NewAuctionItemsFragment newAuctionFragment = new NewAuctionItemsFragment();
            FragmentManager fragmentManager = getParentFragmentManager(); // Use childFragmentManager if dealing with nested fragments
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            // Replace the current fragment with the new fragment
            transaction.replace(R.id.Admincontentpart, newAuctionFragment); // Admincontentpart is the container ID in AdminHomeFinal
            transaction.addToBackStack(null); // Optional: Adds the transaction to the back stack
            transaction.commit();
        });



        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize Adapter and set to RecyclerView
        adminAuctionAdapter = new adminAuctionAdapter(auctionList);
        recyclerView.setAdapter(adminAuctionAdapter);

        // Fetch Auction Items
        fetchAuctionItems();

        handleExpiredAuctions();


        // Return the inflated view
        return view;
    }


    private void fetchAuctionItems() {
        String URL = "http://10.0.2.2/Projectfinal/api/Auction/GetAllAuctionItemsForAdmin";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("API Response", "Response: " + response.toString());
                        if (response.length() > 0) {
                            try {
                                parseAuctionItems(response);
                            } catch (JSONException e) {
                                Log.e("JSON Error", "Error parsing JSON: " + e.getMessage());

                            }
                        } else {
                            Toast.makeText(getContext(), "No items in Auction.", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", "Error: " + error.getMessage());
                        Toast.makeText(getContext(), "Failed to fetch auction items.", Toast.LENGTH_LONG).show();
                    }
                });

        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(getContext()).add(request);
    }

    private void parseAuctionItems(JSONArray response) throws JSONException {
        auctionList.clear();

        for (int i = 0; i < response.length(); i++) {
            JSONObject item = response.getJSONObject(i);

            int itemId = item.optInt("item_id", -1);
            String category = item.optString("CategoryName", "N/A");
            String foundDate = item.optString("found_date", "N/A");
            String foundLocation = item.optString("found_location", "N/A");
            String foundBy = item.optString("found_by", "N/A");
            int estimatedPrice = item.optInt("estimated_price", -1);
            String startDate = item.optString("start_date", "N/A");
            String endDate = item.optString("end_date", "N/A");
            String status = item.optString("Status", "N/A");
            int maximumBid = item.optInt("MaximumBid", 0);

            if (maximumBid==0){
                maximumBid = estimatedPrice;
            }


            String maximumBidder = item.optString("BidBy", "null");

            if(maximumBidder.equals("null")){
                maximumBidder="Not Bid Yet";

            }


            JSONArray attributesArray = item.optJSONArray("Attributes");
            StringBuilder attributes = new StringBuilder();
            if (attributesArray != null) {
                for (int j = 0; j < attributesArray.length(); j++) {
                    JSONObject attribute = attributesArray.getJSONObject(j);
                    attributes.append(attribute.getString("attribute_name"))
                            .append(": ")
                            .append(attribute.getString("attribute_value"))
                            .append("\n");
                }
            }

            String imageBase64 = item.optString("Image", null);
            Bitmap decodedBitmap = null;
            if (imageBase64 != null && !imageBase64.isEmpty()) {
                byte[] decodedString = Base64.decode(imageBase64, Base64.DEFAULT);
                decodedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            }

            auctionList.add(0,new studentAuctionmodel(
                    itemId, category, foundDate, foundLocation, foundBy,
                    estimatedPrice, startDate, endDate, status, maximumBid, maximumBidder,
                    attributes.toString(), decodedBitmap
            ));
        }

        adminAuctionAdapter.notifyDataSetChanged();
    }


    private void handleExpiredAuctions() {
        String URL = "http://10.0.2.2/Projectfinal/api/Auction/HandleExpiredAuctions";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Extract the message from the response
                            String message = response.getString("message");
                            Log.d("HandleExpiredAuctions", "Response: " + message);

                        } catch (JSONException e) {
                            Log.e("HandleExpiredAuctions", "JSON Parsing Error: " + e.getMessage());

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error responses, including server or network errors
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            try {
                                // Extract the error message if possible
                                String errorMessage = new String(error.networkResponse.data);
                                JSONObject errorObj = new JSONObject(errorMessage);
                                String message = errorObj.optString("message", "Unknown error occurred.");
                                Log.e("HandleExpiredAuctions", "Error Response: " + message);
                                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                Log.e("HandleExpiredAuctions", "Error Parsing Error Response: " + e.getMessage());
                                Toast.makeText(getContext(), "An error occurred.", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Log.e("HandleExpiredAuctions", "Network Error: " + error.getMessage());
                            Toast.makeText(getContext(), "Failed to connect to the server.", Toast.LENGTH_LONG).show();
                        }
                    }
                });

        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(getContext()).add(request);
    }

}


