package com.example.runningapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.runningapp.FoundItemDetailActivity;
import com.example.runningapp.R;
import com.example.runningapp.models.ItemModel;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private final List<ItemModel> itemsList;
    private final Context context;
    private final Class<?> targetActivity;

    public ItemAdapter(Context context, List<ItemModel> itemsList, Class<?> targetActivity) {
        this.context = context;
        this.itemsList = itemsList;
        this.targetActivity = targetActivity;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_design_onmain, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        ItemModel item = itemsList.get(position);

        holder.categoryTextView.setText(item.getCategory());

        String Check=item.getUrgent();

        if(Check.equals("True")) {
            holder.DateTextView.setText("if you find this item Kindly Report it Quickly");
        }else {
            holder.DateTextView.setText("Date: " + item.getDate());
        }


        holder.LocationTextView.setText("Location: " + item.getLocation());

        if (item.getImage() != null) {
            holder.itemImageView.setImageBitmap(item.getImage());
        } else {
            holder.itemImageView.setImageResource(R.drawable.ic_placeholder);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, targetActivity);
            intent.putExtra("ItemId",item.getItem_id());
            intent.putExtra("category", item.getCategory());
            intent.putExtra("date", item.getDate());
            intent.putExtra("location", item.getLocation());
            intent.putExtra("money", item.getmoney());
            intent.putExtra("person", item.getperson());
            intent.putExtra("attributes", item.getAttributes());
            intent.putExtra("Nominate", item.getNominate());

            Toast.makeText(context, item.getperson(), Toast.LENGTH_SHORT).show();


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
        TextView categoryTextView, DateTextView, LocationTextView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImageView = itemView.findViewById(R.id.itemImageView);
            categoryTextView = itemView.findViewById(R.id.categoryTextView);
            DateTextView = itemView.findViewById(R.id.DateTextView);
            LocationTextView = itemView.findViewById(R.id.LocationTextView);
        }
    }
}
