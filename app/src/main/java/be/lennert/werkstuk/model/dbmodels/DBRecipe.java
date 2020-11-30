package be.lennert.werkstuk.model.dbmodels;


import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

import be.lennert.werkstuk.model.apimodels.Ingredient;
import be.lennert.werkstuk.model.apimodels.Recipe;
import be.lennert.werkstuk.model.interfaces.IDetailedRecipe;
import be.lennert.werkstuk.model.interfaces.IIngredient;
import be.lennert.werkstuk.model.interfaces.IRecipe;
import be.lennert.werkstuk.model.interfaces.IStep;

@Entity(tableName = "recipes")
public class DBRecipe implements IDetailedRecipe {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "rId")
    private int rId;
    @ColumnInfo(name = "portions")
    private int portions;
    @NonNull
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "image")
    private String image;


    @Ignore
    private Bitmap imageBitmap;

    @Ignore
    private List<IIngredient> ingredients;

    @Ignore
    private List<IStep> steps;

    public DBRecipe() {
    }

    public DBRecipe(int id, int portions, String title, String image, List<IIngredient> ingredients,List<IStep>steps ) {
        this.rId = id;
        this.portions = portions;
        this.title = title;
        this.image = image;
        this.ingredients = ingredients;
        this.steps = steps;
    }
    @Ignore
    public DBRecipe(Recipe r,String imagePath){
        this(r.getId(),1,r.getTitle(),imagePath,r.getIngredients(), r.getSteps());
    }
    @Ignore
    public DBRecipe(IDetailedRecipe r, String imagePath){
        this(r.getId(),1,r.getTitle(),imagePath,r.getIngredients(), r.getSteps());
    }


    public int getRId() {
        return rId;
    }

    public void setRId(int rId) {
        this.rId = rId;
    }

    public int getPortions() {
        return portions;
    }

    public void setPortions(int portions) {
        this.portions = portions;
    }

    @Override
    public int getId() {
        return rId;
    }



    @Override
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    @Override
    public List<IIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<DBIngredient> dbIngredients) {
        List<IIngredient> ingredients = new ArrayList<>();
        for(DBIngredient ingredient : dbIngredients){
            ingredients.add((IIngredient) ingredient);
        }
        this.ingredients = ingredients;
    }




    @Override
    public List<IStep> getSteps() {
        return steps;
    }
    public void setSteps(List<IStep> steps) {
        this.steps = steps;
    }

    public void setDbsteps(List<DBStep> dbsteps) {
        List<IStep> steps = new ArrayList<>();
        for(DBStep ingredient : dbsteps){
            steps.add((IStep) ingredient);
        }
        this.steps = steps;
    }
    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }
}
