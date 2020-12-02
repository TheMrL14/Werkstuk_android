package be.lennert.werkstuk.model.dbmodels;

import java.util.ArrayList;
import java.util.List;

import be.lennert.werkstuk.model.apimodels.ExtendedIngredient;
import be.lennert.werkstuk.model.dbmodels.DBCardIngredient;
import be.lennert.werkstuk.model.interfaces.IIngredient;

public class DBingredientList {

    private List<DBCardIngredient> ingredients;

    public List<DBCardIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<DBCardIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    public DBingredientList(List<DBCardIngredient> ingredients) {
        this.ingredients = ingredients;
    }
    public DBingredientList(List<IIngredient> ingredients,int portions) {
        this.ingredients = new ArrayList<>();
        for(IIngredient i : ingredients)this.ingredients.add( new DBCardIngredient((ExtendedIngredient)i,portions));
    }
}
