package com.example.outfitvault;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.outfitvault.model.DataBaseHelper;
import com.example.outfitvault.model.Outfit;
import com.example.outfitvault.types.Season;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "com.example.outfitvault.MainActivity";
    private NavigationBarView navigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // REQUEST NEEDS TO BE BEFORE super.onCreate CALL
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        getWindow().setExitTransition(new Fade());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // NEVER DELETE THIS LINE BY ACCIDENT OR CANT ACCESS VIEWS

        setupBottomNavDefault();
        setupBottomNavFragmentSwitch();

    }

    private void setupBottomNavDefault() {
        navigationBar = findViewById(R.id.bottom_navigation);
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
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.nav_favorites:
                        selectedFragment = new FavoritesFragment();
                        break;
                    case R.id.nav_places:
                        selectedFragment = new MapsFragment();
                        break;
                    default:
                        selectedFragment = new HomeFragment();
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
