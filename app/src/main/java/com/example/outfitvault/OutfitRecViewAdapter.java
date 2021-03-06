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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.outfitvault.model.Outfit;
import com.example.outfitvault.model.PhotoHelper;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

/** Creates custom adapter for displaying outfits on a recycler view. */
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
                                    .getPhotoFile(context, currentOutfit.getPhotoName())
                                    .getAbsolutePath();
        Bitmap photoBitmap = BitmapFactory.decodeFile(photoFilePath);
        Bitmap rotatedBitmap = PhotoHelper.rotate90Degrees(photoBitmap);
        holder.ivOutfit.setImageBitmap(rotatedBitmap);

        // set favorite iv visibility
        if (!currentOutfit.getFavorite()) {
            holder.ivFavorite.setVisibility(View.GONE);
        }

        // set season text
        holder.tvSeason.setText(currentOutfit.getSeason().toString());

        // move to OutfitViewActivity on click
        holder.parent.setOnClickListener(view -> {
            Intent intent = OutfitViewActivity.makeIntent(context, currentOutfit);

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
        private final ImageView ivFavorite;
        private final TextView tvSeason;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivOutfit = itemView.findViewById(R.id.iv_recycler);
            parent = itemView.findViewById(R.id.cv_recycler_item);
            ivFavorite = itemView.findViewById(R.id.iv_favorite_recycler);
            tvSeason = itemView.findViewById(R.id.tv_season_recycler);
        }
    }
}