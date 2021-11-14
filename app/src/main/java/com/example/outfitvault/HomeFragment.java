package com.example.outfitvault;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.outfitvault.model.DataBaseHelper;
import com.example.outfitvault.model.Outfit;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends OutfitRecyclerAbstract {
    private static final int NUM_COLS = 3;
    private static final String TAG = "com.example.outfitvault.HomeFragment";

    private List<Outfit> outfits;
    private FloatingActionButton fabAddButton;
    private RecyclerView rvDisplayOutfits;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        instantiateViews(view);
        instantiateOutfits();
        displayOutfitsOnRecView(getActivity(), outfits, rvDisplayOutfits, NUM_COLS);
        wireFAB(getActivity(), fabAddButton);

        return view;
    }

    @Override
    void instantiateViews(View view) {
        fabAddButton = view.findViewById(R.id.fab_add_outfits);
        rvDisplayOutfits = view.findViewById(R.id.rv_display_outfits);
    }

    @Override
    void instantiateOutfits() {
        instantiateDatabase(getActivity());
        outfits = dataBaseHelper.getAll();
    }

    @Override
    public void onResume() {
        super.onResume();

        instantiateOutfits();
        displayOutfitsOnRecView(getActivity(), outfits, rvDisplayOutfits, NUM_COLS);
    }
}
