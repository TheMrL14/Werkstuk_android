
package be.lennert.werkstuk.model.apimodels;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import be.lennert.werkstuk.model.interfaces.IIngredient;
import be.lennert.werkstuk.model.interfaces.IStep;

public class Step implements IStep {

    @SerializedName("number")
    @Expose
    private int number;
    @SerializedName("step")
    @Expose
    private String step;
    @SerializedName("ingredients")
    @Expose
    private List<ExtendedIngredient> ingredients = null;
    @SerializedName("equipment")
    @Expose
    private List<Equipment> equipment = null;
    @SerializedName("length")
    @Expose
    private Length length;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Step() {
    }

    /**
     * 
     * @param number
     * @param length
     * @param ingredients
     * @param equipment
     * @param step
     */
    public Step(int number, String step, List<ExtendedIngredient> ingredients, List<Equipment> equipment, Length length) {
        super();
        this.number = number;
        this.step = step;
        this.ingredients = ingredients;
        this.equipment = equipment;
        this.length = length;
    }
    @Override
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    @Override
    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }
    @Override
    public List<IIngredient> getIngredients() {
        List<IIngredient> returnIngredients = new ArrayList<IIngredient>();

        for(ExtendedIngredient i : ingredients)returnIngredients.add((IIngredient<String>) i);
        return returnIngredients;
    }

    public void setIngredients(List<ExtendedIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Equipment> getEquipment() {
        return equipment;
    }

    public void setEquipment(List<Equipment> equipment) {
        this.equipment = equipment;
    }

    public Length getLength() {
        return length;
    }

    public void setLength(Length length) {
        this.length = length;
    }

}
