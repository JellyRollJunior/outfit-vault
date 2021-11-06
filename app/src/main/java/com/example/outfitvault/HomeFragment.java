package com.example.outfitvault;

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

public class HomeFragment extends Fragment {
    public static final int NUM_COLS = 3;
    private final String TAG = "com.example.outfitvault.MainActivity.HomeFragment";
    private List<Outfit> outfits = new ArrayList<>();
    private DataBaseHelper dataBaseHelper;

    FloatingActionButton fabAddButton;
    RecyclerView rvDisplayOutfits;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        instantiateViews(view);
        instantiateDatabase();
        displayOnRecView(outfits);
        wireAddOutfitFloatingActionButton();

        return view;
    }

    private void instantiateViews(View view) {
        fabAddButton = view.findViewById(R.id.fab_add_outfits);
        rvDisplayOutfits = view.findViewById(R.id.rv_display_outfits2);
    }

    @Override
    public void onResume() {
        refreshRecyclerView();
        super.onResume();
    }

    private void refreshRecyclerView() {
        instantiateDatabase();
        displayOnRecView(outfits);
    }

    private void wireAddOutfitFloatingActionButton() {
        fabAddButton.setOnClickListener(view -> {
            Intent intent = OutfitCreateActivity.makeIntent(getActivity());
            startActivity(intent);
        });
    }

    private void instantiateDatabase() {
        dataBaseHelper = new DataBaseHelper(getActivity());
        outfits = dataBaseHelper.getAll();

        // debug
        Log.d(TAG, "instantiateDatabase: " + outfits.toString());
    }

    private void displayOnRecView(List<Outfit> outfits) {
        OutfitRecViewAdapter rvAdapter = new OutfitRecViewAdapter(getActivity(), outfits);

        rvDisplayOutfits.setAdapter(rvAdapter);
        rvDisplayOutfits.setLayoutManager(new GridLayoutManager(getActivity(), NUM_COLS));
    }
}
