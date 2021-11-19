package com.example.outfitvault;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.outfitvault.model.DataBaseHelper;
import com.example.outfitvault.model.Outfit;
import com.example.outfitvault.types.Season;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class ChartFragment extends Fragment {
    private static String TAG = "com.example.outfitvault.ChartFragment";
    private DataBaseHelper dataBaseHelper;
    private int[] outfitsPerSeason = {0, 0, 0, 0};
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
            if (outfitsPerSeason[i] > 0) {
                pieEntries.add(new PieEntry(outfitsPerSeason[i], season.toString()));
            }
            i++;

            // debug
            Log.d(TAG, "setUpPieChart: " + season.toString());
        }

        PieDataSet dataSet = new PieDataSet(pieEntries, getString(R.string.outfits_per_season));
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextSize(15);

        PieData data = new PieData(dataSet);
        pieChart.setData(data);

        setChartVisuals();
        pieChart.invalidate();     // make chart redraw
    }

    private void setChartVisuals() {
        pieChart.setCenterText(getString(R.string.chart_title));
        pieChart.setCenterTextSize(30);
        pieChart.setCenterTextColor(Color.WHITE);
        pieChart.setHoleColor(Color.BLACK);

        Legend l = pieChart.getLegend();
        l.setTextColor(Color.WHITE);

        pieChart.setMinAngleForSlices(20);
        pieChart.animateY(1000);
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
                    outfitsPerSeason[3]++;
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
