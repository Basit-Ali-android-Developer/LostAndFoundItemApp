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
import com.example.runningapp.models.AuctionHistoryModel;


import java.io.ByteArrayOutputStream;
import java.util.List;

public class AuctionHistoryAdapter extends RecyclerView.Adapter<AuctionHistoryAdapter.ItemViewHolder> {

    private final List<AuctionHistoryModel> itemsList;
    private final Context context;
    private final Class<?> targetActivity;

    public AuctionHistoryAdapter(Context context, List<AuctionHistoryModel> itemsList,Class<?> targetActivity) {
        this.context = context;
        this.itemsList = itemsList;
        this.targetActivity = targetActivity;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.auctionhistorylayout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        AuctionHistoryModel item = itemsList.get(position);

        holder.categoryTextView.setText(item.getCategory());
        holder.soldtoTextView.setText("Sold To: " + item.getSoldTo());
        holder.DateTextView.setText("Deliver Date: " + item.getSoldDate());


        if (item.getImage() != null) {
            holder.itemImageView.setImageBitmap(item.getImage());
        } else {
            holder.itemImageView.setImageResource(R.drawable.ic_placeholder);
        }






        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context,targetActivity);
            intent.putExtra("category", item.getCategory());
            intent.putExtra("foundDate", item.getFoundDate());
            intent.putExtra("foundLocation", item.getFoundLocation());
            intent.putExtra("foundBy", item.getFoundBy());
            intent.putExtra("startingPrice", item.getEstimatedPrice());
            intent.putExtra("startingdate", item.getStartDate());
            intent.putExtra("enddate", item.getEndDate());

            intent.putExtra("soldPrice", item.getFinalBidPrice());
            intent.putExtra("soldto", item.getSoldTo());
            intent.putExtra("solddate", item.getSoldDate());
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
        TextView categoryTextView, soldtoTextView, DateTextView;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImageView = itemView.findViewById(R.id.itemImageView);
            categoryTextView = itemView.findViewById(R.id.categoryTextView);
            soldtoTextView = itemView.findViewById(R.id.soldtoTextView);
            DateTextView = itemView.findViewById(R.id.DateTextView);

        }
    }
}
