package com.example.outfitvault;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OutfitRecViewAdapter {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView outfitImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}