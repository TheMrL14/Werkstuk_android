package be.lennert.werkstuk.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import be.lennert.werkstuk.controllers.RecipeRepository;
import be.lennert.werkstuk.model.dbmodels.DBRecipe;
import be.lennert.werkstuk.model.interfaces.TaskListener;

public class FavouritesViewModel extends AndroidViewModel {

    private RecipeRepository repo;
    private LiveData<List<DBRecipe>> allRecipes;

    public FavouritesViewModel(@NonNull Application app) {
        super(app);
        repo = new RecipeRepository(app);
        allRecipes = repo.getAllRecipes();
    }

    public LiveData<List<DBRecipe>> getAllRecipes() {
        return allRecipes;
    }
    public void isRecipeSaved(int id, TaskListener l){repo.isRecipeSaved(id, l);}
}
