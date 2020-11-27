package be.lennert.werkstuk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import be.lennert.werkstuk.controllers.FoodAPIClient;
import be.lennert.werkstuk.viewmodel.RecipeViewModel;
import be.lennert.werkstuk.views.MainFragmentFavourite;
import be.lennert.werkstuk.views.MainFragmentSearch;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();


        BottomNavigationView navBottom = (BottomNavigationView) findViewById(R.id.navBottom);
        navBottom.setOnNavigationItemSelectedListener(navBottomListener);

        initFragment(new MainFragmentSearch());

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navBottomListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()){
                case R.id.action_search:
                    selectedFragment = new MainFragmentSearch();
                    break;
                case R.id.action_favorites:
                    selectedFragment = new MainFragmentFavourite();
                    break;
            }
            // It will help to replace the one fragment to other.
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(
                            R.id.fragment_main,
                            selectedFragment)
                    .commit();
            return true;
        }
    };


    private void initFragment(Fragment selected){
        Fragment selectedFragment = selected;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(
                        R.id.fragment_main,
                        selectedFragment)
                .commit();
    }
}

