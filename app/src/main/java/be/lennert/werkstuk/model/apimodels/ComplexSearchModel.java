package be.lennert.werkstuk.model.apimodels;
import com.google.gson.annotations.SerializedName;

import be.lennert.werkstuk.model.interfaces.IRecipe;

public class ComplexSearchModel implements IRecipe {
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("image")
    private String image;


    public ComplexSearchModel() {
    }

    public ComplexSearchModel(int id, String title, String image) {
        this.id = id;
        this.title = title;
        this.image = image;
    }
    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    @Override
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
