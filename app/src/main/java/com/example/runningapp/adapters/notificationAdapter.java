package com.example.runningapp.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.example.runningapp.R;
import com.example.runningapp.models.notificationModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class notificationAdapter extends RecyclerView.Adapter<notificationAdapter.ItemViewHolder> {

    private final List<notificationModel> notificationList;

    public notificationAdapter(List<notificationModel> notificationList) {
        this.notificationList = notificationList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false);
        return new ItemViewHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        notificationModel item = notificationList.get(position);
        String message = item.getmessage();


        if (message != null && !message.isEmpty()) {
            holder.Messagetextbox.setText(message);
        } else {
            holder.Messagetextbox.setText("No message available");
        }


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
        String part12 = parts.length > 11 ? parts[11] : "";
        String part13 = parts.length > 12 ? parts[12] : "";


        if ("ShowButton:true".equalsIgnoreCase(part6.trim())) {
            holder.DeliverButton.setVisibility(View.VISIBLE);
            holder.rejectButton.setVisibility(View.VISIBLE);

        } else {
            holder.DeliverButton.setVisibility(View.GONE);
            holder.rejectButton.setVisibility(View.GONE);

        }

        int Id = item.getnotification_id();

        if ("Deliver".equalsIgnoreCase(part12.trim())) {

            holder.Messagetextbox.setText(part1);
            holder.Catagorytextbox.setText(part2);
            holder.persontextbox.setText(part3);

            String formattedAttributes = part4.replace(",", "\n");
            holder.attributestextbox.setText(formattedAttributes);

            holder.matchingtextbox.setText(part5);
            holder.lostid = Integer.parseInt(part7.trim());
            holder.foundid = Integer.parseInt(part8.trim());
            holder.notificationId = Integer.parseInt(part9.trim());
            String lostBy = part10;
            String foundBy = part11;

            if ("ShowButton:true".equalsIgnoreCase(part6.trim())) {

                holder.ClaimCounttextbox.setVisibility(View.VISIBLE);
                holder.ClaimCounttextbox.setText("Claims Count: " + ((Integer.parseInt(part13) + 1)));

            }
            String delivermassege = "Congratulations Your item has been successfully delivered to you.";

            String thankyoumessage = "Thank you for your kindness in reporting the found item. Your " +
                    "efforts helped reunite it with its owner. We truly appreciate it";

            holder.DeliverButton.setOnClickListener(view -> {


                String type = "Original"; // Default value



                deliverItem(holder.foundid, holder.lostid, type, holder);
                updateNotificationStatus(holder.notificationId, "Delivered", holder.context);
                addStudentNotification(delivermassege, "Processing", lostBy, holder.context);
                readNotification(Id, holder.context);
                addStudentNotification(thankyoumessage, "Processing", foundBy, holder.context);

                notificationList.remove(position);
                notifyItemRemoved(position); 

            });


            String Rejectedmessage = "Your claim to get your lost item is rejected by Admin";
            holder.rejectButton.setOnClickListener(view -> {
                updateNotificationStatus(holder.notificationId, "Rejected", holder.context);

                addStudentNotification(Rejectedmessage, "Processing", lostBy, holder.context);
                readNotification(Id, holder.context);

                notificationList.remove(position);
                notifyItemRemoved(position);
            });










        }else  if ("Auction".equalsIgnoreCase(part12.trim())) {

            holder.Messagetextbox.setText(part1);
            holder.Catagorytextbox.setText(part2);
            holder.persontextbox.setText(part3);

            String formattedAttributes = part4.replace(",", "\n");
            holder.attributestextbox.setText(formattedAttributes);

            holder.matchingtextbox.setText(part5);
            holder.item_id = Integer.parseInt(part7.trim());
            holder.Bid = Integer.parseInt(part8.trim());
            holder.notificationId = Integer.parseInt(part9.trim());
            String Winner = part10;
            String foundBy = part11;


            String Auctiondelivermassege = "Congratulations You have won the auction, and the item has been " +
                    "successfully delivered to you. Thank you for participating";

            String thankyoumessage = "Thank you for your kindness in reporting the found item. Your " +
                    "efforts helped reunite it with its owner. We truly appreciate it";

            holder.DeliverButton.setOnClickListener(view -> {


               // Toast.makeText(view.getContext(), "Auction deliver is pressed" + holder.foundid, Toast.LENGTH_SHORT).show();
                AuctiondeliverItem(holder.item_id, holder.Bid, Winner, holder);
                updateNotificationStatus(holder.notificationId, "Delivered", holder.context);
                addStudentNotification(Auctiondelivermassege, "Processing", Winner, holder.context);
                readNotification(Id, holder.context);


                notificationList.remove(position);
                notifyItemRemoved(position); //

            });


            String AuctionRejectedmessage = "Admin rejected to give you winning item from Auction";
            holder.rejectButton.setOnClickListener(view -> {
           //     Toast.makeText(view.getContext(), "Auction reject is pressed" + holder.foundid, Toast.LENGTH_SHORT).show();
              updateNotificationStatus(holder.notificationId, "Rejected", holder.context);

                addStudentNotification(AuctionRejectedmessage, "Processing", Winner, holder.context);
                readNotification(Id, holder.context);

                notificationList.remove(position);
                notifyItemRemoved(position);
            });


        }


    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        Context context;
        TextView Messagetextbox, Catagorytextbox, persontextbox, attributestextbox, matchingtextbox,ClaimCounttextbox;
        Button DeliverButton, rejectButton;
        int foundid, lostid, notificationId,item_id,Bid;

       // RadioGroup radioGroup;

        public ItemViewHolder(View itemView, Context context) {
            super(itemView);
            this.context = context;
            Messagetextbox = itemView.findViewById(R.id.Messagetextbox);
            Catagorytextbox = itemView.findViewById(R.id.Catagorytextbox);
            persontextbox = itemView.findViewById(R.id.personTextbox);
            attributestextbox = itemView.findViewById(R.id.attributetextbox);
            matchingtextbox = itemView.findViewById(R.id.matchtextbox);
            DeliverButton = itemView.findViewById(R.id.DeliverButton);
            rejectButton = itemView.findViewById(R.id.rejectButton);
           ClaimCounttextbox = itemView.findViewById(R.id.ClaimCounttextbox);
        }
    }

    private int parseMessagePart(String[] parts, int index, int defaultValue) {
        try {
            return Integer.parseInt(parts.length > index ? parts[index].trim() : String.valueOf(defaultValue));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private void deliverItem(int foundItemId, int lostItemId,String type, ItemViewHolder holder) {
        String url = "http://10.0.2.2/Projectfinal/api/DelieverItem/AddDelieveredItem";
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("LostItemId", lostItemId);
            requestBody.put("FoundItemId", foundItemId);
            requestBody.put("Type", type);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                response -> Toast.makeText(holder.context, "Item delivered successfully!", Toast.LENGTH_SHORT).show(),
                error -> Toast.makeText(holder.context, "Item is not available", Toast.LENGTH_SHORT).show());

        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(holder.context).add(request);
    }

    private void updateNotificationStatus(int notificationId, String status, Context context) {
        String url = "http://10.0.2.2/Projectfinal/api/StudentNotificationHandler/UpdateStatus";
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("NotificationId", notificationId);
            requestBody.put("Status", status);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                response -> Log.d("Notification Update", "Status updated successfully."),
                error -> Log.e("Notification Update", "Error updating notification."));

        Volley.newRequestQueue(context).add(request);
    }

    private void addStudentNotification(String message, String status, String regNo, Context context) {
        String url = "http://10.0.2.2/Projectfinal/api/StudentNotifiaction/SaveNotification";

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("Message", message);
            requestBody.put("Status", status);
            requestBody.put("RegistrationNo", regNo);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                response -> Log.d("Notification Added", "Notification added successfully."),
                error -> Log.e("API Error", "Error adding notification: " + error.getMessage()));

        Volley.newRequestQueue(context).add(request);
    }

    private void readNotification(int id, Context context) {
        String url = "http://10.0.2.2/Projectfinal/api/AdminNotificationHandler/MarkAsReadById?id=" + id;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null,
                response -> Log.d("Notification Update", "Status updated successfully."),
                error -> Log.e("Notification Update", "Error updating notification: " + error.getMessage())) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        Volley.newRequestQueue(context).add(request);
    }

    private void AuctiondeliverItem(int item_id, int Final_Bid_price,String Sold_to, ItemViewHolder holder) {
        String url = "http://10.0.2.2/Projectfinal/api/AuctionHistory/MoveItemToHistory";
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("item_id", item_id);
            requestBody.put("Final_Bid_price", Final_Bid_price);
            requestBody.put("Sold_to", Sold_to);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                response -> Toast.makeText(holder.context, "Auction Item delivered successfully!", Toast.LENGTH_SHORT).show(),
                error -> Toast.makeText(holder.context, "Auction Item is not available", Toast.LENGTH_SHORT).show());

        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(holder.context).add(request);
    }


}
