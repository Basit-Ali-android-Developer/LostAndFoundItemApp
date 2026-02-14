package com.example.runningapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.runningapp.R;
import com.example.runningapp.models.studentAuctionmodel;

import java.util.List;

public class adminAuctionAdapter extends RecyclerView.Adapter<adminAuctionAdapter.ItemViewHolder> {

    private final List<studentAuctionmodel> auctionList;

    public adminAuctionAdapter(List<studentAuctionmodel> auctionList) {
        this.auctionList = auctionList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adminauctionlayout, parent, false);
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
        holder.categoryTextView.setText(item.getCategory());
        holder.specificationTextView.setText(item.getAttributes());
        holder.bidByTextView.setText(item.getMaximumBidder());
        holder.maximumBidTextView.setText(String.valueOf(item.getMaximumBid()));
        holder.StatusTextView.setText(item.getStatus());
    }

    @Override
    public int getItemCount() {
        return auctionList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImageView;
        TextView specificationTextView, bidByTextView, maximumBidTextView,categoryTextView,StatusTextView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemImageView = itemView.findViewById(R.id.itemImageView);
            categoryTextView= itemView.findViewById(R.id.categoryTextView);
            specificationTextView = itemView.findViewById(R.id.SpecificationTextView);
            bidByTextView = itemView.findViewById(R.id.BidbyTextView);
            maximumBidTextView = itemView.findViewById(R.id.maximumbidTextView);
            StatusTextView = itemView.findViewById(R.id.StatusTextView);
        }
    }
}
