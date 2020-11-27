package be.lennert.werkstuk.model.dbmodels.dbrelationmodels;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import be.lennert.werkstuk.model.dbmodels.DBIngredient;
import be.lennert.werkstuk.model.dbmodels.DBRecipe;


public class DBRecipeWithIngredients {
    @Embedded public DBRecipe recipe;
    @Relation(
            parentColumn = "rId",
            entityColumn = "relationId"
    )
    public List<DBIngredient> recipeIngredients;
}
