package com.example.outfitvault;

import android.content.Context;
import android.media.MediaRouter2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.outfitvault.model.Outfit;

import java.util.ArrayList;
import java.util.List;

public class OutfitRecViewAdapter extends RecyclerView.Adapter<OutfitRecViewAdapter.ViewHolder>{
    private static final String TAG = "OutfitRecViewAdapter";
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
    }

    @Override
    public int getItemCount() {
        return outfits.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView outfitImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            outfitImage = itemView.findViewById(R.id.mainRVOutfitImage);
        }
    }
}