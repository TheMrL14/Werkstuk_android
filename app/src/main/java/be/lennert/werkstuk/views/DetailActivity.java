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
import be.lennert.werkstuk.model.interfaces.TaskListener;
import be.lennert.werkstuk.utils.ImageUtils;
import be.lennert.werkstuk.viewmodel.RecipeViewModel;
import retrofit2.Response;

import static be.lennert.werkstuk.controllers.FoodAPIClient.FOODAPI;

public class DetailActivity extends AppCompatActivity {

    public static final String CONNECTION = "be.lennert.werkstuk.IS_OFFLINE";
    public RecipeViewModel recipeViewModel;

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

        Intent intent = getIntent();
        isOnline = intent.getBooleanExtra(CONNECTION,false);
        recipeId = intent.getIntExtra(MainFragmentSearch.RECIPE_ID,0);

        setRecipeById(recipeId);



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
        if(isOnline){
            FOODAPI.getRecipeById(id, new APIListener<Recipe>() {
                @Override
                public void call(Response<Recipe> response) {
                    recipe = (IDetailedRecipe) response.body();
                    loadView();
                }
            });
        }else{

        }
        ;
    }

    private void loadView() {
        TextView txtTitle = (TextView) findViewById(R.id.txtRecipeTitle);
        ImageView imgRecipe = (ImageView) findViewById(R.id.imgRecipe);
        Picasso.get().load((String) recipe.getImage()).into(imgRecipe);
        txtTitle.setText(recipe.getTitle());
        setMainFragment(isViewIngredients? multiStateToggleChoices.INGREDIENTS : multiStateToggleChoices.RECIPE);
        recipeViewModel.isRecipeSaved(recipe.getId(),new TaskListener(){

            @Override
            public void onTaskCompleted(boolean b) {
                isFavourited = false;
                setHeartButton();
            }
        });

    }


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

    public void addRemoveFavourite(View view) {
        isFavourited = !isFavourited;
        if(isFavourited){
            new  DownloadImage(recipe.getTitle().replaceAll("\\s+","")  +".jpeg", new TaskListener() {
                @Override
                public void onTaskCompleted(boolean b) {
                    File file = getApplicationContext().getFileStreamPath(recipe.getTitle().replaceAll("\\s+","") +".jpeg");
                    String imageFullPath = file.getAbsolutePath();
                    recipeViewModel.insert(new DBRecipe(recipe, imageFullPath), new TaskListener() {
                        @Override
                        public void onTaskCompleted(boolean b) {
                            setHeartButton();
                            recipeViewModel.getAllRecipes();
                        }
                    });

                }
            }).execute((String) recipe.getImage());


        }else{

        }

    }

    private void setHeartButton(){
        final ImageButton heartBtn = (ImageButton) findViewById(R.id.btnFavourite);
        if(isFavourited) heartBtn.setImageResource(R.drawable.favourite_fill);
        else heartBtn.setImageResource(R.drawable.favourite);
    }


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

    private class DownloadImage extends AsyncTask<String,Void, Bitmap>{

        private String fileName;
        private TaskListener listener;
        public DownloadImage(String fileName, TaskListener listener){
            this.fileName = fileName;
            this.listener = listener;
        }


        private Bitmap downloadImageBitmap(String sUrl) {
            Bitmap bitmap = null;
            try {
                InputStream inputStream = new URL(sUrl).openStream();   // Download Image from URL
                bitmap = BitmapFactory.decodeStream(inputStream);       // Decode Bitmap
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            return downloadImageBitmap(strings[0]);
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if(fileName != null) ImageUtils.saveImage(getApplicationContext(),result, fileName,listener);

        }
    }
}