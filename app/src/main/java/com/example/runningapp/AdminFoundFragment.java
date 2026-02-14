package com.example.runningapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.runningapp.adapters.ItemAdapter;
import com.example.runningapp.models.ItemModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdminFoundFragment extends Fragment {

    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private List<ItemModel> ItemsList;
    private List<ItemModel> filteredList; // List to store filtered items

    public AdminFoundFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_found, container, false);

        // Initialize views
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize the list of items
        ItemsList = new ArrayList<>();
        filteredList = new ArrayList<>();
        itemAdapter = new ItemAdapter(getContext(), filteredList, AdminFoundItemDetail.class);
        recyclerView.setAdapter(itemAdapter);

        // Set up SearchView
        SearchView searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterItems(newText); // Filter items based on search text
                return false;
            }
        });

        // Fetch found items
        fetchFoundItems();

        return view;
    }

    private void fetchFoundItems() {
        String URL = "http://10.0.2.2/Projectfinal/api/AdminFound/GetAllFoundItems";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response.length() > 0) {
                            try {
                                parseFoundItems(response); // Parse all items
                            } catch (JSONException e) {
                                Toast.makeText(getContext(), "Error parsing data: " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "No found items.", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "No Found items: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(getContext()).add(request);
    }

    private void parseFoundItems(JSONArray response) throws JSONException {
        ItemsList.clear(); // Clear previous data

        // Loop through the response and add each item to the list
        for (int i = 0; i < response.length(); i++) {
            JSONObject item = response.getJSONObject(i);
            int itemid = item.optInt("ItemId", -1);
            String category = item.optString("CategoryName", "N/A");
            String Date = item.optString("FoundDate", "Unknown");
            String Location = item.optString("FoundLocation", "Unknown");
            String Person = item.optString("FoundBy", "Unknown");
            String imageBase64 = item.optString("Image", null);  // Image as Base64 string
            Integer Money = item.optInt("Price", 0);

            String Urgent="False";

            String Nominate=null;

            JSONArray attributesArray = item.optJSONArray("Attributes");
            StringBuilder attributes = new StringBuilder();
            if (attributesArray != null) {
                for (int j = 0; j < attributesArray.length(); j++) {
                    JSONObject attribute = attributesArray.getJSONObject(j);
                    attributes.append(attribute.getString("AttributeName"))
                            .append(":            ")
                            .append(attribute.getString("AttributeValue"))
                            .append("\n");
                }
            }

            Bitmap decodedBitmap = null;
            if (imageBase64 != null && !imageBase64.isEmpty()) {
                byte[] decodedString = Base64.decode(imageBase64, Base64.DEFAULT);
                decodedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            }

            // Add item to the list
            ItemModel itemModel = new ItemModel(itemid, category, Date, Location, Person,Urgent, Money,Nominate, attributes.toString(), decodedBitmap);
            ItemsList.add(itemModel);
        }

        // Initially show all items in the filtered list
        filteredList.addAll(ItemsList);
        itemAdapter.notifyDataSetChanged();
    }

    private void filterItems(String query) {
        filteredList.clear();

        if (TextUtils.isEmpty(query)) {
            filteredList.addAll(ItemsList); // If query is empty, show all items
        } else {
            for (ItemModel item : ItemsList) {
                // Filter based on item attributes (like category, date, location, etc.)
                if (item.getCategory().toLowerCase().contains(query.toLowerCase()) ||
                        item.getLocation().toLowerCase().contains(query.toLowerCase()) ||
                        item.getDate().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(item);
                }
            }
        }

        itemAdapter.notifyDataSetChanged(); // Notify adapter about changes
    }
}
