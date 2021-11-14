package com.example.outfitvault;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.outfitvault.model.DataBaseHelper;
import com.example.outfitvault.model.Outfit;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public abstract class OutfitRecyclerAbstract extends Fragment{
    private static final String TAG = "com.example.outfitvault.MainActivity.OutfitRecyclerAbstract";

    public DataBaseHelper dataBaseHelper;

    abstract void instantiateViews(View view);

    abstract void instantiateOutfits();

    public void instantiateDatabase(Context context) {
        if (dataBaseHelper == null) {
            dataBaseHelper = new DataBaseHelper(context);
        }
    }

    public void displayOutfitsOnRecView(Context context, List<Outfit> outfits, RecyclerView recyclerView, int numCols) {
        OutfitRecViewAdapter rvAdapter = new OutfitRecViewAdapter(context, outfits);

        recyclerView.setAdapter(rvAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numCols));
    }

    public void wireFAB(Context context, FloatingActionButton fab) {
        fab.setOnClickListener(view -> {
            Intent intent = OutfitCreateActivity.makeIntent(context);

            // get exit transition set from context
            startActivity(intent, ActivityOptions
                                        .makeSceneTransitionAnimation((Activity) context)
                                        .toBundle()
            );
        });
    }

    private void refreshRecyclerView(Context context, List<Outfit> outfits, RecyclerView recyclerView, int numCols) {
        instantiateOutfits();
        displayOutfitsOnRecView(context, outfits, recyclerView, numCols);
    }
}
