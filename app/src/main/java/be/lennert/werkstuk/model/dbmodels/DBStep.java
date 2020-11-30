package be.lennert.werkstuk.model.dbmodels;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

import be.lennert.werkstuk.model.apimodels.Step;
import be.lennert.werkstuk.model.interfaces.IIngredient;
import be.lennert.werkstuk.model.interfaces.IStep;


@Entity(tableName = "steps")
public class DBStep implements IStep {

    @PrimaryKey(autoGenerate = true)
    private int stepId = 0;
    private int number;
    private String step;
    private int recipeId;
    @Ignore
    private List<IIngredient> ingredients;

    public DBStep() {
    }

    public DBStep(int number, String step, int recipeId) {
        this.number = number;
        this.step = step;
        this.recipeId = recipeId;
    }

    public DBStep(IStep s,int recipeId){
        this(s.getNumber(),s.getStep(),recipeId);
        this.setIngredients(s.getIngredients());
    }

    public int getStepId() {
        return stepId;
    }

    public void setStepId(int id) {
        this.stepId = id;
    }

    @Override
    public int getId() {
        return this.getStepId();
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
        List<IIngredient> castedIngredients = new ArrayList<>();
        if(ingredients != null){
            for(IIngredient i : ingredients){
                castedIngredients.add(new DBIngredient(i.getName(),this.stepId));
            }
        }
        this.ingredients = castedIngredients;
    }

    public void setIngredientsFromDB(List<DBIngredient> ingredients) {
        List<IIngredient> castedIngredients = new ArrayList<>();
        if(ingredients != null){
            for(IIngredient i : ingredients){
                castedIngredients.add(new DBIngredient(i.getName(),this.stepId));
            }
        }
        this.ingredients = castedIngredients;
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
