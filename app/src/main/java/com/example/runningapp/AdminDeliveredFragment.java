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
import org.json.JSONException;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.runningapp.models.DeliveredModel;
import com.example.runningapp.adapters.DeliveredAdapter;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdminDeliveredFragment extends Fragment {

    private RecyclerView recyclerView;
    private DeliveredAdapter deliveredAdapter;
    private List<DeliveredModel> ItemsList;




    public AdminDeliveredFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_delivered, container, false);

        // Initialize views
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize the list of items
        ItemsList = new ArrayList<>();
        deliveredAdapter = new DeliveredAdapter(getContext(), ItemsList,AdminDeliveredItemDetails.class);
        recyclerView.setAdapter(deliveredAdapter);

        // Fetch lost items
        fetchDeliveredItems();

        return view;
    }

    private void fetchDeliveredItems() {


        String URL = "http://10.0.2.2/Projectfinal/api/DelieverItem/GetAllDeliveredItems";

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
                            Toast.makeText(getContext(), "No Delivered items.", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getContext(), "No Delivered items : " + error.getMessage(), Toast.LENGTH_LONG).show();
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

        // Loop through the response and add each item to the list
        for (int i = 0; i < response.length(); i++) {
            JSONObject item = response.getJSONObject(i);
            //
            String category = item.optString("CategoryName", "N/A");
            String lostdate = item.optString("LostDate", "Unknown");
            String lostlocation = item.optString("LostLocation", "Unknown");
            String lostby = item.optString("LostBy", "Unknown");
            String imageBase64 = item.optString("Image", null);  // Image as Base64 string
            Integer award = item.has("Award") && !item.isNull("Award") ? item.getInt("Award") : null;
            String founddate = item.optString("FoundDate", "Unknown");
            String foundlocation = item.optString("FoundLocation", "Unknown");
            String foundby = item.optString("FoundBy", "Unknown");
            int price = item.optInt("EstimatedPrice", 0);
            String deliveredDate = item.optString("DeliveredDate", "Unknown");
            String type = item.optString("Type", "Unknown");


            JSONArray attributesArray = item.optJSONArray("Attributes");
            StringBuilder attributes = new StringBuilder();
            if (attributesArray != null) {
                for (int j = 0; j < attributesArray.length(); j++) {
                    JSONObject attribute = attributesArray.getJSONObject(j);
                    attributes.append(attribute.getString("AttributeName"))
                            .append(" :              ")
                            .append(attribute.getString("AttributeValue"))
                            .append("\n");
                }
            }

            // Decode the Base64 image if available
            Bitmap decodedBitmap = null;
            if (imageBase64 != null && !imageBase64.isEmpty()) {
                byte[] decodedString = Base64.decode(imageBase64, Base64.DEFAULT);
                decodedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            }
            ItemsList.add(0,new DeliveredModel(category, lostdate, lostlocation,lostby, award,founddate,foundlocation,foundby,
                    price,deliveredDate,type, attributes.toString(), decodedBitmap)); }



        deliveredAdapter.notifyDataSetChanged();  // Notify the adapter about data changes
    }
}
