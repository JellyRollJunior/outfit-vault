package com.example.outfitvault;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.outfitvault.model.DataBaseHelper;
import com.example.outfitvault.model.Outfit;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FavoritesFragment extends OutfitRecyclerAbstract {
    private static final String TAG = "com.example.outfitvault.FavoritesFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_favorites, container, false);

        instantiateViews(view);
        instantiateOutfits();
        displayOutfitsOnRecView(getActivity(), outfits, rvDisplayOutfits, NUM_COLS);
        wireFAB(getActivity(), fabAddButton);

        return view;
    }

    @Override
    void instantiateOutfits() {
        instantiateDatabase(getActivity());
        outfits = dataBaseHelper.getAllFavorites();
    }

}