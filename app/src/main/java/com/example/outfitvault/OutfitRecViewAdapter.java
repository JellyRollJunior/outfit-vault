package com.example.outfitvault;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.outfitvault.model.Outfit;
import com.example.outfitvault.model.PhotoHelper;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class OutfitRecViewAdapter extends RecyclerView.Adapter<OutfitRecViewAdapter.ViewHolder>{
    private static final String TAG = "com.example.outfitvault.OutfitRecViewAdapter";
    private List<Outfit> outfits;
    private Context context;

    public OutfitRecViewAdapter(Context context, List<Outfit> outfits) {
        this.context = context;
        this.outfits = outfits;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.outfit_list_item, parent, false);
        return new ViewHolder(view);    // make sure to pass OUR created ViewHolder class
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Outfit currentOutfit = outfits.get(position);

        // set image
        String photoFilePath = PhotoHelper.getPhotoFile(context, currentOutfit.getImageName()).getAbsolutePath();
        Bitmap photoBitmap = BitmapFactory.decodeFile(photoFilePath);

        Bitmap rotatedBitmap = PhotoHelper.rotate90Degrees(photoBitmap);
        holder.outfitImage.setImageBitmap(rotatedBitmap);

        // move to outfit details on click
        holder.parent.setOnClickListener(view -> {
            int currentOutfitID = currentOutfit.getID();
            Intent intent = OutfitViewActivity.makeIntent(context, currentOutfitID);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return outfits.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView outfitImage;
        private final MaterialCardView parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            outfitImage = itemView.findViewById(R.id.mainRVOutfitImage);
            parent = itemView.findViewById(R.id.rvCardView);
        }
    }
}