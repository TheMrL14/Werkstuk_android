package be.lennert.werkstuk.model.interfaces;

import java.util.List;

public interface IDetailedRecipe extends IRecipe  {
    public List<IIngredient> getIngredients();
    public List<IStep> getSteps();
}
