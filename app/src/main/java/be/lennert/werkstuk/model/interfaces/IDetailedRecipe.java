package be.lennert.werkstuk.model.interfaces;

import java.util.List;

public interface IDetailedRecipe<T> extends IRecipe<T>  {
    public List<IIngredient> getIngredients();
    public List<IStep> getSteps();
}
