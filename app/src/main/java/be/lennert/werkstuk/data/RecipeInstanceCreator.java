package be.lennert.werkstuk.data;

import android.content.Context;

import com.google.gson.InstanceCreator;

import java.lang.reflect.Type;

import be.lennert.werkstuk.model.apimodels.Recipe;

public class RecipeInstanceCreator implements InstanceCreator<Recipe> {

    private Context context;

    public RecipeInstanceCreator(Context context){
        this.context = context;
    }

    @Override
    public Recipe createInstance(Type type) {
        Recipe recipe = new Recipe(context);

        return recipe;
    }
}
