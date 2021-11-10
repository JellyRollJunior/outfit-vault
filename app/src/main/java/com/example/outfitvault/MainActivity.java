package com.example.outfitvault;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private NavigationBarView navigationBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // NEVER DELETE THIS LINE BY ACCIDENT OR CANT ACCESS VIEWS

        setupBottomNavDefaults();
        setupBottomNavFragmentSwitch();
    }

    private void setupBottomNavDefaults() {
        navigationBarView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        navigationBarView.setSelectedItemId(R.id.nav_home);
        navigationBarView.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);
    }

    private void setupBottomNavFragmentSwitch() {
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.nav_charts:
                        selectedFragment = new ChartFragment();
                        break;
                    case R.id.nav_home:
                        selectedFragment = HomeFragment.displayOnlyFavorites(false);
                        break;
                    case R.id.nav_favorites:
                        selectedFragment = HomeFragment.displayOnlyFavorites(true);
                        break;
                    case R.id.nav_places:
                        selectedFragment = new MapsFragment();
                        break;
                }
                //navigationBarView.
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                return true;
            }
        });
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
