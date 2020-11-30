package be.lennert.werkstuk.model.dbmodels.dbrelationmodels;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import be.lennert.werkstuk.model.dbmodels.DBIngredient;
import be.lennert.werkstuk.model.dbmodels.DBStep;

public class DBStepWithIngredients {

    @Embedded  public DBStep step;
    @Relation(
            parentColumn = "stepId",
            entityColumn = "relationId"
    )
    public List<DBIngredient> ingredientList;
}
