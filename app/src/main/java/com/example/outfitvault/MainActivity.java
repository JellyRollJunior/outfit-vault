package com.example.outfitvault;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.outfitvault.model.DataBaseHelper;
import com.example.outfitvault.model.Outfit;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // NEVER DELETE THIS LINE BY ACCIDENT OR CANT ACCESS VIEWS

        NavigationBarView navigationBarView = findViewById(R.id.bottom_navigation);
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.nav_charts:
                        break;
                    case R.id.nav_home:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.nav_favorites:
                        break;
                    case R.id.nav_places:
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                return true;
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        navigationBarView.setSelectedItemId(R.id.nav_home);
        navigationBarView.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);
    }



}

/*
    // SQLITE db delete debug
    Outfit outfit = new Outfit(1, "newIMAGE!", "it's a new image", Season.SPRING, false);
    boolean successDel = dataBaseHelper.deleteOne(outfit);
    if (successDel) {
        Toast.makeText(MainActivity.this, "success", Toast.LENGTH_LONG).show();
    }
 */
