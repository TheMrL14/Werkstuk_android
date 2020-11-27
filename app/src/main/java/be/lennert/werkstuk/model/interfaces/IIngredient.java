package be.lennert.werkstuk.model.interfaces;

public interface IIngredient<T> {
    String getName();
    String getMetricAmount(int servings);
    T getImage();
}
