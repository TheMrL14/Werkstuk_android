package be.lennert.werkstuk.model.interfaces;

import java.util.List;

public interface IStep  {
    int getId();
    int getNumber();
    String getStep();
    List<IIngredient> getIngredients();
}
