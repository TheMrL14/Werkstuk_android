package be.lennert.werkstuk.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import be.lennert.werkstuk.controllers.RecipeRepository;
import be.lennert.werkstuk.model.apimodels.Recipe;
import be.lennert.werkstuk.model.dbmodels.DBRecipe;
import be.lennert.werkstuk.model.interfaces.TaskListener;

public class RecipeViewModel extends AndroidViewModel {
    private RecipeRepository repo;
    private LiveData<List<DBRecipe>> allRecipes;

    public RecipeViewModel(@NonNull Application app){
        super(app);
        repo = new RecipeRepository(app);
        allRecipes = repo.getAllRecipes();
    }

    public LiveData<List<DBRecipe>> getAllRecipes() {
        return allRecipes;
    }

    public void insert(DBRecipe r,TaskListener l){repo.insert(r,l);}

    public void isRecipeSaved(int id,TaskListener l){repo.isRecipeSaved(id, l);}

    public void delete(DBRecipe r,TaskListener l ){repo.deleteRecipe(r,l);}
}
