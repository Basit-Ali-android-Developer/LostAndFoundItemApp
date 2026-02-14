package com.example.runningapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.runningapp.adapters.studentAuctionAdapter;
import com.example.runningapp.models.studentAuctionmodel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AuctionFragment extends Fragment {

    private RecyclerView recyclerView;
    private studentAuctionAdapter studentAuctionAdapter;
    private List<studentAuctionmodel> auctionList = new ArrayList<>();

    public AuctionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auction, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize Adapter and set to RecyclerView
        studentAuctionAdapter = new studentAuctionAdapter(auctionList, getContext());
        recyclerView.setAdapter(studentAuctionAdapter);


        // Fetch Auction Items
        fetchAuctionItems();

        return view;
    }

    private void fetchAuctionItems() {
        String URL = "http://10.0.2.2/Projectfinal/api/Auction/GetAllAuctionItems";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("API Response", "Response: " + response.toString());
                        if (response.length() > 0) {
                            try {
                                parseAuctionItems(response); // Parse all items
                            } catch (JSONException e) {
                                Log.e("JSON Parsing Error", "Error parsing JSON: " + e.getMessage(), e);
                                Toast.makeText(getContext(), "Error parsing data: " + e.getMessage(), Toast.LENGTH_LONG).show();
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
                        if (error.networkResponse != null) {
                            String errorMsg = new String(error.networkResponse.data);
                            Log.e("Volley Error", "Code: " + error.networkResponse.statusCode + ", Data: " + errorMsg);
                        }
                        Toast.makeText(getContext(), "No items in Auction: " + error.getMessage(), Toast.LENGTH_LONG).show();
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

        studentAuctionAdapter.notifyDataSetChanged();
    }
}
