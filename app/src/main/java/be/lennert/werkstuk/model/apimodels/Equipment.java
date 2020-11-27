
package be.lennert.werkstuk.model.apimodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Equipment {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("localizedName")
    @Expose
    private String localizedName;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("temperature")
    @Expose
    private Temperature temperature;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Equipment() {
    }

    /**
     * 
     * @param image
     * @param localizedName
     * @param name
     * @param temperature
     * @param id
     */
    public Equipment(int id, String name, String localizedName, String image, Temperature temperature) {
        super();
        this.id = id;
        this.name = name;
        this.localizedName = localizedName;
        this.image = image;
        this.temperature = temperature;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocalizedName() {
        return localizedName;
    }

    public void setLocalizedName(String localizedName) {
        this.localizedName = localizedName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

}
