package com.example.outfitvault;

import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.MenuItem;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private NavigationBarView navigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // REQUEST NEEDS TO BE BEFORE super.onCreate CALL
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        getWindow().setExitTransition(new Explode());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // NEVER DELETE THIS LINE BY ACCIDENT OR CANT ACCESS VIEWS


        setupBottomNavDefaults();
        setupBottomNavFragmentSwitch();
    }

    private void setupBottomNavDefaults() {
        navigationBar = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment, new HomeFragment())
                .commit();

        navigationBar.setSelectedItemId(R.id.nav_home);

        navigationBar.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);
    }

    private void setupBottomNavFragmentSwitch() {
        navigationBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
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

                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                android.R.anim.fade_in,
                                android.R.anim.fade_out)
                        .replace(R.id.nav_host_fragment, selectedFragment)
                        .commit();
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
