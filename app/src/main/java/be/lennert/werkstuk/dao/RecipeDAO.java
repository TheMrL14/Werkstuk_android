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
import be.lennert.werkstuk.model.dbmodels.dbrelationmodels.DBRecipeWithIngredients;
import be.lennert.werkstuk.model.dbmodels.dbrelationmodels.DBRecipeWithSteps;
import be.lennert.werkstuk.model.dbmodels.dbrelationmodels.DBStepWithIngredients;
import be.lennert.werkstuk.model.interfaces.IIngredient;
import be.lennert.werkstuk.model.interfaces.IStep;

@Dao
public abstract class RecipeDAO {

    @Transaction
    @Query("SELECT * FROM recipes")
    public abstract LiveData<List<DBRecipe>> loadAllRecipes();

    @Transaction
    @Query("SELECT * FROM steps WHERE recipeId = :id")
    public abstract List<DBStep> getStepsWithId(int id);

    @Transaction
    @Query("SELECT * FROM ingredients WHERE relationId = :id")
    public abstract List<DBIngredient> getIngredientsWithId(int id);

    @Transaction
    @Query("SELECT * FROM recipes WHERE rId = :id")
    public abstract DBRecipeWithIngredients getRecipeById(int id);

    @Query("SELECT rId FROM recipes WHERE rId = :id")
    public abstract int recipeIsSaved(int id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insert(DBRecipe recipe);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertFull(DBRecipe recipe){
        if(recipe.getSteps() != null && recipe.getIngredients() != null){
            insertIngredients(recipe.getId(), recipe.getIngredients());
            insertSteps(recipe, recipe.getSteps());
            insertRecipe(recipe);
        }
    };

    private void insertIngredients(int id, List<IIngredient> ingredients){
        ArrayList<DBIngredient> dbIngredientList = new ArrayList<>();
        for(IIngredient i : ingredients){
            DBIngredient ing = new DBIngredient(i,id) ;
            dbIngredientList.add(ing);
        }

        insertAllIngredients(dbIngredientList);
    }
    private void insertMinimalIngredients(int id, List<IIngredient> ingredients){
        ArrayList<DBIngredient> dbIngredientList = new ArrayList<>();
        for(IIngredient i : ingredients){
            DBIngredient ing = new DBIngredient(i.getName(),id) ;
            dbIngredientList.add(ing);
        }

        insertAllIngredients(dbIngredientList);
    }

    private void insertSteps(DBRecipe recipe, List<IStep> steps){
        ArrayList<DBStep> dbStepsList = new ArrayList<>();
        for(IStep i : steps){
            DBStep step = new DBStep(i,recipe.getId());
            int id = (int) insertStep(step);
            if(step.getIngredients() != null) insertMinimalIngredients(id,step.getIngredients());
            dbStepsList.add(step);
        }

        insertAllSteps(dbStepsList);
    }



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertAllIngredients(List<DBIngredient> ingredients);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertRecipe(DBRecipe recipe);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertAllSteps(List<DBStep> steps);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long insertStep(DBStep step);

    @Delete
    public abstract void delete(DBRecipe recipes);

    @Query("DELETE FROM recipes")
    public abstract void deleteAll();

}
