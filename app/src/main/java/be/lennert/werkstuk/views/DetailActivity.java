package be.lennert.werkstuk.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.honorato.multistatetogglebutton.MultiStateToggleButton;
import org.honorato.multistatetogglebutton.ToggleButton;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.lennert.werkstuk.MainActivity;
import be.lennert.werkstuk.R;
import be.lennert.werkstuk.model.dbmodels.DBRecipe;
import be.lennert.werkstuk.model.interfaces.APIListener;
import be.lennert.werkstuk.model.interfaces.IDetailedRecipe;

import be.lennert.werkstuk.model.apimodels.Recipe;
import be.lennert.werkstuk.model.interfaces.IRecipe;
import be.lennert.werkstuk.model.interfaces.TaskListener;
import be.lennert.werkstuk.utils.DownloadImage;
import be.lennert.werkstuk.utils.ImageUtils;
import be.lennert.werkstuk.utils.StringUtils;
import be.lennert.werkstuk.viewmodel.RecipeViewModel;
import retrofit2.Response;

import static be.lennert.werkstuk.controllers.FoodAPIClient.FOODAPI;

public class DetailActivity extends AppCompatActivity {


    private RecipeViewModel recipeViewModel;

    IDetailedRecipe recipe;
    MultiStateToggleButton switchBtn;
    private boolean isViewIngredients = true;
    private boolean isOnline;
    private int recipeId;
    private boolean isFavourited = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);

        setContentView(R.layout.activity_recipe_detail);
        getSupportActionBar().hide();


        //Get DATA from previous page
        Intent intent = getIntent();
        isOnline = intent.getBooleanExtra(MainActivity.CONNECTION,false);
        recipeId = intent.getIntExtra(MainFragmentSearch.RECIPE_ID,0);

        //Set displayed recipe
        setRecipeById(recipeId);


        //switch btn settings
        switchBtn = (MultiStateToggleButton) findViewById(R.id.switch_rec_ing);
        switchBtn.enableMultipleChoice(false);

        switchBtn.setOnValueChangedListener(new ToggleButton.OnValueChangedListener() {
            @Override
            public void onValueChanged(int position) {
               setMainFragment(multiStateToggleChoices.valueOf(position));
            }
        });

    }

    private void setRecipeById(int id){
        //Check if using an Online or Offline Recipe
        if(isOnline){
            //get recipe from API
            FOODAPI.getRecipeById(id, new APIListener<Recipe>() {
                @Override
                public void call(Response<Recipe> response) {
                    recipe = (IDetailedRecipe) response.body();
                    loadView();
                }
            });
        }else{
            recipeViewModel.getRecipeById(id, new TaskListener<DBRecipe>() {
                @Override
                public void onTaskCompleted(DBRecipe recipeOfDb) {
                    File file = getFileStreamPath(recipeOfDb.getImage());
                    if(file.exists()){
                        recipeOfDb.setImage(file.getAbsolutePath());
                    }
                    recipe = (IDetailedRecipe) recipeOfDb;
                    loadView();
                }
            });
        }
        ;
    }

    private void loadView() {//Put Data in View
        TextView txtTitle = (TextView) findViewById(R.id.txtRecipeTitle);
        txtTitle.setText(recipe.getTitle());
        //set main fragment to isViewIngredients (default true = default Display the ingredients)
        ImageView imgRecipe = (ImageView) findViewById(R.id.imgRecipe);
        if(isOnline) Picasso.get().load((String) recipe.getImage()).into(imgRecipe);
        else{
            Bitmap bmp = BitmapFactory.decodeFile(recipe.getImage());
            imgRecipe.setImageBitmap(bmp);
        }

        setMainFragment(isViewIngredients? multiStateToggleChoices.INGREDIENTS : multiStateToggleChoices.RECIPE);
        //Check if recipe is saved in Local Database
        recipeViewModel.isRecipeSaved(recipe.getId(),new TaskListener<Boolean>(){
            @Override
            public void onTaskCompleted(Boolean b) {
                isFavourited = b;
                //fill or unfill heart
                setHeartButton();
            }
        });

    }

    //change fragment on the page
    private void setMainFragment(multiStateToggleChoices toggle){
        Fragment selectedFragment = null;
        switch (toggle){
            case INGREDIENTS:
                isViewIngredients = true;
                selectedFragment = new FragmentIngredientDetail(recipe.getIngredients());
                break;
            case RECIPE:
                isViewIngredients = false;
                selectedFragment = new FragmentRecipeDetail(recipe.getSteps());
                break;

        }
        swhitchMultiStateIngredients(isViewIngredients);
        // It will help to replace the one fragment to other.
        getSupportFragmentManager()
                .beginTransaction()
                .replace(
                        R.id.fragmentDetailContainer,
                        selectedFragment)
                .commit();

    }

    private void swhitchMultiStateIngredients(boolean bool){
        switchBtn.setStates(new boolean[]{bool,!bool});
    }

    // Heart button is pressed
    //uploads/deletes from database
    public void addRemoveFavourite(View view) {
        isFavourited = !isFavourited;
        if(isFavourited){uploadRecipe();}
        else{deleteRecipe();}

    }

    private void uploadRecipe(){
        final String imagePath = StringUtils.generateInternalImagePath(recipe.getTitle());
        //TODO optimise Async flow ( download image seperate from uploading recipe)
        new DownloadImage(getApplicationContext(),imagePath, new TaskListener<Boolean>() {
            @Override
            public void onTaskCompleted(Boolean b) {
            }
        }).execute((String) recipe.getImage());

        recipeViewModel.insert(new DBRecipe(recipe, imagePath), new TaskListener<Boolean>() {
            @Override
            public void onTaskCompleted(Boolean b) {setHeartButton();}
        });
    }

    private void deleteRecipe(){
        //Delete from database
        recipeViewModel.delete(new DBRecipe(recipe, ""), new TaskListener<Boolean>() {
            @Override
            public void onTaskCompleted(Boolean b) {
                setHeartButton();
            }
        });
        //Delete image
        File file = ImageUtils.getLocalFile(getApplicationContext(),recipe.getTitle());
        file.delete();
    }
    //fill / unfill heart
    private void setHeartButton(){
        final ImageButton heartBtn = (ImageButton) findViewById(R.id.btnFavourite);
        if(isFavourited) heartBtn.setImageResource(R.drawable.favourite_fill);
        else heartBtn.setImageResource(R.drawable.favourite);
    }


    //Enum for Multe state button ( uses int value )
    enum multiStateToggleChoices{
        //https://codingexplained.com/coding/java/enum-to-integer-and-integer-to-enum
        INGREDIENTS(0),
        RECIPE(1);
        private int value;
        private static Map map = new HashMap<>();


        private multiStateToggleChoices(int value) {
            this.value = value;
        }
        static {
            for (multiStateToggleChoices state : multiStateToggleChoices.values()) {
                map.put(state.value, state);
            }
        }

        public static multiStateToggleChoices valueOf(int pageType) {
            return (multiStateToggleChoices) map.get(pageType);
        }

        public int getValue() {
            return value;
        }

    }

    //used for downloading an image and save locally
    //in combination with static funcs from ImageUtils

}