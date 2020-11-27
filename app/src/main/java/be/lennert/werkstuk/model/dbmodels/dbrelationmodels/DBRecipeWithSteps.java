package be.lennert.werkstuk.model.dbmodels.dbrelationmodels;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import be.lennert.werkstuk.model.dbmodels.DBRecipe;
import be.lennert.werkstuk.model.dbmodels.DBStep;


public class DBRecipeWithSteps {
    @Embedded public DBRecipe recipe;
    @Relation(
            parentColumn = "rId",
            entityColumn = "recipeId"
    )
    public List<DBStep> recipeSteps;
}
