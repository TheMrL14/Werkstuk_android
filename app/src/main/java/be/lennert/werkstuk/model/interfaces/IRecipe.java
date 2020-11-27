package be.lennert.werkstuk.model.interfaces;

public interface IRecipe<T> {
    int getId();
    String getTitle();
    T getImage();

}
