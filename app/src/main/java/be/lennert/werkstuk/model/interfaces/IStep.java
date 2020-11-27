package be.lennert.werkstuk.model.interfaces;

import java.util.List;

public interface IStep  {
    int getNumber();
    String getStep();
    List<IIngredient> getIngredients();
}
