package com.example.outfitvault;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/** Displays favorite outfits via recycler view. */
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
        outfits = dataBaseHelper.getFavorites();
    }

}