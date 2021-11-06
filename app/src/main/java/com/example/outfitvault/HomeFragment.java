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
    private final String TAG = "com.example.outfitvault.MainActivity";
    private List<Outfit> outfits = new ArrayList<>();
    private DataBaseHelper dataBaseHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //instantiateDatabase();
        //displayOnRecView(outfits);
        //wireAddOutfitFloatingActionButton();
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
        FloatingActionButton fabAddButton = getActivity().findViewById(R.id.fab_add_outfits);

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
        RecyclerView rvDisplayOutfits = getActivity().findViewById(R.id.rv_display_outfits);
        OutfitRecViewAdapter rvAdapter = new OutfitRecViewAdapter(getActivity(), outfits);

        rvDisplayOutfits.setAdapter(rvAdapter);
        rvDisplayOutfits.setLayoutManager(new GridLayoutManager(getActivity(), NUM_COLS));
    }
}
