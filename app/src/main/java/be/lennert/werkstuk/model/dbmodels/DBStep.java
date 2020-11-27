package be.lennert.werkstuk.model.dbmodels;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

import be.lennert.werkstuk.model.interfaces.IIngredient;
import be.lennert.werkstuk.model.interfaces.IStep;


@Entity(tableName = "steps")
public class DBStep implements IStep {

    @PrimaryKey(autoGenerate = true)
    private int stepId;
    private int number;
    private String step;
    private int recipeId;
    @Ignore
    private List<IIngredient> ingredients;

    public DBStep() {
    }

    public DBStep(int stepId, int number, String step, int recipeId) {
        this.stepId = stepId;
        this.number = number;
        this.step = step;
        this.recipeId = recipeId;
    }

    public int getStepId() {
        return stepId;
    }

    public void setStepId(int id) {
        this.stepId = id;
    }
    @Override
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    @Override
    public String getStep() {
        return step;
    }

    @Override
    public List<IIngredient> getIngredients() {
        return  ingredients;
    }

    public void setIngredients(List<IIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }
}
