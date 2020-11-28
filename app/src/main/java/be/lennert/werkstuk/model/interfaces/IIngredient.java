package be.lennert.werkstuk.model.interfaces;

public interface IIngredient {
    String getName();
    double getAmount();
    String GetUnit();
    String getMetricAmount(int servings);
    String getImage();
}
