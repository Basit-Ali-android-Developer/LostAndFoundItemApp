package com.example.runningapp.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.runningapp.R;
import com.example.runningapp.models.notificationModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class StudentnotificationAdapter extends RecyclerView.Adapter<StudentnotificationAdapter.ItemViewHolder> {

    private List<notificationModel> notificationList;
    private final Context context;

    public StudentnotificationAdapter(Context context, List<notificationModel> notificationList) {
        this.context = context;
        this.notificationList = notificationList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_notification_items, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        notificationModel item = notificationList.get(position);

        String message = item.getmessage();

        if (message.contains("!")) {
            String[] parts = message.split("!");
            // Parse message parts
            String part1 = parts.length > 0 ? parts[0] : "";
            String part2 = parts.length > 1 ? parts[1] : "";
            String part3 = parts.length > 2 ? parts[2] : "";
            String part4 = parts.length > 3 ? parts[3] : "";
            String part5 = parts.length > 4 ? parts[4] : "";
            String part6 = parts.length > 5 ? parts[5] : "";
            String part7 = parts.length > 6 ? parts[6] : "";
            String part8 = parts.length > 7 ? parts[7] : "";
            String part9 = parts.length > 8 ? parts[8] : "";
            String part10 = parts.length > 9 ? parts[9] : "";
            String part11 = parts.length > 10 ? parts[10] : "";

            if ("Deliver".equalsIgnoreCase(part5.trim())) {
//
                holder.ClaimButton.setVisibility(View.VISIBLE);
                holder.rejectButton.setVisibility(View.VISIBLE);
                holder.itemImageView.setVisibility(View.VISIBLE);



                holder.Lost_id = Integer.parseInt(part1.trim());
                holder.Found_id = Integer.parseInt(part2.trim());
                holder.Messagetextbox.setText(part3);
                String Attributes = part4;
                String Category = part6;
                holder.MatchingPercentage = Integer.parseInt(part7.trim());
                holder.LostBy = part8.trim();
                holder.FoundBy = part9.trim();

                String formattedAttributes = part4.replace(",", "\n");
                holder.attributestextbox.setText(formattedAttributes);



                // Load the image from part10
                byte[] imageByteArray = decodeImageFromPart10(part10);
                if (imageByteArray != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
                    holder.itemImageView.setImageBitmap(bitmap);
                } else {
                    holder.itemImageView.setImageResource(R.drawable.ic_placeholder); // Fallback image
                }
                holder.Claim_Count = Integer.parseInt(part11.trim());

                int notificationId = item.getnotification_id();

                // Get registration number of the user from shared preferences
                SharedPreferences sharedPreferences = context.getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
                String regNo = sharedPreferences.getString("RegNo", "Unknown User");

                // Claim button action to update status and add new notification
                holder.ClaimButton.setOnClickListener(v -> {
                    updateNotificationStatus(notificationId, "Claimed");

                    // Prepare acceptance message
                    String claimMessage = createclaimMessage(holder.Lost_id, holder.Found_id, Attributes, Category, regNo, notificationId, holder.MatchingPercentage, holder.LostBy, holder.FoundBy,holder.Claim_Count);
                    addNotificationToAdminTable(claimMessage);

                    updateClaimCount(regNo);

                    notificationList.remove(position);
                    notifyItemRemoved(position);  // This will visually remove the item from the RecyclerView

                    // Update the adapter
                    notifyDataSetChanged();
                });

                // Reject button action
                holder.rejectButton.setOnClickListener(v -> {
                    updateNotificationStatus(notificationId, "Rejected");

                    // Fetch notifications after reject
                    //fetchNotification(regNo);

                    notificationList.remove(position);
                    notifyItemRemoved(position);

                    // Update the adapter
                    notifyDataSetChanged();
                });








           } else if ("Auction".equalsIgnoreCase(part5.trim())) {
//
                holder.ClaimButton.setVisibility(View.VISIBLE);
                holder.rejectButton.setVisibility(View.VISIBLE);
                holder.itemImageView.setVisibility(View.GONE);
                holder.Maximum_bidtextbox.setVisibility(View.VISIBLE);
                holder.Maximum_biddertextbox.setVisibility(View.VISIBLE);


                holder.item_id = Integer.parseInt(part1.trim());

                holder.Messagetextbox.setText(part3);
                String Attributes = part4;
                String Category = part6;

                holder.Maximum_bid = Integer.parseInt(part7.trim());
                holder.Maximum_bidder = part8.trim();

                holder.Maximum_bidtextbox.setText("Winning Bid: " + holder.Maximum_bid);
                holder.Maximum_biddertextbox.setText("Auction Winner: " + holder.Maximum_bidder);




                String formattedAttributes = part4.replace(",", "\n");
                holder.attributestextbox.setText(formattedAttributes);




                int notificationId = item.getnotification_id();




                holder.ClaimButton.setOnClickListener(v -> {
                    updateNotificationStatus(notificationId, "Claimed");

                    // Prepare acceptance message
                    String AuctionclaimMessage = createAuctionclaimMessage(holder.item_id, holder.Maximum_bid, Attributes, Category,  holder.Maximum_bidder, notificationId);
                    addNotificationToAdminTable(AuctionclaimMessage);




                    notificationList.remove(position);
                    notifyItemRemoved(position);  // This will visually remove the item from the RecyclerView
                    notifyDataSetChanged();
                });

                // Reject button action
                holder.rejectButton.setOnClickListener(v -> {
                    updateNotificationStatus(notificationId, "Rejected");



                    notificationList.remove(position);
                    notifyItemRemoved(position);

                    notifyDataSetChanged();
                });



            }


        } else {
            // Simple string message
            holder.Messagetextbox.setText(message);
            holder.attributestextbox.setVisibility(View.GONE);
            holder.ClaimButton.setVisibility(View.GONE);
            holder.rejectButton.setVisibility(View.GONE);
            holder.itemImageView.setVisibility(View.GONE);
            holder.Maximum_biddertextbox.setVisibility(View.GONE);
            holder.Maximum_bidtextbox.setVisibility(View.GONE);// Hide image when ShowButton is not "true"
        }
    }

    // Method to decode image from part10
    private byte[] decodeImageFromPart10(String part10) {
        if (part10 != null && !part10.isEmpty()) {
            return Base64.decode(part10, Base64.DEFAULT);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView Messagetextbox, attributestextbox,Maximum_biddertextbox,Maximum_bidtextbox;
        Button rejectButton, ClaimButton;
        public String LostBy,Maximum_bidder;
        public String FoundBy;

        int Lost_id, Found_id,item_id,MatchingPercentage,Maximum_bid,Claim_Count;
        ImageView itemImageView;

        public ItemViewHolder(View itemView) {
            super(itemView);

            Messagetextbox = itemView.findViewById(R.id.Messagetextbox);
            attributestextbox = itemView.findViewById(R.id.attributestextbox);
            Maximum_biddertextbox = itemView.findViewById(R.id.Maximum_biddertextbox);
            Maximum_bidtextbox = itemView.findViewById(R.id.Maximum_bidtextbox);
            rejectButton = itemView.findViewById(R.id.rejectButton);
            ClaimButton = itemView.findViewById(R.id.ClaimButton);
            itemImageView = itemView.findViewById(R.id.itemImageView);
        }
    }

    private void updateNotificationStatus(int notificationId, String status) {
        String url = "http://10.0.2.2/Projectfinal/api/StudentNotificationHandler/UpdateStatus";

        // Prepare the request body
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("NotificationId", notificationId);
            requestBody.put("Status", status); // Set the status (e.g., "Rejected" or "Delivered")
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Send the API request to update the notification status
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                response -> Log.d("Notification Update", "Status updated successfully for ID: " + notificationId),
                error -> Log.e("API Error", "Error updating notification: " + error.getMessage())
        );

        // Add the request to the Volley queue
        Volley.newRequestQueue(context).add(request);
    }

    private void addNotificationToAdminTable(String message) {
        String url = "http://10.0.2.2/Projectfinal/api/AdminNotification/SaveAdminNotification";

        // Prepare the request body
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("Message", message);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Send the API request to add a new notification to the admin's table
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                response -> Log.d("Notification Added", "Notification added successfully for admin"),
                error -> Log.e("API Error", "Error adding notification: " + error.getMessage())
        );

        // Add the request to the Volley queue
        Volley.newRequestQueue(context).add(request);
    }

    private String createclaimMessage(int lostId, int foundId, String attributes, String Catagory, String regNo, int notificationid, int MatchingPercentage, String lostby, String foundby,int Claim_Count ) {
        return "Found item is Claimed!Item: " + Catagory + "!Claimed by: " + regNo + "!" +
                attributes + "!Matched percentage " + MatchingPercentage + "%!" + "ShowButton:true!" +
                lostId + "!" + foundId + "!" + notificationid + "!" + lostby + "!" + foundby + "!Deliver!"+Claim_Count;
    }

    private String createAuctionclaimMessage(int item_id, int Maximum_bid, String attributes, String Catagory, String Maximum_bidder, int notificationid) {
        return Maximum_bidder + " Claimed the auction " + Catagory + "!Catagory: " + Catagory + "!Claimed by: " + Maximum_bidder + "!" +
                attributes + "!Winning Bid is " + Maximum_bid + "!" + "ShowButton:true!" +
                item_id + "!" + Maximum_bid + "!" + notificationid + "!" + Maximum_bidder + "!" + null +"!Auction!"+0 ;
    }

    private void updateClaimCount(String regno) {
        String url = "http://10.0.2.2/Projectfinal/api/Signup/IncrementClaimCount?regno=" + regno;

        // Create a PUT request with an empty request body
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, null,
                response -> Log.d("Claim Count Update", "Claim count updated successfully for regno: " + regno),
                error -> {
                    Log.e("API Error", "Error updating ClaimCount: " + error.getMessage());
                    if (error.networkResponse != null) {
                        Log.e("API Error", "Response Code: " + error.networkResponse.statusCode);
                    }
                }
        );

        // Add request to the Volley queue
        Volley.newRequestQueue(context).add(request);
    }



}
