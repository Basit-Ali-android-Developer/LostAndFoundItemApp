package com.example.runningapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import com.example.runningapp.R;
import com.example.runningapp.models.DeliveredModel;


import java.io.ByteArrayOutputStream;
import java.util.List;

public class DeliveredAdapter extends RecyclerView.Adapter<DeliveredAdapter.ItemViewHolder> {

    private final List<DeliveredModel> itemsList;
    private final Context context;
    private final Class<?> targetActivity;

    public DeliveredAdapter(Context context, List<DeliveredModel> itemsList,Class<?> targetActivity) {
        this.context = context;
        this.itemsList = itemsList;
        this.targetActivity = targetActivity;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.delivered_item_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        DeliveredModel item = itemsList.get(position);

        holder.categoryTextView.setText(item.getCategory());
        holder.LostbyTextView.setText("Lost By: " + item.getLostby());
        holder.FoundbyTextView.setText("Found By: " + item.getFoundby());


        if (item.getImage() != null) {
            holder.itemImageView.setImageBitmap(item.getImage());
        } else {
            holder.itemImageView.setImageResource(R.drawable.ic_placeholder);
        }






        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context,targetActivity);
            intent.putExtra("category", item.getCategory());
            intent.putExtra("lostDate", item.getLostdate());
            intent.putExtra("lostLocation", item.getLostlocation());
            intent.putExtra("LostBy", item.getLostby());
          //  intent.putExtra("Award", item.getAward());
            intent.putExtra("FoundDate", item.getFounddate());
            intent.putExtra("foundLocation", item.getFoundlocation());
            intent.putExtra("FoundBy", item.getFoundby());

            intent.putExtra("Delivered_date", item.getDelivered_date());
            intent.putExtra("Type", item.getType());
          //  intent.putExtra("Price", item.getPrice());
            intent.putExtra("attributes", item.getAttributes());


            if (item.getImage() != null) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                item.getImage().compress(Bitmap.CompressFormat.JPEG, 50, stream); // Compress the image
                byte[] byteArray = stream.toByteArray();
                intent.putExtra("image", byteArray);
            }


            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImageView;
        TextView categoryTextView, LostbyTextView, FoundbyTextView;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImageView = itemView.findViewById(R.id.itemImageView);
            categoryTextView = itemView.findViewById(R.id.categoryTextView);
            LostbyTextView = itemView.findViewById(R.id.LostByTextView);
            FoundbyTextView = itemView.findViewById(R.id.FoundbyTextView);

        }
    }
}
