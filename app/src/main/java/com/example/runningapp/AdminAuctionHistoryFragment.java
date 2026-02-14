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

import com.example.runningapp.adapters.AuctionHistoryAdapter;
import com.example.runningapp.models.AuctionHistoryModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdminAuctionHistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private AuctionHistoryAdapter AuctionHistoryAdapter;
    private List<AuctionHistoryModel> ItemsList;



    public AdminAuctionHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_auction_history, container, false);

        // Initialize the button






        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize the list of items
        ItemsList = new ArrayList<>();
        AuctionHistoryAdapter = new AuctionHistoryAdapter(getContext(), ItemsList,AdminAuctionHistoryDetailsActivity.class);
        recyclerView.setAdapter(AuctionHistoryAdapter);

        fetchAuctionHistoryItems();

        return view;
    }


    private void fetchAuctionHistoryItems() {

        String URL = "http://10.0.2.2/Projectfinal/api/AuctionHistory/GetAllHistoryItems";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("API Response", "Response: " + response.toString());
                        if (response.length() > 0) {
                            try {
                                parseLostItems(response); // Parse all items
                            } catch (JSONException e) {
                                Log.e("JSON Parsing Error", "Error parsing JSON: " + e.getMessage(), e);
                                Toast.makeText(getContext(), "Error parsing data: " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "No items in Auction History.", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getContext(), "No items in Auction History: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(getContext()).add(request);
    }

    private void parseLostItems(JSONArray response) throws JSONException {
        ItemsList.clear(); // Clear previous data

        for (int i = 0; i < response.length(); i++) {
            JSONObject item = response.getJSONObject(i);

            int itemId = item.optInt("ItemId", -1);
            String category = item.optString("CategoryName", "N/A");
            String foundDate = item.optString("FoundDate", "N/A");
            String foundLocation = item.optString("FoundLocation", "N/A");
            String foundBy = item.optString("FoundBy", "N/A");
            int estimatedPrice = item.optInt("EstimatedPrice", -1);
            String startDate = item.optString("StartDate", "N/A");
            String endDate = item.optString("EndDate", "N/A");
            int FinalBidPrice = item.optInt("FinalBidPrice", 0);
            String SoldTo = item.optString("SoldTo", "null");
            String SoldDate = item.optString("SoldDate", "N/A");









            JSONArray attributesArray = item.optJSONArray("Attributes");
            StringBuilder attributes = new StringBuilder();
            if (attributesArray != null) {
                for (int j = 0; j < attributesArray.length(); j++) {
                    JSONObject attribute = attributesArray.getJSONObject(j);
                    attributes.append(attribute.getString("AttributeName"))
                            .append(": ")
                            .append(attribute.getString("AttributeValue"))
                            .append("\n");
                }
            }

            String imageBase64 = item.optString("Image", null);
            Bitmap decodedBitmap = null;
            if (imageBase64 != null && !imageBase64.isEmpty()) {
                byte[] decodedString = Base64.decode(imageBase64, Base64.DEFAULT);
                decodedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            }

            ItemsList.add(0,new AuctionHistoryModel(
                    itemId, category, foundDate, foundLocation, foundBy,
                    estimatedPrice, startDate, endDate,  FinalBidPrice, SoldTo,SoldDate,
                    attributes.toString(), decodedBitmap
            ));
        }

        AuctionHistoryAdapter.notifyDataSetChanged();
    }



}


