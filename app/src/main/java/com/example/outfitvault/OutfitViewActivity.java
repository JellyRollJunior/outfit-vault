package com.example.outfitvault;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OutfitViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outfit_view);

        Button btn = findViewById(R.id.btnEditOutfit);
        btn.setVisibility(View.GONE);
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, OutfitViewActivity.class);
    }
}