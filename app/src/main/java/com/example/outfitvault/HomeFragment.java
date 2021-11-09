package com.example.outfitvault;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

public class HomeFragment extends Fragment {
    public static final int NUM_COLS = 3;
    private static final String EXTRA_DISPLAY_FAVORITE = "com.example.outfitvault.MainActivity.HomeFragment - display favorite";
    private static final String TAG = "com.example.outfitvault.MainActivity.HomeFragment";

    private List<Outfit> outfits = new ArrayList<>();
    private DataBaseHelper dataBaseHelper;
    private FloatingActionButton fabAddButton;
    private RecyclerView rvDisplayOutfits;
    private boolean displayOnlyFavorites;

    static HomeFragment displayOnlyFavorites(Boolean displayFavorite) {
        HomeFragment homeFragment = new HomeFragment();

        // store displayFavorite boolean in EXTRA
        Bundle args = new Bundle();
        args.putBoolean(EXTRA_DISPLAY_FAVORITE, displayFavorite);
        homeFragment.setArguments(args);

        return homeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        getExtraDisplayOnlyFavorites();
        instantiateViews(view);
        instantiateDatabase(displayOnlyFavorites);
        displayOnRecView(outfits);
        wireAddOutfitFloatingActionButton();

        return view;
    }

    private void getExtraDisplayOnlyFavorites() {
        if (getArguments() != null) {
            displayOnlyFavorites = getArguments().getBoolean(EXTRA_DISPLAY_FAVORITE);
            Toast.makeText(getContext(), "Display Only favorites: " + displayOnlyFavorites, Toast.LENGTH_SHORT).show();
        }
    }

    private void instantiateViews(View view) {
        fabAddButton = view.findViewById(R.id.fab_add_outfits);
        rvDisplayOutfits = view.findViewById(R.id.rv_display_outfits2);
    }

    private void instantiateDatabase(boolean displayOnlyFavorites) {
        dataBaseHelper = new DataBaseHelper(getActivity());

        if (displayOnlyFavorites) {
            outfits = dataBaseHelper.getAllFavorites();
        } else {
            outfits = dataBaseHelper.getAll();
        }

        // debug
        Log.d(TAG, "instantiateDatabase: " + outfits.toString());
    }

    private void displayOnRecView(List<Outfit> outfits) {
        OutfitRecViewAdapter rvAdapter = new OutfitRecViewAdapter(getActivity(), outfits);

        rvDisplayOutfits.setAdapter(rvAdapter);
        rvDisplayOutfits.setLayoutManager(new GridLayoutManager(getActivity(), NUM_COLS));
    }

    private void wireAddOutfitFloatingActionButton() {
        fabAddButton.setOnClickListener(view -> {
            Intent intent = OutfitCreateActivity.makeIntent(getActivity());
            startActivity(intent);
        });
    }

    @Override
    public void onResume() {
        refreshRecyclerView();
        super.onResume();
    }

    private void refreshRecyclerView() {
        instantiateDatabase(displayOnlyFavorites);
        displayOnRecView(outfits);
    }
}
