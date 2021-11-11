package com.example.outfitvault;

import android.app.Activity;
import android.app.ActivityOptions;
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
    private final List<Outfit> outfits;
    private final Context context;

    public OutfitRecViewAdapter(Context context, List<Outfit> outfits) {
        this.context = context;
        this.outfits = outfits;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.outfit_list_item, parent, false);
        return new ViewHolder(view);    // make sure to pass OUR created ViewHolder class
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Outfit currentOutfit = outfits.get(position);

        // set image
        String photoFilePath = PhotoHelper
                                    .getPhotoFile(context, currentOutfit.getImageName())
                                    .getAbsolutePath();
        Bitmap photoBitmap = BitmapFactory.decodeFile(photoFilePath);

        Bitmap rotatedBitmap = PhotoHelper.rotate90Degrees(photoBitmap);
        holder.ivOutfit.setImageBitmap(rotatedBitmap);

        // move to OutfitViewActivity on click
        holder.parent.setOnClickListener(view -> {
            int currentOutfitID = currentOutfit.getID();
            Intent intent = OutfitViewActivity.makeIntent(context, currentOutfitID);

            // create shared element transition
            ActivityOptions options =
                    ActivityOptions
                            .makeSceneTransitionAnimation(
                                    (Activity) context,
                                    holder.ivOutfit,
                                    "outfit");
            context.startActivity(intent, options.toBundle());
        });
    }

    @Override
    public int getItemCount() {
        return outfits.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivOutfit;
        private final MaterialCardView parent;
        final View ivOutfitViewActivityIV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivOutfit = itemView.findViewById(R.id.iv_recycler_item);
            ivOutfitViewActivityIV = itemView.findViewById(R.id.iv_outfit_view);
            parent = itemView.findViewById(R.id.cv_recycler_item);
        }
    }
}