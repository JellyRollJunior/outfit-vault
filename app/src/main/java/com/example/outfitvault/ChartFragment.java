package com.example.outfitvault;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.outfitvault.model.DataBaseHelper;
import com.example.outfitvault.model.Outfit;
import com.example.outfitvault.types.Season;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class ChartFragment extends Fragment {
    private static String TAG = "com.example.outfitvault.ChartFragment";
    private DataBaseHelper dataBaseHelper;
    private float[] outfitsPerSeason = {0.0f, 0.0f, 0.0f, 0.0f};
    private List<Outfit> outfits;
    private PieChart pieChart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_chart, container, false);

        instantiateViews(view);
        instantiateDatabase();
        setUpPieChart();

        return view;
    }

    private void setUpPieChart() {
        countArticlesPerSeason();

        // populate list of pie entries
        List<PieEntry> pieEntries = new ArrayList<>();
        int i = 0;
        for (Season season: Season.values()) {
            pieEntries.add(new PieEntry(outfitsPerSeason[i], season.toString()));
            i++;

            // debug
            Log.d(TAG, "setUpPieChart: " + season.toString());
        }

        PieDataSet dataSet = new PieDataSet(pieEntries, getString(R.string.outfits_per_season));
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData data = new PieData(dataSet);

        // get the chart
        pieChart.setData(data);
        pieChart.animateY(1000);
        pieChart.invalidate();     // make chart redraw
    }

    private void countArticlesPerSeason() {
        for (Outfit outfit : outfits) {
            String currOutfitSeason = outfit.getSeason().toString();

            // i know hard coding isn't good practice :(
            switch (currOutfitSeason) {
                case "FALL":
                    outfitsPerSeason[0]++;
                    break;
                case "WINTER":
                    outfitsPerSeason[1]++;
                    break;
                case "SPRING":
                    outfitsPerSeason[2]++;
                    break;
                case "SUMMER":
                    outfitsPerSeason[4]++;
                    break;
            }
        }
    }

    private void instantiateDatabase() {
        dataBaseHelper = new DataBaseHelper(getActivity());
        outfits = dataBaseHelper.getAll();
    }

    private void instantiateViews(View view) {
        pieChart = view.findViewById(R.id.chart_season);
    }

}
