package com.example.outfitvault;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OutfitRecViewAdapter {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView outfitImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            outfitImage = itemView.findViewById(R.id.mainRVOutfitImage);
        }
    }
}