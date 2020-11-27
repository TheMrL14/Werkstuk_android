package be.lennert.werkstuk.model.dbmodels;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import be.lennert.werkstuk.model.interfaces.IIngredient;
import be.lennert.werkstuk.utils.StringUtils;

@Entity(tableName = "ingredients")
public class DBIngredient implements IIngredient<byte[]> {
    @PrimaryKey(autoGenerate = true)
    private int ingredientId;
    private String name;
    private double quantity;
    private String unit;
    private byte[] image;
    private int relationId;

    public DBIngredient() {
    }

    public DBIngredient(int ingredientId, String name, double quantity, String unit, byte[] image, int relationId) {
        this.ingredientId = ingredientId;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.image = image;
        this.relationId = relationId;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getMetricAmount(int servings) {
        StringBuilder sb = new StringBuilder();
        sb.append(StringUtils.roundToNearestTen(quantity*servings));
        sb.append(unit);
        return sb.toString();
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getRecipeId() {
        return relationId;
    }

    public void setRecipeId(int recipeId) {
        this.relationId = recipeId;
    }

    public int getRelationId() {
        return relationId;
    }

    public void setRelationId(int relationId) {
        this.relationId = relationId;
    }


}
