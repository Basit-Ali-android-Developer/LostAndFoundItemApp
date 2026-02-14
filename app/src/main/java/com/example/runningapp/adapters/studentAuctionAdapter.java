package com.example.runningapp.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.runningapp.R;
import com.example.runningapp.models.studentAuctionmodel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class studentAuctionAdapter extends RecyclerView.Adapter<studentAuctionAdapter.ItemViewHolder> {

    private final List<studentAuctionmodel> auctionList;
    private final Context context; // Add context to the adapter

    public studentAuctionAdapter(List<studentAuctionmodel> auctionList, Context context) {
        this.auctionList = auctionList;
        this.context = context; // Initialize context
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.studentauctionlayout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        studentAuctionmodel item = auctionList.get(position);

        // Set Image
        if (item.getImage() != null) {
            holder.itemImageView.setImageBitmap(item.getImage());
        } else {
            holder.itemImageView.setImageResource(R.drawable.ic_placeholder);
        }

        // Set Text
        int item_id = item.getItemId();
        holder.categoryTextView.setText(item.getCategory());
        holder.specificationTextView.setText(item.getAttributes());
        holder.bidByTextView.setText(item.getMaximumBidder());
        holder.maximumBidTextView.setText(String.valueOf(item.getMaximumBid()));  // Display latest bid

        // Get registration number of the user from shared preferences
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        String regNo = sharedPreferences.getString("RegNo", "Unknown User");

        holder.saveBidButton.setOnClickListener(view -> {
            // Get bid price from the EditText
            String priceText = holder.userBidEditText.getText().toString().trim();
            if (priceText.isEmpty()) {
                Toast.makeText(context, "Please enter a bid amount.", Toast.LENGTH_SHORT).show();
                return;
            }

            int price;
            try {
                price = Integer.parseInt(priceText);
            } catch (NumberFormatException e) {
                Toast.makeText(context, "Invalid bid amount.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Call AddBid with the entered details
            AddBid(item_id, regNo, price, position);

            holder.userBidEditText.setText("");
        });
    }

    @Override
    public int getItemCount() {
        return auctionList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImageView;
        TextView specificationTextView, bidByTextView, maximumBidTextView,categoryTextView;
        EditText userBidEditText;
        Button saveBidButton;

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemImageView = itemView.findViewById(R.id.itemImageView);
            categoryTextView= itemView.findViewById(R.id.categoryTextView);
            specificationTextView = itemView.findViewById(R.id.SpecificationTextView);
            bidByTextView = itemView.findViewById(R.id.BidbyTextView);
            maximumBidTextView = itemView.findViewById(R.id.maximumbidTextView);
            userBidEditText = itemView.findViewById(R.id.userBidEditText);
            saveBidButton = itemView.findViewById(R.id.saveBidButton);
        }
    }

    // Method to handle adding a bid
    private void AddBid(int item_id, String Bid_by, int Bid_price, int position) {
        String url = "http://10.0.2.2/Projectfinal/api/Bidding/AddBid";
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("item_id", item_id);
            requestBody.put("Bid_by", Bid_by);
            requestBody.put("Bid_price", Bid_price);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                response -> {
                    // This will be executed if the request is successful
                    try {
                        // Directly check for the "Message" field in the response
                        if (response.has("Message")) {
                            String message = response.getString("Message");
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

                            // Update the bid in the auctionList
                            auctionList.get(position).setMaximumBid(Bid_price); // Update the maximum bid
                            auctionList.get(position).setMaximumBidder(Bid_by); // Up

                            // Notify the adapter that the item at position has changed
                            notifyItemChanged(position);  // This will trigger onBindViewHolder() again for this item
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context, "Error parsing response", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    // This will be executed if there is any error
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        try {
                            // Convert the error response to a String
                            String errorResponse = new String(error.networkResponse.data);
                            JSONObject errorJson = new JSONObject(errorResponse);
                            // Display the "Message" field from the error response
                            if (errorJson.has("Message")) {
                                String errorMessage = errorJson.getString("Message");
                                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                            } else {
                                // If no "Message" field is found, show a generic error message
                                Toast.makeText(context, "Bid not stored", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Error parsing error response", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Handle case where there is no network response or data
                        Toast.makeText(context, "Network error", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(context).add(request);
    }

}
