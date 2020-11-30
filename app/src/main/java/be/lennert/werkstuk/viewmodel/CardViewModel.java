package be.lennert.werkstuk.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import be.lennert.werkstuk.controllers.CardRepository;
import be.lennert.werkstuk.controllers.RecipeRepository;
import be.lennert.werkstuk.model.dbmodels.DBCardIngredient;
import be.lennert.werkstuk.model.dbmodels.DBRecipe;
import be.lennert.werkstuk.model.interfaces.IIngredient;
import be.lennert.werkstuk.model.interfaces.TaskListener;

public class CardViewModel extends AndroidViewModel {

    private CardRepository repo;
    private LiveData<List<DBCardIngredient>> allIngredients;

    public CardViewModel(@NonNull Application app) {
        super(app);
        repo = new CardRepository(app);
        allIngredients = repo.getAllIngredients();

    }

    public LiveData<List<DBCardIngredient>> getAllIngredients() {
        return allIngredients;
    }
    public void insertIngredients(List<DBCardIngredient> i, TaskListener l){repo.insert(i, l); }
}
