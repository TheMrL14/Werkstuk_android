package be.lennert.werkstuk.model.interfaces;

public interface IIngredient {
    String getName();
    double getAmount();
    String getUnit();
    String getMetricAmount(int servings);
    String getImage();
}
