package be.lennert.werkstuk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import be.lennert.werkstuk.controllers.FoodAPIClient;
import be.lennert.werkstuk.services.TimerService;
import be.lennert.werkstuk.viewmodel.RecipeViewModel;
import be.lennert.werkstuk.views.MainFragmentFavourite;
import be.lennert.werkstuk.views.MainFragmentSearch;
import be.lennert.werkstuk.views.MainFragmentShoppingList;
import be.lennert.werkstuk.views.MainFragmentTimer;
import be.lennert.werkstuk.views.TimerFormActivity;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();


        BottomNavigationView navBottom = (BottomNavigationView) findViewById(R.id.navBottom);
        navBottom.setOnNavigationItemSelectedListener(navBottomListener);

        Intent i = getIntent();

        if(i.getBooleanExtra(TimerFormActivity.NEWTIMER, false)){
            initFragment(new MainFragmentTimer());
            navBottom.setSelectedItemId(R.id.action_timer);
        }
        else  initFragment(new MainFragmentSearch());



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
                case R.id.action_list:
                    selectedFragment = new MainFragmentShoppingList();
                    break;
                case R.id.action_timer:
                    selectedFragment = new MainFragmentTimer();
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

