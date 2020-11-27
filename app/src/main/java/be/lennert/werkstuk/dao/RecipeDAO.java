package be.lennert.werkstuk.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.ArrayList;
import java.util.List;

import be.lennert.werkstuk.model.apimodels.Ingredient;
import be.lennert.werkstuk.model.dbmodels.DBIngredient;
import be.lennert.werkstuk.model.dbmodels.DBRecipe;
import be.lennert.werkstuk.model.dbmodels.DBStep;
import be.lennert.werkstuk.model.interfaces.IIngredient;
import be.lennert.werkstuk.model.interfaces.IStep;

@Dao
public abstract class RecipeDAO {

    @Transaction
    @Query("SELECT * FROM recipes")
    public abstract LiveData<List<DBRecipe>> loadAllRecipes();

    @Query("SELECT rId FROM recipes WHERE rId = :id")
    public abstract int recipeIsSaved(int id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insert(DBRecipe recipe);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertFull(DBRecipe recipe){
        if(recipe.getSteps() != null && recipe.getIngredients() != null){
            insertIngredients(recipe, recipe.getIngredients());
            insertSteps(recipe, recipe.getSteps());
            insertRecipe(recipe);
        }
    };

    private void insertIngredients(DBRecipe recipe, List<IIngredient> ingredients){
        ArrayList<DBIngredient> dbIngredientList = new ArrayList<>();
        for(IIngredient i : ingredients){
            DBIngredient ing = (DBIngredient) i;
            ing.setRecipeId(recipe.getId());
            dbIngredientList.add(ing);
        }

        insertAllIngredients(dbIngredientList);
    }

    private void insertSteps(DBRecipe recipe, List<IStep> steps){
        ArrayList<DBStep> dbStepsList = new ArrayList<>();
        for(IStep i : steps){
            DBStep step = (DBStep) i;
            step.setRecipeId(recipe.getId());
            dbStepsList.add(step);
        }

        insertAllSteps(dbStepsList);
    }

    @Insert
    public abstract void insertAllIngredients(List<DBIngredient> ingredients);
    @Insert
    public abstract void insertRecipe(DBRecipe recipe);
    @Insert
    public abstract void insertAllSteps(List<DBStep> steps);

    @Delete
    public abstract void delete(DBRecipe recipes);

    @Query("DELETE FROM recipes")
    public abstract void deleteAll();

}
